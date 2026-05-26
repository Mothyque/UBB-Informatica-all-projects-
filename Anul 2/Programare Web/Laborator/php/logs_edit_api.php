<?php
require_once 'db_sqlite.php';
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['id'])) {
    $stmt = $sqlite_db->prepare("SELECT id, event, time FROM logs WHERE id = :id");
    $stmt->execute([':id' => $_GET['id']]);
    $record = $stmt->fetch(PDO::FETCH_ASSOC);
    echo json_encode($record ?: ['error' => 'Record not found']);
}

elseif ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = json_decode(file_get_contents('php://input'), true);

    if (isset($data['id'], $data['event'], $data['time'])) {
        $stmt = $sqlite_db->prepare("UPDATE logs SET event = :event, time = :time WHERE id = :id");
        $success = $stmt->execute([
            ':id' => $data['id'],
            ':event' => $data['event'],
            ':time' => $data['time']
        ]);
        echo json_encode(['success' => $success]);
    } else {
        echo json_encode(['error' => 'Invalid input']);
    }
}

elseif ($_SERVER['REQUEST_METHOD'] === 'GET' && !isset($_GET['id'])) {
    $stmt = $sqlite_db->query("SELECT id FROM logs ORDER BY id DESC");
    echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));
}