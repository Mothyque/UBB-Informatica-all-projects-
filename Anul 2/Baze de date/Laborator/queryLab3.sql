USE Euroleague
GO


 -- modifica tipul unei coloane
CREATE PROCEDURE modificaArenaCapacityType
AS 
BEGIN
ALTER TABLE Arena
ALTER COLUMN Capacity BIGINT;
PRINT 'Modificare realizata cu succes!';
END

EXEC modificaArenaCapacityType

-- modifica tipul unei coloane - reversed
CREATE PROCEDURE modificaArenaCapacityTypeReversed
AS 
BEGIN 
ALTER TABLE Arena
ALTER COLUMN Capacity INT;
PRINT 'Modificare inversata cu succes!';
END

EXEC modificaArenaCapacityTypeReversed

-- adauga o constrangere de valoare implicita pentru un camp
CREATE PROCEDURE addDefaultConstraint
AS
BEGIN
ALTER TABLE Players 
ADD CONSTRAINT df_16 DEFAULT 16 for Age
PRINT 'Modificare realizata cu succes!'
END

EXEC addDefaultConstraint

-- sterge o constrangere de valoare implicita pentru un camp
CREATE PROCEDURE removeDefaultConstraint
AS 
BEGIN
ALTER TABLE Players
DROP CONSTRAINT df_16
PRINT 'Modificare inversata cu succes!'
END

EXEC removeDefaultConstraint

-- creeaza o noua tabela
CREATE PROCEDURE createRatingTable
AS 
BEGIN
CREATE TABLE Rating(
RatingID int not null PRiMARY KEY IDENTITY(1,1));
PRINT 'Am creat tabela Rating!'
END


EXEC createRatingTable


--sterge noua tabela
CREATE PROCEDURE deleteRatingTable
AS 
BEGIN
DROP TABLE Rating
PRINT 'Am sters tabela Rating!'
END

EXEC deleteRatingTable


-- adauga un camp nou
CREATE PROCEDURE addNewColumn
AS
BEGIN
ALTER TABLE Rating
ADD Nota int
PRINT 'Modificare realizata cu succes in tabela Rating!'
END

EXEC addNewColumn


-- sterge noul camp
CREATE PROCEDURE removeNewColumn
AS
BEGIN
ALTER TABLE Rating
DROP COLUMN Nota;
PRINT 'Modificare inversata cu succes in tabela Rating!'
END


EXEC removeNewColumn


-- creeaza o constrangere de cheie straina
CREATE PROCEDURE addFKConstraint
AS
BEGIN
ALTER TABLE Rating
ADD RefereeID int CONSTRAINT fk_Referee_Rating FOREIGN KEY(RefereeID) REFERENCES Referees(RefereeID);
PRINT 'Am adaugat cu succes constrangerea'
END

EXEC addFKConstraint


-- sterge o constrangere de cheie straina
CREATE PROCEDURE dropFKConstraint
AS 
BEGIN
ALTER TABLE Rating
DROP CONSTRAINT fk_Referee_Rating;
ALTER TABLE Rating
DROP COLUMN RefereeID;
PRINT 'Am sters cu succes constrangerea'
END

EXEC dropFKConstraint


-- Functie care ne ajuta sa navigam printre versiunile disponibile

CREATE PROCEDURE updateVersion @WantedVersion int

AS 
BEGIN
	SET NOCOUNT ON;

	IF @WantedVersion < 0 OR @WantedVersion > 5
	BEGIN
		PRINT 'Versiunea dorita trebuie sa fie intre 0 si 5.';
		RETURN -1;
	END

	DECLARE @CurrentVersion INT
	SELECT @CurrentVersion = VersiuneCurenta FROM Versiune WHERE ID = 1;

	IF @WantedVersion = @CurrentVersion
	BEGIN
		PRINT 'Baza de date este deja la vesrsiunea dorita.'
		RETURN 0;
	END

	WHILE @CurrentVersion < @WantedVersion
	BEGIN
		SET @CurrentVersion = @CurrentVersion + 1;

		IF @CurrentVersion = 1
			EXEC modificaArenaCapacityType;
		ELSE IF @CurrentVersion = 2
			EXEC addDefaultConstraint
		ELSE IF @CurrentVersion = 3
			EXEC createRatingTable
		ELSE IF @CurrentVersion = 4
			EXEC addNewColumn
		ELSE IF @CurrentVersion = 5
			EXEC addFKConstraint

		UPDATE Versiune SET VersiuneCurenta = @CurrentVersion WHERE ID = 1;
	END

	WHILE @CurrentVersion > @WantedVersion
	BEGIN
		SET @CurrentVersion = @CurrentVersion - 1;

		IF @CurrentVersion = 5
			EXEC dropFKConstraint
		ELSE IF @CurrentVersion = 4
			EXEC removeNewColumn
		ELSE IF @CurrentVersion = 3
			EXEC deleteRatingTable
		ELSE IF @CurrentVersion = 2
			EXEC removeDefaultConstraint
		ELSE IF @CurrentVersion = 1
			EXEC modificaArenaCapacityType

		UPDATE Versiune SET VersiuneCurenta = @CurrentVersion WHERE ID = 1;
	END
	PRINT 'Am facut update la versiunea ' + CAST(@CurrentVersion AS NVARCHAR(10));
	RETURN 0;
END