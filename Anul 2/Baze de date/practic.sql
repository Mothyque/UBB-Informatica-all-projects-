CREATE DATABASE SKI

USE SKI
GO

CREATE TABLE Instructori(
	id INT IDENTITY (1,1) PRIMARY KEY,
	nume NVARCHAR(50),
	prenume NVARCHAR(50),
	varsta INT,
	experienta INT
);	

CREATE TABLE Echipamente(
	id INT IDENTITY (1,1) PRIMARY KEY, 
	categorie NVARCHAR(50),
	brand NVARCHAR(50),
	model_echipament NVARCHAR(50),
	an_fabricare INT
);

CREATE TABLE Partii(
	id INT IDENTITY (1,1) PRIMARY KEY, 
	denumire NVARCHAR(50),
	resort NVARCHAR(50),
	zona NVARCHAR(50),
	dificultate INT,
	siguranta NVARCHAR(50),
	facilitati NVARCHAR(50)
);

CREATE TABLE Cursanti(
	id INT IDENTITY(1,1) PRIMARY KEY,	
	nume NVARCHAR(50),
	prenume NVARCHAR(50), 
	data_nasterii DATE,
	gen NVARCHAR(50),
	restrictii NVARCHAR(50),
	id_instructor INT,
	id_echipament INT, 
	
	CONSTRAINT FK_Cursanti_id_instructor FOREIGN KEY (id_instructor) REFERENCES Instructori(id),
	CONSTRAINT FK_Cursanti_id_echipament FOREIGN KEY (id_echipament) REFERENCES Echipamente(id),
);

CREATE TABLE PartiiInstructori(
	id_instructor INT NOT NULL,
	id_partie INT NOT NULL,
	inceput_rezervare DATETIME,
	final_rezervare DATETIME,
	cost int,

	PRIMARY KEY(id_instructor, id_partie),
	CONSTRAINT FK_PartiiInstructori_id_instructor FOREIGN KEY (id_instructor) REFERENCES Instructori(id),
	CONSTRAINT FK_PartiiInstructori_id_partie FOREIGN KEY (id_partie) REFERENCES Partii(id)
);

INSERT INTO Instructori VALUES
('Pop', 'Andrei', 36, 10),
('Pascu', 'Marian', 42, 19),
('Moldovan', 'Camelia', 27, 6);

INSERT INTO Echipamente VALUES
('Snowboard', 'Head', 'SP823', 2017),
('Manusi', 'Nike', 'AL2', 2022),
('Clapari', 'Nordica', 'L123', 2025);

INSERT INTO Partii VALUES
('Cocos', 'Bistrita', 'Parte nordica', 7, 9, 'Bar, Mancare, Telescaun'),
('Partia Olimpica Borsa', 'Borsa', 'Parte nordica', 9, 10, 'Telecabina, Parcare supraterana, Restaurant'),
('Toplita', 'Toplita', 'Parte sudica', 4, 10, 'Instructori,Teleski, Traseu alternativ prin padure');

INSERT INTO Cursanti VALUES
('Popa', 'Cosmin', '2004-10-22', 'M', 'Nu are restrictii', 1, 3),
('Szabo', 'Alexia', '2001-10-09', 'F', 'Rau de inaltime', 2, 3),
('Cosma', 'Laurentiu', '1998-01-18', 'M', 'Intoleranta la lactoza', 2, 1);

INSERT INTO PartiiInstructori VALUES
(1,1, '2026-01-11 12:00', '2026-01-11 14:00', 160),
(1,3, '2026-01-11 16:00', '2026-01-11 20:00', 320),
(2,1, '2026-01-11 20:00', '2026-01-11 22:00', 160);

CREATE PROCEDURE AdaugaPartieLaInstructor @id_instructor INT, @id_partie INT, @data_inceput DATETIME, @data_sfarsit DATETIME, @cost INT
AS 
BEGIN
	SET NOCOUNT ON;
	DECLARE @id_partie_verif INT;
	SELECT @id_partie_verif = id FROM Partii WHERE id = @id_partie
	IF @id_partie_verif IS NULL
	BEGIN
		PRINT 'Eroare! Nu exista partia'
		RETURN;
	END

	DECLARE @id_instructor_verif INT;
	SELECT @id_instructor_verif = id FROM Partii WHERE id = @id_instructor
	IF @id_instructor_verif IS NULL
	BEGIN
		PRINT 'Eroare! Nu exista instructorul'
		RETURN;
	END


	IF EXISTS (SELECT * FROM PartiiInstructori WHERE id_partie = @id_partie AND id_instructor = @id_instructor)
	BEGIN
		UPDATE PartiiInstructori SET inceput_rezervare = @data_inceput, final_rezervare = @data_sfarsit, cost = @cost WHERE id_partie = @id_partie AND id_instructor = @id_instructor;
		PRINT 'Rezervare actualizata cu succes';
	END
	ELSE
	BEGIN
		INSERT INTO PartiiInstructori VALUES (@id_instructor, @id_partie, @data_inceput, @data_sfarsit, @cost);
		PRINT 'Rezervare adaugata cu succes';
	END
END

SELECT * FROM PartiiInstructori

EXECUTE AdaugaPartieLaInstructor 1, 1, '2026-01-11 12:00', '2026-01-11 14:00', 190; 
EXECUTE AdaugaPartieLaInstructor 3, 1, '2026-01-14 16:00', '2026-01-14 18:00', 400;
EXECUTE AdaugaPartieLaInstructor 6, 7, '2026-01-12 12:00', '2026-01-12 14:00', 190; 
SELECT * FROM PartiiInstructori

CREATE VIEW vw_Cei_Mai_Multi_Instructori
AS 
SELECT 
P.denumire AS NumePartie
FROM Partii AS P
INNER JOIN PartiiInstructori PaIn on P.id = PaIn.id_partie
GROUP BY P.denumire
HAVING COUNT(PaIn.id_instructor) = 
(
	SELECT MAX(InstructorCount)
	FROM
	(
		SELECT COUNT(id_instructor) AS InstructorCount FROM PartiiInstructori
		GROUP BY id_partie
	)	AS MaxCount
);

SELECT * FROM vw_Cei_Mai_Multi_Instructori