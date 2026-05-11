<?php
try {
    $db_path = __DIR__ . "/activity.sqlite";
    $sqlite_db = new PDO("sqlite:" . $db_path);
    $sqlite_db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    $sqlite_db->exec("CREATE TABLE IF NOT EXISTS logs (id INTEGER PRIMARY KEY, event TEXT, time DATETIME)");
} catch (PDOException $e) {
    die("SQLite Connection failed: " . $e->getMessage());
}
?>