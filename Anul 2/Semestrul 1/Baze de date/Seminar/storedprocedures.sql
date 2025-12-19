USE ParcDistractii
GO	


-- Adauga o constrangere de valoare implicita pentru coloana varsta_min
CREATE PROCEDURE AdaugaConstrangereDefault
AS
BEGIN 
ALTER TABLE Atractii
ADD CONSTRAINT df_varsta_min DEFAULT 12 FOR varsta_min
END;

EXEC AdaugaConstrangereDefault;

-- Returneaza numele, descrierea, varsta minima a atractiilor care au varsta_min egala cu parametrul dat apelulului exect
CREATE PROCEDURE ReturneazaActivitatiCuVarstaMin @varsta_min INT
AS 
BEGIN 
SELECT nume, descriere, varsta_min FROM Atractii
WHERE varsta_min = @varsta_min;
END;

EXEC ReturneazaActivitatiCuVarstaMin 12;

-- Returneaza numarul de atractii care au varsta_min egala cu parametrul dat apelulului exect
CREATE PROCEDURE ReturneazaNrActivitatiCuVarstaMin @varsta_min INT, @nr_atractii INT OUTPUT
AS
BEGIN
SELECT @nr_atractii = COUNT(*) FROM Atractii WHERE @varsta_min = varsta_min;
END;

DECLARE @cnt INT
EXEC ReturneazaNrActivitatiCuVarstaMin 12, @cnt OUTPUT;
SELECT @cnt AS 'Numarul de atractii gasite';

-- EXERCITII 

-- 1) Sa se creeze o care insereaza o sectiune noua in tabelul Sectiuni. Procedura va avea doi parametri de intrare : numele si descrierea sectiunii

CREATE PROCEDURE AdaugaSectiune @numeSect nvarchar(50), @descriereSect nvarchar(50)
AS 
BEGIN
INSERT INTO Sectiuni VALUES ( @numeSect, @descriereSect);
END;

EXEC AdaugaSectiune 'Sectiune smechera', 'e cea mai smechera';


-- 2) Sa se creez o procedura stocata care actualizeaza adresa de email a unui vizitator din tabelul Vizitatori. Procedura stocata primeste 2 parametri de intrare: codul vizitatorului si noua adresa de email.

CREATE PROCEDURE UpdateVizitator @codVizitator INT, @emailNou nvarchar(50)
AS 
BEGIN
UPDATE Vizitatori SET email = @emailNou WHERE cod_v = @codVizitator;
END;

EXEC UpdateVizitator 1, 'andrei.popescu@gmail.com';


-- 3) Sa se creeze o procedura stocata care returneaza numele vizitatorului, adresa de email si numarul total de note pentru toti vizitatorii care au dat cel putin o nota unei atractii.

CREATE PROCEDURE NoteVizitatori
AS 
BEGIN
SELECT
v.nume, 
v.email,
COUNT(n.cod_v) as nr_note
FROM Vizitatori as V
JOIN Note as n on v.cod_v = n.cod_v
GROUP BY v.cod_v, v.nume, v.email;
END;

EXEC NoteVizitatori


-- 4) Sa se creeze o procedura stocata care insereaza o categorie noua in tabelul Categorii. Procedura va avea un parametru de intratre: Numele categoriei. Daca exista deja categoria data ca parametru, se va afisa un mesaj pe ecran si categoria nu va fi adaugata inca o data.

CREATE PROCEDURE AdaugaCategorieUnica @numeCategorie nvarchar(50)
AS 
BEGIN 
IF((SELECT COUNT(*) FROM Categorii WHERE nume = @numeCategorie) = 0)
	INSERT INTO Categorii VALUES (@numeCategorie);
ELSE
	PRINT 'Aceasta categorie exista deja';
END;

EXEC AdaugaCategorieUnica 'adolescenti';


-- 5) Sa se creeze o procedura stocata care inseereaza o atractie noua in tabelul Atractii. Procedura va avea 4 parametrii de intrare: numele, descrierea, varsta minima recomandata, numele sectiunii, in care se gaseste atractia. Daca nu exista sectiunea daca ca parametru aceasta va fi adaugata in tabelul Sectiuni.

CREATE PROCEDURE AdaugaAtractie @numeAtractie nvarchar(50), @descriereAtractie nvarchar(50), @varstaMinAtractie INT, @numeSectiune nvarchar(50)
AS 
BEGIN
DECLARE @id_Sectiune INT;
SELECT @id_Sectiune = cod_s FROM Sectiuni WHERE nume = @numeSectiune
IF @id_Sectiune IS NULL
BEGIN 
	INSERT INTO Sectiuni(nume) VALUES (@numeSectiune);
	SELECT @id_Sectiune = cod_S FROM Sectiuni WHERE nume = @numeSectiune;
END
INSERT INTO Atractii VALUES(@numeAtractie, @descriereAtractie, @varstaMinAtractie, @id_Sectiune);
END


EXECUTE AdaugaAtractie 'Atractie ascunsa', 'Este ascunsa de vedere', 18, 'Sectiunea ascunsa';

-- 6) Sa se creeze o procedura stocata care verifica daca exista un vizitator caruia ii corespunde o adresa de email data ca parametur de intrare. Daca vizitatorul exista, se va returna codul acestui, daca nu exista se va genera un measj de eroare.

CREATE PROCEDURE VerificaMail @emailVizitator nvarchar(50)
AS 
BEGIN
DECLARE @codVizitator INT;
SELECT @codVizitator = cod_v FROM Vizitatori WHERE email = @emailVizitator
IF @codVizitator IS NULL
	PRINT 'Eroare! Nu exista vizitator cu acest email'
ELSE
	SELECT @codVizitator AS 'CodVizitator';
END

EXECUTE VerificaMail 'radu.petrescu@email.com';


-- 7) Sa se creeze o procedura stocata xare sterge o atractie din tabelul Atractii. Procedura stocata va avea un singur parametru de intrare: numele atractiei. Inainte de stergerea atractiei, se va verifica daca exista note pentru acea atractie. In cazul in care exista note date aceeli atractii, se va afisa pe ecran un mesaj corespunzator si nu se va sterge atractia respectiva

CREATE PROCEDURE StergeAtractie @numeAtractie nvarchar(50)
AS
BEGIN
DECLARE @codAtractie INT;
SELECT @codAtractie = cod_a FROM Atractii WHERE nume = @numeAtractie
IF @codAtractie is NULL
	PRINT 'Eroare! Nu exista o atractie cu acest nume'
ELSE
	DELETE FROM Atractii WHERE @codAtractie = cod_a
END

EXECUTE StergeAtractie 'Atractie tare';
