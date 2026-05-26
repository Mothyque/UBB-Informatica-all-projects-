<?php
require_once 'db_sqlite.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    http_response_code(405);
    echo json_encode(['error' => 'Method Not Allowed']);
    exit;
}

$page = isset($_GET['page']) ? (int)$_GET['page'] : 1;
$per_page = isset($_GET['per_page']) ? (int)$_GET['per_page'] : 5;

if ($page < 1) {
    $page = 1;
}
if ($per_page < 1) {
    $per_page = 5;
}

$offset = ($page - 1) * $per_page;

try {
    $count_stmt = $sqlite_db->prepare("SELECT COUNT(*) as total FROM logs");
    $count_stmt->execute();
    $total = (int)$count_stmt->fetch(PDO::FETCH_ASSOC)['total'];

    $sql = "SELECT id, event, time FROM logs ORDER BY time DESC LIMIT :limit OFFSET :offset";
    $stmt = $sqlite_db->prepare($sql);
    $stmt->bindValue(':limit', $per_page, PDO::PARAM_INT);
    $stmt->bindValue(':offset', $offset, PDO::PARAM_INT);
    $stmt->execute();
    $records = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $total_pages = (int)ceil($total / $per_page);

    header('Content-Type: application/json; charset=utf-8');
    echo json_encode([
        'records' => $records,
        'page' => $page,
        'per_page' => $per_page,
        'total' => $total,
        'total_pages' => $total_pages
    ]);
} catch (PDOException $e) {
    http_response_code(500);
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
}