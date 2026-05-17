<?php
session_start();
require_once 'db_pdo.php';

if (!isset($_SESSION['user_id']) || $_SESSION['role'] !== 'admin') {
    die("Acces neautorizat.");
}

// VULNERABILITY DEMO: CSRF
// if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['delete_player'])) {
//     $nameToDelete = $_POST['player_name'];

//     $stmt = $pdo->prepare("SELECT image_link FROM players WHERE name = ?");
//     $stmt->execute([$nameToDelete]);
//     $player = $stmt->fetch();

//     if ($player) {
//         $imageName = $player['image_link'];
//         $filePath = "../images/players/" . $imageName;

//         if (!empty($imageName) && file_exists($filePath)) {
//             unlink($filePath); 
//         }

//         $deleteStmt = $pdo->prepare("DELETE FROM players WHERE name = ?");
//         $deleteStmt->execute([$nameToDelete]);

//         header("Location: ../html/adminPage5.php?status=deleted");
//         exit;
//     } else {
//         header("Location: ../html/adminPage5.php?status=error&msg=NotFound");
//         exit;
//     }
// }

// SAFE VERSION
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['delete_player'])) {
    if (!isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
        die("CSRF token invalid.");
    }

    $nameToDelete = $_POST['player_name'];
    $stmt = $pdo->prepare("SELECT image_link FROM players WHERE name = ?");
    $stmt->execute([$nameToDelete]);
    $player = $stmt->fetch();
    if ($player) {
        $imageName = $player['image_link'];
        $filePath = "../images/players/" . $imageName;

        if (!empty($imageName) && file_exists($filePath)) {
            unlink($filePath); 
        }

        $deleteStmt = $pdo->prepare("DELETE FROM players WHERE name = ?");
        $deleteStmt->execute([$nameToDelete]);

        header("Location: ../html/adminPage5.php?status=deleted");
        exit;
    } else {
        header("Location: ../html/adminPage5.php?status=error&msg=NotFound");
        exit;
    }
}