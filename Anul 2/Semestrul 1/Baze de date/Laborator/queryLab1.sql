CREATE DATABASE Euroleague
GO

USE Euroleague
GO

CREATE TABLE Teams(
	TeamID int IDENTITY(1,1) PRIMARY KEY, 
	TeamName NVARCHAR(100) NOT NULL,
	City NVARCHAR(50) NOT NULL,
	ArenaID int NOT NULL,
	FoundYear int,
	CoachID int, 
	Titles int default 0,
	CONSTRAINT FK_Teams_Arena FOREIGN KEY (ArenaID) REFERENCES Arena(ArenaID),
    CONSTRAINT FK_Teams_Coach FOREIGN KEY (CoachID) REFERENCES Coaches(CoachID)
);

CREATE TABLE Arena(
	ArenaID int IDENTITY(1,1) PRIMARY KEY,
	ArenaName NVARCHAR(100) NOT NULL,
	City NVARCHAR(50) NOT NULL,
	Capacity int
);

CREATE TABLE Coaches(
	CoachID int IDENTITY(1,1) PRIMARY KEY,
	FirstName nvarchar(50) NOT NULL,
	LastName nvarchar(50) NOT NULL,
	BirthDate Date,
	Nationality nvarchar(50) NOT NULL
);

CREATE TABLE Players(
	PlayerID int identity(1,1) PRIMARY KEY,
	TeamID int not null,
	FirstName nvarchar(50),
	LastName nvarchar(50),
	Age int not null,
	HeightCm int not null,
	WeightKg int not null,
	Nationalty nvarchar(50),
	Position nvarchar(50),
	CONSTRAINT FK_Players_Teams FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);

CREATE TABLE Sponsors (
	SponsorID INT IDENTITY(1,1) PRIMARY KEY,
	SponsorName nvarchar(100) not null,
	Industry nvarchar(50),
	Country nvarchar(50)
);

CREATE TABLE TeamsSponsors(
	TeamID int not null,
	SponsorID int not null,
	SponsorshipStart date,
	SponsorshipEnd date,
	PRIMARY KEY (TeamID, SponsorID),
	CONSTRAINT FK_TeamsSponsors_Teams FOREIGN KEY (TeamID) REFERENCES Teams(TeamID),
	CONSTRAINT FK_TeamsSponsors_Sponsors FOREIGN KEY (SponsorID) REFERENCES Sponsors(SponsorID)
);

CREATE TABLE Games(
	GameID int identity(1,1) PRIMARY KEY,
	Team1ID int not null,
	Team2ID int not null,
	ArenaID int not null,
	PlayingDate date,
	Score nvarchar(50),
	CONSTRAINT FK_Games_Team1ID FOREIGN KEY (Team1ID) REFERENCES Teams(TeamID),
	CONSTRAINT FK_Games_Team2ID FOREIGN KEY (Team2ID) REFERENCES Teams(TeamID),
);

CREATE TABLE Seasons(
	Years nvarchar(50) PRIMARY KEY,
	WinnerID int not null,
	MVPID int not null,
	DPOYID int not null,
	COTYID int not null,
	CONSTRAINT FK_Seasons_WinnerID FOREIGN KEY (WinnerID) REFERENCES Teams(TeamID),
	CONSTRAINT FK_Seasons_MVPID FOREIGN KEY (MVPID) REFERENCES Players(PlayerID),
	CONSTRAINT FK_Seasons_DPOYID FOREIGN KEY (DPOYID) REFERENCES Players(PlayerID),
	CONSTRAINT FK_Seasons_COTYID FOREIGN KEY (COTYID) REFERENCES Coaches(CoachID),
);

CREATE TABLE Referees(
	RefereeID int identity(1,1) PRIMARY KEY,
	NextMatchID int not null, 
	FirstName nvarchar(50), 
	LastName nvarchar(50),
	Experience int not null default 0,
	MatchesRefereed int not null default 0,
	CONSTRAINT FK_Referees_NextMatchID FOREIGN KEY (NextMatchID) REFERENCES Games(GameID)
);

CREATE TABLE PlayerStats(
	StatID int identity(1,1) PRIMARY KEY,
	PlayerID int not null,
	GameID int not null,
	Points int not null default 0,
	Rebounds int not null default 0,
	Assists int not null default 0,
	Blocks int not null default 0,
	Steals int not null default 0,
	CONSTRAINT FK_PlayerStats_PlayerID FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID),
	CONSTRAINT FK_PlayerStats_GameID FOREIGN KEY (GameID) REFERENCES Games(GameID)
);