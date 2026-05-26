<?php
session_start();
require_once '../php/db_pdo.php';

// VULNERABILITY DEMO: SQL INJECTION
// http://localhost:8000/html/dashboard.php?team=%27%20UNION%20SELECT%201,%20username,%20password_hash,%20role,%205,%206,%207%20FROM%20users%20%23
// $team = $_GET['team'] ?? '';
// $query = "SELECT * FROM players WHERE team = '$team' ORDER BY id DESC";
// $stmt = $pdo->query($query);
// $players = $stmt ? $stmt->fetchAll(PDO::FETCH_ASSOC) : [];

// SAFE VERSION 
try {
    $team = $_GET['team'] ?? '';
    if ($team !== '') {
        $stmt = $pdo->prepare("SELECT * FROM players WHERE team = ? ORDER BY id DESC");
        $stmt->execute([$team]);
    } else {
        $stmt = $pdo->query("SELECT * FROM players ORDER BY id DESC");
        $stmt->execute();
    }

    $players = $stmt->fetchAll(PDO::FETCH_ASSOC);
} catch (PDOException $e) {
    $players = [];
}


?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title> Dashboard Jucători </title>
        <link rel="stylesheet" href="/css/style3_responsive.css">
    </head>
    <body>
        <?php include __DIR__ . '/_nav.html'; ?>
        <h1> DASHBOARD JUCĂTORI </h1>
        <br>
        <h2> Această pagină vizualizează dinamic jucătorii din baza de date </h2>
        <br>
        
        <div class="dashboard-grid">
            <?php if (count($players) > 0): ?>
                <?php foreach ($players as $row): ?>
                    <div class="card">
                        <!-- VULNERABILITY DEMO: XSS -->
                        <!-- <img src="/images/players/<?php echo $row['image_link']; ?>"
                            alt="<?php echo $row['name']; ?>"
                            width="150" height="150"
                            style="object-fit: cover; border-radius: 5px;">
                        <h4><?php echo $row['name']; ?></h4>
                        <p><strong>Echipă:</strong> <?php echo $row['team']; ?></p>
                        <p><strong>Poziție:</strong> <?php echo $row['position']; ?></p>
                        <p><strong>Înălțime:</strong> <?php echo $row['height']; ?> cm</p> -->

                        <!-- SAFE VERSION -->
                        <img src="/images/players/<?php echo htmlspecialchars($row['image_link']); ?>" 
                             alt="<?php echo htmlspecialchars($row['name']); ?>" 
                             width="150" height="150" 
                             style="object-fit: cover; border-radius: 5px;">
                        <h4> <?php echo htmlspecialchars($row['name']); ?> </h4>
                        <p> <strong>Echipă:</strong> <?php echo htmlspecialchars($row['team']); ?> </p>
                        <p> <strong>Poziție:</strong> <?php echo htmlspecialchars($row['position']); ?> </p>
                        <p> <strong>Înălțime:</strong> <?php echo htmlspecialchars($row['height']); ?> cm </p>
                    </div>
                <?php endforeach; ?>
            <?php else: ?>
                <p>Momentan nu există jucători în baza de date.</p>
            <?php endif; ?>
        </div>
    </body>
</html>