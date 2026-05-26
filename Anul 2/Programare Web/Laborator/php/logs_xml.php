<?php
require_once 'db_sqlite.php';

$page = isset($_GET['page']) ? (int)$_GET['page'] : 1;
$per_page = isset($_GET['per_page']) ? (int)$_GET['per_page'] : 5;
if ($page < 1) {
    $page = 1;
}
if ($per_page < 1) {
    $per_page = 5;
}
$offset = ($page - 1) * $per_page;

header('Content-Type: application/xml; charset=utf-8');

try {
    $count_stmt = $sqlite_db->prepare("SELECT COUNT(*) as total FROM logs");
    $count_stmt->execute();
    $total = (int)$count_stmt->fetch(PDO::FETCH_ASSOC)['total'];
    $total_pages = (int)ceil($total / $per_page);

    $stmt = $sqlite_db->prepare("SELECT id, event, time FROM logs ORDER BY time DESC LIMIT :limit OFFSET :offset");
    $stmt->bindValue(':limit', $per_page, PDO::PARAM_INT);
    $stmt->bindValue(':offset', $offset, PDO::PARAM_INT);
    $stmt->execute();
    $records = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo '<response>';
    echo '<metadata>';
    echo '<page>' . $page . '</page>';
    echo '<total_pages>' . $total_pages . '</total_pages>';
    echo '</metadata>';
    echo '<records>';
    if ($records) {
        foreach ($records as $row) {
            echo '<record>';
            echo '<id>' . htmlspecialchars($row['id']) . '</id>';
            echo '<event>' . htmlspecialchars($row['event']) . '</event>';
            echo '<time>' . htmlspecialchars($row['time']) . '</time>';
            echo '</record>';
        }
    }
    echo '</records>';
    echo '</response>';
} catch (PDOException $e) {
    http_response_code(500);
    header('Content-Type: application/xml; charset=utf-8');
    echo '<error>' . $e->getMessage() . '</error>';
}