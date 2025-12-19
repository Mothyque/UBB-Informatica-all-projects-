USE Euroleague
GO

-- Operatii CRUD pentru tabela Teams

CREATE FUNCTION formatText(@text NVARCHAR(100))
RETURNS NVARCHAR(100)
AS 
BEGIN
	IF @text IS NULL RETURN NULL;
	IF LEN(@text) = 0 RETURN '';
	RETURN UPPER(LEFT(@text, 1)) + LOWER(SUBSTRING(@text, 2, LEN(@text)));
END

CREATE FUNCTION valideazaAn (@an INT)
RETURNS BIT
AS
BEGIN
	IF @an IS NULL RETURN 0;
	IF @an >= 1900 AND @an <= YEAR(GETDATE())
		RETURN 1;
	RETURN 0;
END

CREATE PROCEDURE createTeam @teamName NVARCHAR(100), @city NVARCHAR(100), @arenaID INT, @foundYear INT, @coachID INT, @titles INT, @newTeamID INT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;
	IF dbo.valideazaAn(@foundYear) = 0
	BEGIN
		RAISERROR('Eroare: Anul infiintarii este invalid (trebuie sa fie intre 1900 si anul curent', 16, 1);
		RETURN;
	END
	
	DECLARE @formattedCity NVARCHAR(100) = dbo.formatText(@city);

	INSERT INTO Teams VALUES(@teamName, @formattedCity, @arenaID, @foundYear, @coachID, @titles);

	SET @newTeamID = SCOPE_IDENTITY();
	PRINT 'Echipa adaugata cu ID-ul: ' + CAST(@newTeamID AS NVARCHAR(10));
END

CREATE PROCEDURE readTeam @teamID INT 
AS 
BEGIN
	SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM Teams WHERE TeamID = @teamID)
    BEGIN
        RAISERROR('Eroare: Echipa cu acest ID nu exista!', 16, 1);
        RETURN;
    END
	SELECT TeamID, TeamName, City, ArenaID, FoundYear, CoachID, Titles FROM Teams WHERE TeamID = @teamID 
END


CREATE PROCEDURE updateTeam @teamID INT, @teamName NVARCHAR(100), @city NVARCHAR(100), @arenaID INT, @foundYear INT, @coachID INT, @titles INT
AS
BEGIN
    SET NOCOUNT ON;

    IF NOT EXISTS (SELECT 1 FROM Teams WHERE TeamID = @teamID)
    BEGIN
        RAISERROR('Eroare: Echipa cu acest ID nu exista!', 16, 1);
        RETURN;
    END

    IF dbo.valideazaAn(@foundYear) = 0
    BEGIN
        RAISERROR('Eroare: Anul infiintarii este invalid.', 16, 1);
        RETURN;
    END
    DECLARE @formattedCity NVARCHAR(100) = dbo.formatText(@city);

    UPDATE Teams
    SET TeamName = @teamName, City = @formattedCity, ArenaID = @arenaID, FoundYear = @foundYear, CoachID = @coachID, Titles = @titles WHERE TeamID = @teamID;
    PRINT 'Datele echipei au fost actualizate cu succes.';
END

CREATE PROCEDURE deleteTeam @teamID INT
AS 
BEGIN
	SET NOCOUNT ON;
	IF NOT EXISTS (SELECT 1 FROM Teams WHERE TeamID = @teamID)
    BEGIN
        RAISERROR('Eroare: Echipa nu exista.', 16, 1);
        RETURN;
    END
    BEGIN
        DELETE FROM TeamsSponsors WHERE TeamID = @teamID;
        DELETE FROM Teams WHERE TeamID = @teamID;
        PRINT 'Echipa a fost stearsa';
    END
END

DECLARE @idEchipaGenerat INT;
EXEC createTeam 'Echipa tare', 'OrasulMeu', 10,2005,1,10, @idEchipaGenerat OUTPUT;
EXEC readTeam 18;
EXEC updateTeam 18, 'Echipa cea mai tare', 'Oras', 10, 2001,1,20;
EXEC deleteTeam 18;
DBCC CHECKIDENT ('Teams', reseed,17);

SELECT * FROM Teams

-- Operatii CRUD pentru tabela Sponsors

CREATE PROCEDURE createSponsor @sponsorName NVARCHAR(100), @industry NVARCHAR(50), @country NVARCHAR(50), @newSponsorID INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @formattedCountry NVARCHAR(50) = dbo.formatText(@country);

    INSERT INTO Sponsors (SponsorName, Industry, Country) VALUES (@sponsorName, @industry, @formattedCountry);

    SET @newSponsorID = SCOPE_IDENTITY();
    PRINT 'Sponsor adaugat cu ID-ul: ' + CAST(@newSponsorID AS NVARCHAR(10));
END

CREATE PROCEDURE readSponsor @sponsorID INT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM Sponsors WHERE SponsorID = @sponsorID)
    BEGIN
        RAISERROR('Eroare: Sponsorul cu acest ID nu exista!', 16, 1);
        RETURN;
    END

    SELECT SponsorID, SponsorName, Industry, Country FROM Sponsors WHERE SponsorID = @sponsorID;
END

CREATE PROCEDURE updateSponsor @sponsorID INT, @sponsorName NVARCHAR(100), @industry NVARCHAR(50), @country NVARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM Sponsors WHERE SponsorID = @sponsorID)
    BEGIN
        RAISERROR('Eroare: Sponsorul cu acest ID nu exista!', 16, 1);
        RETURN;
    END

    DECLARE @formattedCountry NVARCHAR(50) = dbo.formatText(@country);

    UPDATE Sponsors
    SET SponsorName = @sponsorName, Industry = @industry, Country = @formattedCountry
    WHERE SponsorID = @sponsorID;

    PRINT 'Datele sponsorului au fost actualizate cu succes.';
END

CREATE PROCEDURE deleteSponsor @sponsorID INT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM Sponsors WHERE SponsorID = @sponsorID)
    BEGIN
        RAISERROR('Eroare: Sponsorul nu exista.', 16, 1);
        RETURN;
    END
    BEGIN
        DELETE FROM TeamsSponsors WHERE SponsorID = @sponsorID;
        DELETE FROM Sponsors WHERE SponsorID = @sponsorID;
        PRINT 'Sponsorul a fost sters';
    END
END

DECLARE @idSponsorGenerat INT;
EXEC createSponsor 'Dedeman', 'Bricolaj', 'romania', @idSponsorGenerat OUTPUT;
EXEC readSponsor 11;
EXEC updateSponsor 11, 'Dedeman SRL', 'Retail & DIY', 'Romania';
EXEC deleteSponsor 11;
DBCC CHECKIDENT ('Sponsors', reseed, 10);

SELECT * FROM Sponsors

-- Operatii CRUD pentru tabela TeamsSponsors

CREATE PROCEDURE createTeamSponsor @teamID INT, @sponsorID INT, @sponsorshipStart DATE, @sponsorshipEnd DATE
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM Teams WHERE TeamID = @teamID) OR NOT EXISTS (SELECT 1 FROM Sponsors WHERE SponsorID = @sponsorID)
    BEGIN
        RAISERROR('Eroare: Echipa sau Sponsorul nu exista.', 16, 1);
        RETURN;
    END

    IF @sponsorshipStart > @sponsorshipEnd
    BEGIN
        RAISERROR('Eroare: Data de inceput nu poate fi dupa data de sfarsit.', 16, 1);
        RETURN;
    END

    INSERT INTO TeamsSponsors (TeamID, SponsorID, SponsorshipStart, SponsorshipEnd) VALUES (@teamID, @sponsorID, @sponsorshipStart, @sponsorshipEnd);
    PRINT 'Contractul a fost creat cu succes.';
END

CREATE PROCEDURE readTeamSponsor @teamID INT, @sponsorID INT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM TeamsSponsors WHERE TeamID = @teamID AND SponsorID = @sponsorID)
    BEGIN
        RAISERROR('Eroare: Nu exista un contract intre aceste doua entitati.', 16, 1);
        RETURN;
    END

    SELECT T.TeamName, S.SponsorName, TS.SponsorshipStart, TS.SponsorshipEnd 
    FROM TeamsSponsors TS
    JOIN Teams T ON TS.TeamID = T.TeamID
    JOIN Sponsors S ON TS.SponsorID = S.SponsorID
    WHERE TS.TeamID = @teamID AND TS.SponsorID = @sponsorID;
END

CREATE PROCEDURE updateTeamSponsor @teamID INT, @sponsorID INT, @sponsorshipStart DATE, @sponsorshipEnd DATE
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM TeamsSponsors WHERE TeamID = @teamID AND SponsorID = @sponsorID)
    BEGIN
        RAISERROR('Eroare: Contractul pe care incerci sa il modifici nu exista.', 16, 1);
        RETURN;
    END

    IF @sponsorshipStart > @sponsorshipEnd
    BEGIN
        RAISERROR('Eroare: Data de inceput nu poate fi dupa data de sfarsit.', 16, 1);
        RETURN;
    END

    UPDATE TeamsSponsors 
    SET SponsorshipStart = @sponsorshipStart, SponsorshipEnd = @sponsorshipEnd
    WHERE TeamID = @teamID AND SponsorID = @sponsorID;
    
    PRINT 'Contractul a fost actualizat cu succes.';
END

CREATE PROCEDURE deleteTeamSponsor @teamID INT, @sponsorID INT
AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM TeamsSponsors WHERE TeamID = @teamID AND SponsorID = @sponsorID)
    BEGIN
        RAISERROR('Eroare: Contractul nu exista.', 16, 1);
        RETURN;
    END

    DELETE FROM TeamsSponsors WHERE TeamID = @teamID AND SponsorID = @sponsorID;
    PRINT 'Contractul a fost sters.';
END

EXEC createTeamSponsor 6, 7, '2024-01-01', '2025-01-01';
EXEC readTeamSponsor 6, 7;
EXEC updateTeamSponsor 6, 7, '2024-01-01', '2028-01-01';
EXEC deleteTeamSponsor 6, 7;

SELECT * FROM TeamsSponsors


-- Tabelele de log pentru tabelele implicate in M:N
CREATE TABLE Teams_Log (
    LogID INT IDENTITY(1,1) PRIMARY KEY, 
    TipOperatie CHAR(6),               
    DataOperatie DATETIME DEFAULT GETDATE(),
    Utilizator NVARCHAR(100) DEFAULT SYSTEM_USER,
    TeamID INT,
    TeamName NVARCHAR(100),
    City NVARCHAR(100),
    ArenaID INT,
    FoundYear INT,
    CoachID INT,
    Titles INT
);

CREATE TABLE Sponsors_Log (
    LogID INT IDENTITY(1,1) PRIMARY KEY,
    TipOperatie CHAR(6),
    DataOperatie DATETIME DEFAULT GETDATE(),
    Utilizator NVARCHAR(100) DEFAULT SYSTEM_USER,
    SponsorID INT,
    SponsorName NVARCHAR(100),
    Industry NVARCHAR(50),
    Country NVARCHAR(50)
);

CREATE TABLE TeamsSponsors_Log (
    LogID INT IDENTITY(1,1) PRIMARY KEY,
    TipOperatie CHAR(6),
    DataOperatie DATETIME DEFAULT GETDATE(),
    Utilizator NVARCHAR(100) DEFAULT SYSTEM_USER,
    TeamID INT,
    SponsorID INT,
    SponsorshipStart DATE,
    SponsorshipEnd DATE
);

-- Triggerele corespunzatoare operatiilor de delete / update pe tabelele implicate

CREATE TRIGGER triggerTeams
ON Teams
AFTER UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @TipOp CHAR(6);
    
    IF EXISTS (SELECT * FROM inserted)
        SET @TipOp = 'UPDATE';
    ELSE
        SET @TipOp = 'DELETE';

    INSERT INTO Teams_Log (TipOperatie, Utilizator, TeamID, TeamName, City, ArenaID, FoundYear, CoachID, Titles)
    SELECT @TipOp, SYSTEM_USER, TeamID, TeamName, City, ArenaID, FoundYear, CoachID, Titles
    FROM deleted;

    PRINT 'Operatiune pe Teams inregistrata in Log.';
END

CREATE TRIGGER triggerSponsors
ON Sponsors
AFTER UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @TipOp CHAR(6);
    IF EXISTS (SELECT * FROM inserted)
        SET @TipOp = 'UPDATE';
    ELSE
        SET @TipOp = 'DELETE';

    INSERT INTO Sponsors_Log (TipOperatie, Utilizator, SponsorID, SponsorName, Industry, Country)
    SELECT @TipOp, SYSTEM_USER, SponsorID, SponsorName, Industry, Country
    FROM deleted;

    PRINT 'Operatiune pe Sponsors inregistrata in Log.';
END

CREATE TRIGGER triggerTeamsSponsors
ON TeamsSponsors
AFTER UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @TipOp CHAR(6);
    IF EXISTS (SELECT * FROM inserted)
        SET @TipOp = 'UPDATE';
    ELSE
        SET @TipOp = 'DELETE';

    INSERT INTO TeamsSponsors_Log (TipOperatie, Utilizator, TeamID, SponsorID, SponsorshipStart, SponsorshipEnd)
    SELECT @TipOp, SYSTEM_USER, TeamID, SponsorID, SponsorshipStart, SponsorshipEnd
    FROM deleted;

    PRINT 'Operatiune pe TeamsSponsors inregistrata in Log.';
END

SELECT * FROM Teams_Log

-- Views pentru tabelele implicate

CREATE VIEW viewDetaliiContracte
AS
SELECT 
    T.TeamName,
    S.SponsorName,
    S.Industry,
    TS.SponsorshipStart,
    TS.SponsorshipEnd
FROM TeamsSponsors TS
INNER JOIN Teams T ON TS.TeamID = T.TeamID
INNER JOIN Sponsors S ON TS.SponsorID = S.SponsorID

CREATE NONCLUSTERED INDEX IX_Sponsors_SponsorName_Covering
ON Sponsors (SponsorName)
INCLUDE (Industry);

SELECT * FROM viewDetaliiContracte WHERE SponsorName = 'Nike';


CREATE OR ALTER VIEW viewContracteActive
AS
SELECT 
    T.TeamName,
    S.SponsorName,
    TS.SponsorshipStart,
    TS.SponsorshipEnd
FROM TeamsSponsors TS
INNER JOIN Teams T ON TS.TeamID = T.TeamID
INNER JOIN Sponsors S ON TS.SponsorID = S.SponsorID
WHERE GETDATE() BETWEEN TS.SponsorshipStart AND TS.SponsorshipEnd;

CREATE NONCLUSTERED INDEX IX_TeamsSponsors_Dates
ON TeamsSponsors (SponsorshipStart, SponsorshipEnd)
INCLUDE (TeamID, SponsorID); 
GO

SELECT * FROM viewContracteActive