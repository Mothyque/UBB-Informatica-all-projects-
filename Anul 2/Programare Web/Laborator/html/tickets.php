<!DOCTYPE html>
<html lang="ro">
<head> 
    <meta charset="UTF-8">
    <title>Cumpără Bilete</title>
    <link rel="stylesheet" href="/css/style3_responsive.css">
</head>
<body>
    <?php include __DIR__ . '/_nav.html'; ?>
    <h1> CUMPĂRĂ BILETE EUROLEAGUE </h1>
    <div style="margin: 20px;">
        <form id="ticketsForm" novalidate>
            <fieldset>
                <legend>Rezervare Bilete</legend>
                <b>Email:</b><br>
                <input type="email" name="ticketEmail" id="ticketEmail"><br><br>

                <b>Țara gazdă:</b><br>
                <select id="countrySelect" name="country">
                    <option value="">-- Alege o țară --</option>
                    <option value="Spania">Spania</option>
                    <option value="Turcia">Turcia</option>
                    <option value="Serbia">Serbia</option>
                    <option value="Franta">Franța</option>
                </select><br><br>

                <b>Echipa:</b><br>
                <select id="teamSelect" name="team">
                    <option value="">-- Alege întâi țara --</option>
                </select><br><br>

                <b>Data Meciului:</b><br>
                <input type="date" name="matchDate" id="matchDate"><br><br>

                <b>Număr bilete (Max 5):</b><br>
                <input type="number" name="ticketCount" id="ticketCount" min="1" max="5"><br><br>

                <b>Tip Bilet:</b><br>
                Standard <input type="radio" name="ticketType" value="Standard" checked>
                VIP <input type="radio" name="ticketType" value="VIP"><br><br>

                <input type="submit" value="Rezervă Bilete">
            </fieldset>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/js/data.js"></script>
    <script src="/js/forms.js"></script>
</body>
</html>