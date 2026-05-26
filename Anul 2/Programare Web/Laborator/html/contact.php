<!DOCTYPE html>
<html lang="ro">
<head> 
    <meta charset="UTF-8">
    <title>Contact Scouting</title>
    <link rel="stylesheet" href="/css/style3_responsive.css">
    <div id="nav-placeholder"></div>
<head>
<body>    
    <?php include __DIR__ . '/_nav.html'; ?>
    
    <h1> APLICĂ PENTRU SCOUTING </h1>
    <div style="margin: 20px;">
        <form id="contactForm" novalidate>
            <fieldset>
                <legend>Trimite-ne datele tale</legend>
                
                <b>Nume Aplicant:</b><br>
                <input type="text" id="scoutName"><br><br>

                <b>Creează o parolă de cont:</b><br>
                <input type="password" id="scoutPassword"><br><br>

                <b>Încarcă Video Highlight (doar .mp4):</b><br>
                <input type="file" id="scoutVideo" accept=".mp4"><br><br>

                <b>Mesaj de prezentare:</b><br>
                <textarea id="scoutMessage" rows="4" cols="40"></textarea><br><br>

                <b>Sunt de acord cu prelucrarea datelor:</b>
                <input type="checkbox" id="scoutTerms" value="Da"><br><br>

                <input type="submit" value="Trimite Aplicație">
            </fieldset>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/js/data.js"></script>
    <script src="/js/forms.js"></script>
</body>
</html>