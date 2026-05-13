<?php
error_reporting(E_ALL);
session_start();
require_once 'db_pdo.php';
require_once 'db_sqlite.php';

if (!isset($_SESSION['captcha_sum'])) {
    $num1 = rand(1, 10);
    $num2 = rand(1, 10);
    $_SESSION['captcha_sum'] = $num1 + $num2;
    $_SESSION['captcha_text'] = "$num1 + $num2 = ?";
}

if (!isset($pdo) || !isset($sqlite_db)) {
    die("Database connection error. Please contact administrator.");
}



if (!isset($_SESSION['user_id']) && isset($_COOKIE['user_login'])) {
    $cookie_user = $_COOKIE['user_login'];

    $stmt = $pdo->prepare("SELECT id, username, role FROM users WHERE username = ?");
    $stmt->execute([$cookie_user]);
    $user = $stmt->fetch();

    if($user) {
        $_SESSION['user_id'] = $user['id'];
        $_SESSION['username'] = $user['username'];
        $_SESSION['role'] = $user['role'];
        header("Location: ../html/" . ($user['role'] === 'admin' ? "adminPage5.php" : "mainPage5.php"));
        exit;
    }
    else {
        setcookie("user_login", "", time() - 3600, "/");
    }
}

$error = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $captcha_response = $_POST['captcha_response'] ?? 0;
    if ($captcha_response != $_SESSION['captcha_sum']) {
        $error = "Incorrect CAPTCHA answer. Please try again.";
        $num1 = rand(1, 10);
        $num2 = rand(1, 10);
        $_SESSION['captcha_sum'] = $num1 + $num2;
        $_SESSION['captcha_text'] = "$num1 + $num2 = ?";
        } else {
        $username = $_POST['username'] ?? '';
        $password = $_POST['password'] ?? '';

        $stmt = $pdo->prepare("SELECT id, username, password_hash, role FROM users WHERE username = ?");
        $stmt->execute([$username]);
        $user = $stmt->fetch();

        if ($user && password_verify($password, $user['password_hash'])) {
            $_SESSION['user_id'] = $user['id'];
            $_SESSION['username'] = $user['username'];
            $_SESSION['role'] = $user['role'];

            if (isset($_POST['remember'])) {
                setcookie("user_login", $user['username'], time() + (86400 * 30), "/");
            }

            $log_stmt = $sqlite_db->prepare("INSERT INTO logs (event, time) VALUES (?, DATETIME('now'))");
            $log_stmt->execute(["User '$username' logged in successfully."]);
            if ($_SESSION['role'] === 'admin') {
                header("Location: ../html/adminPage5.php");
            } else {
                header("Location: ../html/mainPage5.php");
            }


            exit;
        } else {
            $error = "Invalid username or password!";
            
            $log_stmt = $sqlite_db->prepare("INSERT INTO logs (event, time) VALUES (?, DATETIME('now'))");
            $log_stmt->execute(["Failed login attempt for username: $username"]);
            $num1 = rand(1, 10);
            $num2 = rand(1, 10);
            $_SESSION['captcha_sum'] = $num1 + $num2;
            $_SESSION['captcha_text'] = "$num1 + $num2 = ?";
        }
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Euroleague</title>
    <link rel="stylesheet" href="css/style3_responsive.css">
    <style>
        .login-box { width: 300px; margin: 100px auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; background: #f9f9f9; }
        .error { color: red; font-size: 0.9em; }
        input { width: 100%; margin-bottom: 10px; padding: 8px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background-color: #003366; color: white; border: none; cursor: pointer; }
        .remember-me-container { margin-bottom: 10px; display: flex; align-items: center; gap: 8px; }
        .remember-me-container input[type="checkbox"] { width: auto; margin: 0; flex-shrink: 0; }
        .remember-me-container label { margin: 0; }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>Login</h2>
        <?php if($error) echo "<p class='error'>$error</p>"; ?>
        <form method="POST">
            <label>Username</label>
            <input type="text" name="username" required>
            <label>Password</label>
            <input type="password" name="password" required>
            <div class="remember-me-container">
                <label for="remember">Remember me</label>
                <input type="checkbox" name="remember" id="remember">
            </div>
            <div class="captcha-container">
                <label>CAPTCHA: <strong><?php echo $_SESSION['captcha_text']; ?></strong></label>
                <input type="number" name="captcha_response" required>
            </div>
            <button type="submit">Log In</button>
        </form>
    </div>
</body>
</html>