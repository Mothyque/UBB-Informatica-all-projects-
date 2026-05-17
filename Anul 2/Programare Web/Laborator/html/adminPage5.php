<?php
session_start();
require_once '../php/db_pdo.php';
if (!isset($_SESSION['user_id']) || $_SESSION['role'] !== 'admin') {
    header("Location: ../html/mainPage5.php");
    exit;
}
try {
    $teamQuery = $pdo->query("SELECT DISTINCT team FROM players WHERE team IS NOT NULL AND team != ''");
    $existingTeams = $teamQuery->fetchAll(PDO::FETCH_COLUMN);

    $posQuery = $pdo->query("SELECT DISTINCT position FROM players WHERE position IS NOT NULL AND position != ''");
    $existingPositions = $posQuery->fetchAll(PDO::FETCH_COLUMN);

    $playerQuery = $pdo->query("SELECT name FROM players");
    $existingPlayers = $playerQuery->fetchAll(PDO::FETCH_COLUMN);
} catch (PDOException $e) {
    $existingTeams = [];
    $existingPositions = [];
    $existingPlayers = [];
}
?>

<!DOCTYPE html>
<html lang = "ro">
    <head> 
        <meta charset="UTF-8">
        <title> Admin Page</title>
        <link rel="stylesheet" href="/css/style1_horizontal.css">
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
        <h1> PAGINĂ ADMIN</h1>
        <br>
        <h2> Această pagină este dedicată strict adminilor pentru gestionarea jucătorilor </h2>
        <br>
        <h3> Dacă nu sunteți un admin, vă rog să părăsiți pagina</h3>
        <br>   
        <p> <span> Notă informativă: </span> Vă rugăm să verificați datele <strong> de două ori </strong>înainte de a le salva. </p>
        <div class="forms-container">
            <form id = "adminForm" name = "add_player" action="#" novalidate> 
                <fieldset> 
                    <legend> Adaugă jucător nou </legend>
                    <b>
                        Ligă:
                        <input type = "text" name = "Player League" size = "16" value = "Euroleague" readonly>
                        <br>
                    </b>
                    <b> 
                        Nume:
                        <input type = "text" name = "Player Name" size = "16" maxlength="50">
                        <br>
                    </b> 
                    <b> 
                        Țară:
                        <input type = "text" name = "Player Country" size = "16" maxlength="30">
                        <br>
                    </b> 
                    <b> 
                        Poziție:
                        PG <input type = "radio" name = "Player Position" value="PG" checked>
                        SG <input type = "radio" name = "Player Position" value="SG">
                        SF <input type = "radio" name = "Player Position" value="SF">
                        PF <input type = "radio" name = "Player Position" value="PF">
                        C <input type = "radio" name = "Player Position" value="C">
                        <br>
                    </b> 
                    <b> 
                        Înălțime (cm):
                        <input type = "text" name = "Player Height" size = "16" maxlength = "3">
                        <br>
                    </b> 
                    <b> 
                        Greutate (kg):
                        <input type = "text" name = "Player Weight" size = "16" maxlength = "3">
                        <br>
                    </b> 
                    <b>
                        Echipă:
                            <select name  = "Player Team">
                                <option>
                                    Anadolu Efes Istanbul
                                </option>
                                <option>
                                    AS Monaco
                                </option>
                                <option>
                                    Crvena Zvezda Meridianbet Belgrade
                                </option>
                                <option>
                                    Dubai Basketball
                                </option>
                                <option>
                                    EA7 Emporio Armani Milan
                                </option>
                                <option selected>
                                    FC Barcelona
                                </option>
                                <option>
                                    FC Bayern Munich
                                </option>
                                <option>
                                    Fenerbahce Beko Istanbul
                                </option>
                            </select>
                            <br>
                    </b>

                    <b> 
                        Număr Tricou:
                        <input type = "number" name = "Player Number" max="99" step="1">
                        <br>
                    </b> 
                    <b>
                        Experiență NBA:
                        <input type="checkbox" name = "Player Nba" value = "Yes">
                        <br>
                    </b>
                    <b>
                        Accidentat:
                        <input type="checkbox" name = "Player Injured" value = "Yes" disabled>
                        <br>
                    </b>
                    <b>
                        Descriere:
                        <br>
                        <textarea name = "Player Description" rows = "3" cols = "50"></textarea>
                    </b>
                    <b>
                        Aptitudini:
                        <br>
                        <select name = "Player Skills" multiple size = "5">
                            <option value = "Shooting">Shooting</option>
                            <option value = "Passing">Passing</option>
                            <option value = "Dribbling">Dribbling</option>
                            <option value = "Defense">Defense</option>
                            <option value = "Rebounding">Rebounding</option>
                        </select>
                        <br><br>
                    </b>
                    <input type = "submit" value = "Adaugă Jucător">
                    <input type = "reset" value = "Resetează Formular">
                </fieldset>
            </form>

        <div id="adminForm" class="add-player-dashboard-form">
            <form action="../php/process_player.php" method="POST" enctype="multipart/form-data">
                <legend>Adauga un jucator nou in Dashboard</legend>
                <label>Player Name:</label>
                <input type="text" name="name" required>

                <label>Nume echipa:</label>
                <input type="text" name="team" list="teams_data" required placeholder="Scrie sau selectează echipa...">
                <datalist id="teams_data">
                    <?php foreach ($existingTeams as $teamName): ?>
                        <option value="<?php echo htmlspecialchars($teamName); ?>">
                    <?php endforeach; ?>
                </datalist>

                <label>Poziție:</label>
                <input type="text" name="position" list="positions_data" required placeholder="Scrie sau selectează poziția...">
                <datalist id="positions_data">
                    <?php foreach ($existingPositions as $pos): ?>
                        <option value="<?php echo htmlspecialchars($pos); ?>">
                    <?php endforeach; ?>
                </datalist>

                <label>Înălțime (cm):</label>
                <input type="number" name="height">

                <label>Fotografie Jucător:</label>
                <input type="file" name="player_image" accept="image/*" required>

                <button type="submit" name="add_player">Salvează Jucător</button>
            </form>
        </div>

        <div class="delete-player-dashboard-form">
            <?php
                if (empty($_SESSION['csrf_token'])) {
                    $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
                }
            ?>
            <form action="../php/delete_player.php" method="POST">
                <fieldset>
                    <legend>Șterge Jucător</legend>

                    <label><b>Selectează Nume:</b></label>
                    <input type="text" name="player_name" list="players_to_delete" required placeholder="Caută jucător...">
                    <datalist id="players_to_delete">
                        <?php foreach ($existingPlayers as $pName): ?>
                            <option value="<?php echo htmlspecialchars($pName); ?>">
                        <?php endforeach; ?>
                    </datalist>
                            
                    <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
                    <br><br>
                    <input type="submit" name="delete_player" value="Șterge" class="btn-delete">
                </fieldset>
            </form>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/js/data.js"></script>
    <script src="/js/forms.js"></script>
    </body>
</html>