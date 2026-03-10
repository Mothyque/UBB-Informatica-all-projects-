CREATE DATABASE ParcDistractii
GO

USE ParcDistractii
GO

CREATE TABLE Sectiuni(
	cod_s int identity(1,1) not null PRIMARY KEY,
	nume nvarchar(50),
	descriere nvarchar(50)
);

CREATE TABLE Atractii(
	cod_a int identity(1,1) not null PRIMARY KEY,
	nume nvarchar(50),
	descriere nvarchar(50),
	varsta_min int not null default 14,
	cod_s int not null,
	CONSTRAINT PK_Atractii_cod_s FOREIGN KEY (cod_s) REFERENCES Sectiuni(cod_s)
);

CREATE TABLE Categorii(
	cod_c int identity(1,1) not null PRIMARY KEY,
	nume nvarchar(50)
);

CREATE TABLE Vizitatori(
	cod_v int identity(1,1) not null PRIMARY KEY,
	nume nvarchar(50),
	email nvarchar(50),
	cod_c int not null,
	CONSTRAINT PK_Vizitatori_cod_c FOREIGN KEY (cod_c) REFERENCES Categorii(cod_c)
);

CREATE TABLE Note(
	cod_a int not null, 
	cod_v int not null,
	nota int not null default 1
	CONSTRAINT PK_Note_cod_a FOREIGN KEY (cod_a) REFERENCES Atractii(cod_a),
	CONSTRAINT PK_Note_cod_v FOREIGN KEY (cod_v) REFERENCES Vizitatori(cod_v)
);