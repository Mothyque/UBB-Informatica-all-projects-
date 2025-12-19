CREATE DATABASE MersulTrenurilor

USE MersulTrenurilor
GO

CREATE TABLE Trenuri(
	id INT IDENTITY (1,1) PRIMARY KEY,
	nume NVARCHAR(50),
	id_tip INT NOT NULL,
	CONSTRAINT FK_Trenuri_id_tip FOREIGN KEY (id_tip) REFERENCES TipuriTren(id)
);

CREATE TABLE TipuriTren(
	id INT IDENTITY (1,1) PRIMARY KEY,
	descriere NVARCHAR(255)
);


CREATE TABLE Statii(
	id INT IDENTITY (1,1) PRIMARY KEY,
	nume NVARCHAR(50)
);

CREATE TABLE Rute(
	id INT IDENTITY (1,1) PRIMARY KEY,
	nume NVARCHAR(50),
	id_tren INT NOT NULL,
	ora_plecarii NVARCHAR(50),
	ora_sosirii NVARCHAR(50),
	CONSTRAINT FK_Rute_Trenuri FOREIGN KEY (id_tren) REFERENCES Trenuri(id)
);


CREATE TABLE StatiiRute(
	id INT IDENTITY (1,1) PRIMARY KEY,
	id_s INT NOT NULL,
	id_r INT NOT NULL
	CONSTRAINT FK_StatiiRute_Statii FOREIGN KEY (id_s) REFERENCES Statii(id),
	CONSTRAINT FK_StatiiRute_Rute FOREIGN KEY (id_r) REFERENCES Rute(id)
);



INSERT INTO TipuriTren (descriere) VALUES 
('Regio'),
('InterRegio'),
('InterCity'),
('EuroNight'),
('Marfar');

INSERT INTO Trenuri (nume, id_tip) VALUES 
('IR 1632', 2),      
('R 3001', 1),       
('IC 531', 3),       
('EN 346', 4),       
('Marfar 8005', 5),  
('IR 1582', 2),      
('R 5002', 1);

INSERT INTO Statii (nume) VALUES 
('Bucuresti Nord'),
('Cluj-Napoca'),
('Brasov'),
('Constanta'),
('Timisoara Nord'),
('Iasi'),
('Craiova');

INSERT INTO Rute (id_tren, nume, ora_sosirii, ora_plecarii) VALUES 
(1, 'Bucuresti - Brasov', '10:00', '10:15'),  
(2, 'Cluj - Timisoara',   '14:30', '14:45'),  
(3, 'Constanta - Buc',    '08:00', '08:10'),  
(1, 'Brasov - Bucuresti', '21:00', '21:30'),  
(5, 'Craiova - Pitesti',  '06:45', '07:00');


INSERT INTO StatiiRute (id_s, id_r) VALUES 
(1, 1), 
(3, 1),
(2, 2), 
(5, 2),
(4, 3), 
(1, 3),
(1, 2),
(3, 2),
(6, 2),
(7, 2);


CREATE PROCEDURE AdaugaStatie @id_statie INT, @id_ruta INT, @ora_plecarii_new NVARCHAR(50), @ora_sosirii_new NVARCHAR(50)
AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @id_ruta_verif INT;
	SELECT @id_ruta_verif = id FROM Rute WHERE id = @id_ruta
	IF @id_ruta_verif IS NULL
	BEGIN
		PRINT 'Eroare! Nu exista ruta';
		RETURN;
	END
	DECLARE @id_statie_verif INT;
	SELECT @id_statie_verif = id FROM Statii WHERE id = @id_statie
	IF @id_statie_verif IS NULL
	BEGIN
		PRINT 'Eroare! Nu exista statia';
		RETURN;
	END
	
	IF EXISTS (SELECT * FROM StatiiRute WHERE id_r = @id_ruta AND id_s = @id_statie)
	BEGIN
		UPDATE Rute SET ora_sosirii = @ora_sosirii_new, ora_plecarii = @ora_plecarii_new WHERE id = @id_ruta;
		PRINT 'Statia exista deja pe ruta. S-au actualizat orele rutei.';
	END
	ELSE
	BEGIN
		INSERT INTO StatiiRute VALUES(@id_statie, @id_ruta);
		PRINT 'Statia nu exista pe ruta, am adaugat-o acum';
	END
END

EXECUTE AdaugaStatie 1, 2, '12:00', '12:30';
EXECUTE AdaugaStatie 4, 2, '12:00', '14:00';


SELECT * FROM StatiiRute

CREATE VIEW vw_RuteComplete
AS 
SELECT 
R.nume AS NumeRuta
FROM Rute AS R
INNER JOIN StatiiRute SR ON R.id = SR.id_r
GROUP BY R.id, R.nume
HAVING COUNT(SR.id_s) = (SELECT COUNT(*) FROM Statii);

SELECT * FROM vw_RuteComplete
