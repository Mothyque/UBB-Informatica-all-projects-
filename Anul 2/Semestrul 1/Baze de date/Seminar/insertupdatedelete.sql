INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 1', 'cea mai mare sectiune'),
('sectiunea 2', 'cea mai tare sectiune'),
('sectiunea 3', 'sectiunea cu monstri'),
('sectiunea 4', 'sectiunea de copii'),
('sectiunea 5', 'sectiunea cu dulciuri'),
('sectiunea 6', 'sectiunea horror'),
('sectiunea 7', 'sectiunea disney');


INSERT INTO Atractii(nume, descriere, varsta_min, cod_s) VALUES
('rollercoaster', 'cel mai rapid din parc', 12, 1),
('mountain rousse', 'cel mai periculos din parc', 18, 2),
('chiosc inghetata', 'cea mai buna inghetata', 4, 3),
('rollercoaster extreme', 'viteza maxima si adrenalina', 16, 4),
('haunted house', 'casa bantuita cu efecte speciale', 14, 5),
('simulator zbor', 'experienta realista de pilotaj', 12, 6),
('rafting rapid', 'coborare pe apa cu curenti puternici', 15, 7);


INSERT INTO Categorii(nume) VALUES
('copii'),
('prescolari'),
('studenti'),
('tineri'),
('adulti'),
('seniori'),
('familii');

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Andrei Popescu', 'andrei.popescu@email.com', 1),
('Maria Ionescu', 'maria.ionescu@email.com', 2),
('Ioan Georgescu', 'ioan.georgescu@email.com', 3),
('Elena Dumitrescu', 'elena.dumitrescu@email.com', 4),
('Alexandru Stan', 'alexandru.stan@email.com', 5),
('Cristina Marin', 'cristina.marin@email.com', 6),
('Radu Petrescu', 'radu.petrescu@email.com', 7);

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(1,1,10),
(2,2,7),
(3,3,5),
(4,4,7),
(5,5,8),
(6,6,10),
(7,7,4);

UPDATE Sectiuni SET descriere = 'cea mai noua sectiune'
WHERE nume = 'sectiunea 1';

UPDATE Atractii SET descriere = 'cel mai sigur din parc'
WHERE nume = 'mountain rousse';

UPDATE Categorii SET nume = 'tarabe' 
WHERE cod_c = 7

UPDATE Note SET nota = 7
WHERE cod_a = 1;

UPDATE Vizitatori SET cod_c = 1
WHERE nume = 'Andrei Popescu';

INSERT INTO Vizitatori VALUES
('Cosmin Pop', 'cosminpop@gmail.com',6),
('Ana Rotariu', 'anarotariu@gmail.com',7);

DELETE FROM Vizitatori WHERE cod_v = 8 or cod_v = 9;

DBCC CHECKIDENT ('Vizitatori', RESEED, 7);

INSERT INTO Atractii VALUES 
('Atractie de sters', 'Descriere de sters', 20, 6)

DELETE FROM Atractii WHERE nume = 'Atractie de sters';

DBCC CHECKIDENT ('Atractii', RESEED, 7);


INSERT INTO Note VALUES
(1,5,10);

DELETE FROM Note WHERE cod_a = 1 and cod_v = 5

SELECT * FROM Vizitatori WHERE nume LIKE '[ABC]%'