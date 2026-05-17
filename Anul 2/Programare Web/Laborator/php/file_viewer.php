<?php
session_start();
require_once 'db_pdo.php';

if (!isset($_SESSION['user_id'])) {
    die("Access Denied: You must be logged in.");
}

// VULNERABILITY DEMO: Path Traversal
// Example usage:
// http://localhost:8000/php/file_viewer.php?file=../../php/db_pdo.php

// $file = $_GET['file'] ?? '';
// $path = "../images/players/" . $file;

// if (file_exists($path)) {
//     echo "<pre>";
//     echo htmlspecialchars(base64_decode(base64_encode(file_get_contents($path))));
//     echo "</pre>";
// } else {
//     echo "File not found: " . $path;
// }

// SAFE VERSION 
$file = basename($_GET['file'] ?? '');
$path = "../images/players/" . $file;

if (file_exists($path)) {
    echo "<pre>";
    echo htmlspecialchars(file_get_contents($path));
    echo "</pre>";
} else {
    echo "File not found.";
}
?>