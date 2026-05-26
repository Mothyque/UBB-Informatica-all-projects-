<?php session_start(); ?>
<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>Editare Jurnal (jQuery)</title>
    <link rel="stylesheet" href="/css/style1_horizontal.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <?php include __DIR__ . '/_nav.html'; ?>

    <h1 style="text-align:center;">Editare Înregistrare Jurnal</h1>

    <div style="width: 50%; margin: 0 auto;">
        <label>Selectează ID:</label>
        <select id="log-selector">
            <option value="">-- Alege ID --</option>
        </select>

        <form id="edit-form" style="margin-top:20px;">
            <input type="hidden" id="log-id">
            <label>Eveniment:</label>
            <input type="text" id="log-event" style="width:100%"><br><br>
            <label>Dată/Oră:</label>
            <input type="text" id="log-time" style="width:100%"><br><br>
            <button type="button" id="save-btn" disabled>Salvează</button>
        </form>
    </div>

    <script src="/js/jquery_edit.js"></script>
</body>
</html>