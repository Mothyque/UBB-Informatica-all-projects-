<?php
session_start();
require_once '../php/db_pdo.php';

try {
    $stmt = $pdo->query("SELECT * FROM players ORDER BY id DESC");
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
        <nav>
            <ul class="meniu_principal">
                <li>
                    <a href="adminPage5.php">Admin Panel</a>
                </li>
                <li>
                    <a href="mainPage5.php">Pagina principală</a>
                </li>
                <li>
                    <a href="dashboard.php"> Dashboard jucători</a>
                </li>
                <li>
                    <a href="sprites.htm">Sponsori</a>
                </li>
                <li>
                    <a href="tickets.htm">Bilete Meci</a>
                </li>
                <li>
                    <a href="contact.htm">Contact Scouting</a>
                </li>
            </ul>
        </nav>
        <h1> DASHBOARD JUCĂTORI </h1>
        <br>
        <h2> Această pagină vizualizează dinamic jucătorii din baza de date </h2>
        <br>
        
        <div class="dashboard-grid">
            <?php if (count($players) > 0): ?>
                <?php foreach ($players as $row): ?>
                    <div class="card">
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