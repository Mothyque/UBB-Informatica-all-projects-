USE ParcDistractii
GO

-- Functie scalara care primeste ca parametru numele unei categorii si returneaza codul acesteia

CREATE FUNCTION codAtractie (@numeAtractie NVARCHAR(50))
RETURNS INT 
AS
BEGIN
DECLARE @cod INT;
SELECT @cod = cod_a FROM Atractii WHERE nume = @numeAtractie;
RETURN @cod;
END;
GO

PRINT dbo.codAtractie ('haunted house');

-- Functie inline table valued care returneaza toate inregistrarile din tabelul sectiuni al caror nume se termina cu o litera data ca parametru de intrare si au cel putin 2 caractere.

CREATE FUNCTION sectiuniLitera (@ultimaLitera NVARCHAR(1))
RETURNS TABLE
AS
RETURN SELECT S.cod_s, S.nume, S.descriere FROM Sectiuni S
WHERE S.nume LIKE '%' + @ultimaLitera
AND LEN(S.nume) >= 2
GO

SELECT * FROM dbo.sectiuniLitera('a');


-- View care afiseaza toate inregistraril din tabelul Categorii al caror nume este egal cu 'pensionari' sau 'copii'


CREATE VIEW vw_Pensionari_Copii
AS
SELECT * FROM Categorii WHERE nume = 'pensionari' OR nume = 'copii'
GO

SELECT * FROM vw_Pensionari_Copii;


-- View care afiseaza toate inregistrarile din tabelul Sectiuni al caror nume incepe cu litera C

CREATE VIEW vw_Sectiuni_cu_litera_c
AS
SELECT * FROM Sectiuni WHERE nume like 'C%'
GO

SELECT * FROM vw_Sectiuni_cu_litera_c

-- View care afiseaza numele vizitatorilor, nota si numele atractiei

CREATE VIEW vw_AtractiiNote
AS 
SELECT 
V.nume AS NumeVizitator,
A.nume AS NumeAtractie,
N.nota AS Nota
FROM Vizitatori AS V
INNER JOIN Note AS N on V.cod_v = N.cod_v
INNER JOIN Atractii AS A on N.cod_a = A.cod_a;
GO

SELECT * FROM vw_AtractiiNote


-- Trigger care impiedica executia operatiilor de stergere din tabela Categorii si afiseaza un mesaj corespunzator

ALTER TRIGGER trg_forbidDeleteOnCategorii
ON Categorii
FOR DELETE 
AS 
BEGIN
RAISERROR ('Nu se permite stergerea inregistrarilor din tabela Categorii.', 16, 1);
ROLLBACK TRANSACTION;
END;
GO