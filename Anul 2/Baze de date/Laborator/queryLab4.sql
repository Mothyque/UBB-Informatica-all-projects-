USE Euroleague
GO

CREATE TABLE Legaturi_Eliminate (
	IdLog INT IDENTITY(1,1) PRIMARY KEY, 
	NumeTabelaSt NVARCHAR(50),
	IdSt INT,
	NumeTabelaDr NVARCHAR(50),
	IdDr INT,
	);

CREATE PROCEDURE Transformare_1N_in_N1_ArenaTeams
-- Aveam initial relatia 1:N intre arena si teams: O arena poate gazdui mai multe echipe
-- Transformam relatia in N:1: O echipa are mai multe arene (principala, de antrenament, pentru meciuri amicale etc)
AS 
BEGIN
    ALTER TABLE Arena ADD TeamID INT;
    
    DECLARE @UpdateSql NVARCHAR(MAX);
    SET @UpdateSql = N'UPDATE Arena SET TeamID = (SELECT MAX(TeamID) FROM Teams WHERE Teams.ArenaID = Arena.ArenaID)';
    EXEC sp_executesql @UpdateSql;

    -- Salvam in Legaturi_Eliminate datele pierdute in urma transformarii
    -- Daca inainte 2 echipe aveau aceeasi arena, acum cea cu id-ul mai mare a pastrat arena
    INSERT INTO Legaturi_Eliminate (NumeTabelaSt, IdSt, NumeTabelaDr, IdDr)
    SELECT 
        'Arena',
        T.ArenaID,
        'Teams',
        T.TeamID
    FROM Teams T
    WHERE T.TeamID <> (SELECT MAX(T2.TeamID) FROM Teams T2 WHERE T2.ArenaID = T.ArenaID);

    DECLARE @ConstraintName NVARCHAR(128);
    SELECT @ConstraintName = name
    FROM sys.foreign_keys
    WHERE parent_object_id = object_id('Teams') AND referenced_object_id = object_id('Arena');

    DECLARE @SqlDrop NVARCHAR(256) = 'ALTER TABLE Teams DROP CONSTRAINT ' + @ConstraintName;
    EXEC sp_executesql @SqlDrop;

    ALTER TABLE Teams DROP Column ArenaID;

    DECLARE @SqlAddFK NVARCHAR(256);
    SET @SqlAddFK = N'ALTER TABLE Arena ADD Constraint FK_Arena_Teams FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)';
    EXEC sp_executesql @SqlAddFK;

    PRINT 'Transformare Arena-Teams realizata cu succes!';
END

EXEC Transformare_1N_in_N1_ArenaTeams

CREATE PROCEDURE Transformare_1N_in_MN_GamesReferees
-- Aveam initial relatia 1:N intre Games si Referees: un meci poate avea mai multi arbitrii
-- Transformam in M:N: Un meci poate avea mai multi arbitrii dar si un arbitru poate avea mai multe meciuri
AS 
BEGIN
    IF OBJECT_ID('GamesReferees','U') IS NULL
    BEGIN
        CREATE TABLE GamesReferees(
        GameID INT NOT NULL,
        RefereeID INT NOT NULL,
        PRIMARY KEY (GameID,RefereeID),
        FOREIGN KEY (GameID) REFERENCES Games(GameID),
        FOREIGN KEY (RefereeID) REFERENCES Referees(RefereeID)
        );
    END

    -- Mutam meciurile viitoare atasate arbitrilor in noua tabela
    INSERT INTO GamesReferees(GameID, RefereeID)
    SELECT NextMatchID, RefereeID
    FROM Referees
    WHERE NextMatchID IS NOT NULL;

    DECLARE @ConstraintName NVARCHAR(128);
    SELECT @ConstraintName = name
    FROM sys.foreign_keys
    WHERE parent_object_id = object_id('Referees')
      AND referenced_object_id = object_id('Games');
   
    DECLARE @SqlDrop NVARCHAR(256) = 'ALTER TABLE Referees DROP CONSTRAINT ' + @ConstraintName;
    EXEC sp_executesql @SqlDrop;

    ALTER TABLE Referees DROP COLUMN NextMatchID;

    PRINT 'Transformare 1:N -> M:N realizata cu succes!';
END

EXEC Transformare_1N_in_MN_GamesReferees

CREATE PROCEDURE Transformare_MN_in_1N_SponsorsTeams
-- Avem initial relatia M:N intre sponsori si echipe: o echipa poate avea mai multi sponsori, dar si un sponsor poate avea contract cu mai multe echipe
-- Transformam in 1:N: o echipa poate avea mai multi sponsori
AS
BEGIN
    ALTER TABLE Sponsors ADD TeamID INT;

    -- Convenim ca daca un sponsor are contract cu mai multe echipe o pastreaza pe cea cu id-ul maxim
    DECLARE @UpdateSql NVARCHAR(MAX);
    SET @UpdateSql = N'
        UPDATE Sponsors
        SET TeamID = (
            SELECT MAX(TeamID)
            FROM TeamsSponsors TS
            WHERE TS.SponsorID = Sponsors.SponsorID
        )';
    EXEC sp_executesql @UpdateSql;

    -- Salvam datele pierdute (Echipele cu id-ul diferit de cel maxim care avea contract cu un sponsor)
    INSERT INTO Legaturi_Eliminate (NumeTabelaSt, IdSt, NumeTabelaDr, IdDr)
    SELECT 
        'Sponsors',       
        TS.SponsorID,
        'Teams',        
        TS.TeamID
    FROM TeamsSponsors TS
    WHERE TS.TeamID <> (
        SELECT MAX(TS2.TeamID) 
        FROM TeamsSponsors TS2 
        WHERE TS2.SponsorID = TS.SponsorID
    );

    DROP TABLE TeamsSponsors;

    DECLARE @ConstraintSql NVARCHAR(MAX);
    SET @ConstraintSql = N'ALTER TABLE Sponsors ADD CONSTRAINT FK_Sponsors_Teams FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)';
    EXEC sp_executesql @ConstraintSql;

    PRINT 'Transformare M:N -> 1:N realizata cu succes!';
END

EXEC Transformare_MN_in_1N_SponsorsTeams


CREATE PROCEDURE Transformare_1N_in_11_CoachesTeams
-- Avem initial relatia 1:N (un antrenor poate avea mai multe echipe)
-- Transformarm in 1:1: un singur antrenor poate antrena o singura echipa
AS
BEGIN
    -- Daca mai multe echipe au acelasi antrenor, cea cu id-ul maxim pastreaza anternorul
    INSERT INTO Legaturi_Eliminate (NumeTabelaSt, IdSt, NumeTabelaDr, IdDr)
    SELECT 
        'Coaches',
        CoachID,
        'Teams',
        TeamID
    FROM Teams T
    WHERE TeamID <> (
        SELECT MAX(T2.TeamID) 
        FROM Teams T2 
        WHERE T2.CoachID = T.CoachID
    );

    UPDATE Teams
    SET CoachID = NULL
    WHERE TeamID <> (
        SELECT MAX(T2.TeamID) 
        FROM Teams T2 
        WHERE T2.CoachID = Teams.CoachID
    );

    CREATE UNIQUE NONCLUSTERED INDEX UQ_Teams_CoachID
    ON Teams(CoachID)
    WHERE CoachID IS NOT NULL;

    PRINT 'Transformare 1:N -> 1:1 realizata cu succes!';
END

EXEC Transformare_1N_in_11_CoachesTeams