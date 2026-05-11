<?php
session_start();
require_once 'db_pdo.php';

if (!isset($_SESSION['user_id']) || $_SESSION['role'] !== 'admin') {
    die("Access Denied: You do not have permission to perform this action.");
}

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['add_player'])) {
    
    $name     = $_POST['name'] ?? '';
    $team     = $_POST['team'] ?? '';
    $position = $_POST['position'] ?? '';
    $height   = !empty($_POST['height']) ? (int)$_POST['height'] : null;
    $added_by = $_SESSION['user_id']; 
    $upload_dir = "../images/players/";
    
    if (!is_dir($upload_dir)) {
        mkdir($upload_dir, 0777, true);
    }

    $file_name = time() . "_" . basename($_FILES["player_image"]["name"]);
    $target_file = $upload_dir . $file_name;
    $image_file_type = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));

    $upload_ok = true;

    $check = getimagesize($_FILES["player_image"]["tmp_name"]);
    if($check === false) {
        die("Error: File is not a valid image.");
        $upload_ok = false;
    }

    if ($_FILES["player_image"]["size"] > 2000000) {
        die("Error: File is too large. Max size is 2MB.");
        $upload_ok = false;
    }

    if ($image_file_type != "png") {
        die("Error: Only PNG files are allowed.");
        $upload_ok = false;
    }

    if ($upload_ok) {
        if (move_uploaded_file($_FILES["player_image"]["tmp_name"], $target_file)) {
            
            try {
                $sql = "INSERT INTO players (name, team, position, height, image_link, added_by) 
                        VALUES (:name, :team, :position, :height, :image, :added_by)";
                
                $stmt = $pdo->prepare($sql);
                $stmt->execute([
                    ':name'     => $name,
                    ':team'     => $team,
                    ':position' => $position,
                    ':height'   => $height,
                    ':image'    => $file_name,
                    ':added_by' => $added_by
                ]);

                header("Location: ../html/adminPage5.php?status=success&msg=PlayerAdded");
                exit;

            } catch (PDOException $e) {
                unlink($target_file);
                die("Database Error: " . $e->getMessage());
            }
        } else {
            die("Error: There was an issue moving the uploaded file.");
        }
    }
}