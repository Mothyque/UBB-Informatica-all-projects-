<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    header("Location: ../php/login.php");
    exit;
}
require_once '../php/db_sqlite.php';

$per_page = 5;
$page = isset($_GET['page']) ? (int)$_GET['page'] : 1;
if ($page < 1) {
    $page = 1;
}
$offset = ($page - 1) * $per_page;

$count_stmt = $sqlite_db->prepare("SELECT COUNT(*) as total FROM logs");
$count_stmt->execute();
$total = (int)$count_stmt->fetch(PDO::FETCH_ASSOC)['total'];
$total_pages = (int)ceil($total / $per_page);

$stmt = $sqlite_db->prepare("SELECT id, event, time FROM logs ORDER BY time DESC LIMIT :limit OFFSET :offset");
$stmt->bindValue(':limit', $per_page, PDO::PARAM_INT);
$stmt->bindValue(':offset', $offset, PDO::PARAM_INT);
$stmt->execute();
$records = $stmt->fetchAll(PDO::FETCH_ASSOC);
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>Jurnal Activitate (Backend)</title>
    <link rel="stylesheet" href="/css/style1_horizontal.css">
</head>
<body>
    <?php include __DIR__ . '/_nav.html'; ?>

    <h1 style="text-align:center;"> Jurnal Activitate (Server-Side) </h1>

    <table id="logs-table">
        <thead>
            <tr><th>#ID</th><th>Eveniment</th><th>Dată / Oră</th></tr>
        </thead>
        <tbody>
            <?php foreach ($records as $row): ?>
            <tr>
                <td><?= htmlspecialchars($row['id']) ?></td>
                <td><?= htmlspecialchars($row['event']) ?></td>
                <td><?= htmlspecialchars($row['time']) ?></td>
            </tr>
            <?php endforeach; ?>
        </tbody>
    </table>

    <div class="pagination">
        <form method="GET" style="display:inline;">
            <input type="hidden" name="page" value="<?= max(1, $page - 1) ?>">
            <button type="submit" <?= ($page <= 1) ? 'disabled' : '' ?>>← Previous 5</button>
        </form>
        
        <span>Pagina <?= $page ?> / <?= $total_pages ?></span>
        
        <form method="GET" style="display:inline;">
            <input type="hidden" name="page" value="<?= min($total_pages, $page + 1) ?>">
            <button type="submit" <?= ($page >= $total_pages) ? 'disabled' : '' ?>>Next 5 →</button>
        </form>
    </div>
</body>
</html>