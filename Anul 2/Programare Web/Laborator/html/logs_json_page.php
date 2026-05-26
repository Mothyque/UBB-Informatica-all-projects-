<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    header("Location: ../php/login.php");
    exit;
}
?>
<!DOCTYPE html>
<html lang="ro">
    <head>
        <meta charset="UTF-8">
        <title>Jurnal Activitate JSON</title>
        <link rel="stylesheet" href="/css/style1_horizontal.css">
    </head>
    <body>
    <?php include __DIR__ . '/_nav.html'; ?>
    
    <h1 style="text-align:center; margin-top: 20px;"> Jurnal Activitate (JSON) </h1>
    
    <div id="error-banner" class="error-message"></div>

    <table id="logs-table">
        <thead>
            <tr>
                <th>#ID</th>
                <th>Eveniment</th>
                <th>Dată / Oră</th>
            </tr>
        </thead>
        <tbody id="logs-body">
            </tbody>
    </table>

    <div class="pagination">
        <button id="btn-prev" disabled>← Previous 5</button>
        <span id="page-info">Pagina — / —</span>
        <button id="btn-next" disabled>Next 5 →</button>
    </div>
    <script src="/js/json_pagination.js"></script>
</body>
</html>