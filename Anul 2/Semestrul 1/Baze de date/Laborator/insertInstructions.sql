USE Euroleague
GO

INSERT INTO Arena VALUES
('Zalgirio Arena', 'Kaunas', 15415),
('Sinan Erdem Dome', 'Istanbul', 16000),
('Coca-Cola Arena','Dubai',15000),
('Roig Arena','Valencia',15600),
('Stark Arena', 'Belgrade', 18386),
('Peace and Friendship Stadium', 'Piraeus', 12300),
('OAKA Olympic Indoor Hall', 'Athens', 18300),
('SAP Garden', 'Munich', 12500),
('WiZink Center', 'Madrid', 15000),
('Astroballe', 'Lyon', 5500);

INSERT INTO Teams VALUES
('Zalgiris Kaunas', 'Kaunas', 1, 1944, 1, 1),
('Anadolu Efes', 'Istanbul', 2, 1976, 2, 2),
('Dubai Basketball', 'Dubai', 3, 2024, 3, 0),
('Valencia Basket', 'Valencia', 4, 1986, 4, 0),
('Partizan Belgrade', 'Belgrade', 5, 1945, 5, 1),
('Olympiacos Piraeus', 'Piraeus', 6, 1931, 6, 4),
('Panathinaikos Athens', 'Athens', 7, 1919, 7, 6),
('Bayern Munich', 'Munich', 8, 1946, 8, 0),
('Real Madrid', 'Madrid', 9, 1931, 9, 11),
('ASVEL Lyon-Villeurbanne', 'Lyon', 10, 1948, 10, 0);

INSERT INTO Coaches VALUES
('Sarunas', 'Jasikevicius', '1976-03-05', 'Lithuanian'),
('Ergin', 'Ataman', '1966-01-07', 'Turkish'),
('Nenad', 'Markovic', '1968-06-06', 'Bosnian'),
('Alex', 'Mumbru', '1979-06-12', 'Spanish'),
('Zeljko', 'Obradovic', '1960-03-09', 'Serbian'),
('Giorgos', 'Bartzokas', '1965-06-11', 'Greek'),
('Dimitris', 'Priftis', '1972-08-16', 'Greek'),
('Andrea', 'Trinchieri', '1968-08-06', 'Italian'),
('Chus', 'Mateo', '1969-01-23', 'Spanish'),
('T.J.', 'Parker', '1984-05-01', 'French');

INSERT INTO Teams VALUES
('Zalgiris Kaunas', 'Kaunas', 1, 1944, 1, 1),
('Anadolu Efes', 'Istanbul', 2, 1976, 2, 2),
('Dubai Basketball', 'Dubai', 3, 2024, 3, 0),
('Valencia Basket', 'Valencia', 4, 1986, 4, 0),
('Partizan Belgrade', 'Belgrade', 5, 1945, 5, 1),
('Olympiacos Piraeus', 'Piraeus', 6, 1931, 6, 4),
('Panathinaikos Athens', 'Athens', 7, 1919, 7, 6),
('Bayern Munich', 'Munich', 8, 1946, 8, 0),
('Real Madrid', 'Madrid', 9, 1931, 9, 11),
('ASVEL Lyon-Villeurbanne', 'Lyon', 10, 1948, 10, 0);

INSERT INTO Games VALUES 
(1, 2, 2, '2025-10-30', '80-75'),
(3, 4, 4, '2025-10-31', '85-78'),
(5, 6, 6, '2025-10-31', '92-88'),
(7, 8, 8, '2025-10-31', '77-74'),
(9, 10, 10, '2025-10-31', '81-79'),
(1, 3, 1, '2025-11-07', '90-85'),
(2, 4, 2, '2025-11-07', NULL),
(5, 7, 5, '2025-11-07', NULL),
(6, 8, 6, '2025-11-07', NULL),
(9, 1, 9, '2025-11-07', NULL);

INSERT INTO Players VALUES
(1, 'Marius', 'Kalnietis', 33, 192, 88, 'Lithuanian', 'PG'),
(2, 'Vasilije', 'Micic', 28, 196, 90, 'Serbian', 'SG'),
(3, 'Dusan', 'Ristic', 29, 210, 102, 'Serbian', 'C'),
(4, 'Pablo', 'Alvarez', 31, 200, 95, 'Spanish', 'SF'),
(5, 'Georgios', 'Printezis', 36, 203, 104, 'Greek', 'PF'),
(6, 'Shaquille', 'Harrison', 29, 196, 91, 'American', 'SG'),
(7, 'Ioannis', 'Papapetrou', 28, 203, 100, 'Greek', 'SF'),
(8, 'Nihad', 'Djedovic', 33, 198, 93, 'Bosnian', 'SG'),
(9, 'Walter', 'Tavares', 30, 220, 120, 'Cape Verdean', 'C'),
(10, 'David', 'Lighty', 34, 198, 95, 'American', 'SF');

INSERT INTO PlayerStats VALUES
(1, 1, 18, 4, 7, 0, 2),
(2, 1, 22, 5, 3, 1, 1),
(3, 2, 15, 9, 2, 2, 0),
(4, 2, 20, 4, 5, 0, 1),
(5, 3, 25, 10, 4, 1, 0),
(6, 3, 18, 3, 6, 0, 3),
(7, 4, 12, 7, 3, 0, 2),
(8, 4, 19, 5, 2, 1, 1),
(9, 5, 10, 11, 1, 2, 0),
(10, 5, 15, 6, 5, 0, 1),
(1, 6, 21, 3, 8, 0, 1),
(4, 6, 18, 6, 4, 1, 0);

INSERT INTO Referees VALUES
(7, 'John', 'Doe', 12, 150),
(7, 'Mark', 'Smith', 8, 95),
(7, 'Luis', 'Garcia', 15, 200),
(8, 'Anna', 'Ivanova', 10, 120),
(8, 'Peter', 'Brown', 7, 80),
(8, 'Carlos', 'Martinez', 13, 170),
(9, 'Michael', 'Johnson', 9, 110),
(9, 'Sofia', 'Petrova', 11, 140),
(10, 'David', 'Lee', 14, 180),
(10, 'Emma', 'Williams', 6, 70),
(10, 'Tom', 'Anderson', 12, 160);

INSERT INTO Seasons (Years, WinnerID, MVPID, DPOYID, COTYID)
VALUES

('2024-2025', (SELECT TeamID FROM Teams WHERE TeamName='Fenerbahce Istanbul'),
             (SELECT PlayerID FROM Players WHERE FirstName='Kendrick' AND LastName='Nunn'),
             (SELECT PlayerID FROM Players WHERE FirstName='Nick' AND LastName='Weiler-Babb'),
             (SELECT CoachID FROM Coaches WHERE LastName='Jasikevicius')),

('2023-2024', (SELECT TeamID FROM Teams WHERE TeamName='Panathinaikos Athens'),
             (SELECT PlayerID FROM Players WHERE FirstName='Mike' AND LastName='James'),
             (SELECT PlayerID FROM Players WHERE FirstName='Thomas' AND LastName='Walkup'),
             (SELECT CoachID FROM Coaches WHERE LastName='Mateo')),

('2022-2023', (SELECT TeamID FROM Teams WHERE TeamName='Real Madrid'),
             (SELECT PlayerID FROM Players WHERE FirstName='Sasha' AND LastName='Vezenkov'),
             (SELECT PlayerID FROM Players WHERE FirstName='Kyle' AND LastName='Hines'),
             (SELECT CoachID FROM Coaches WHERE LastName='Bartzokas')),

('2021-2022', (SELECT TeamID FROM Teams WHERE TeamName='Anadolu Efes'),
             (SELECT PlayerID FROM Players WHERE FirstName='Nikola' AND LastName='Mirotic'),
             (SELECT PlayerID FROM Players WHERE FirstName='Kyle' AND LastName='Hines'),
             (SELECT CoachID FROM Coaches WHERE LastName='Bartzokas')),

('2020-2021', (SELECT TeamID FROM Teams WHERE TeamName='Anadolu Efes'),
             (SELECT PlayerID FROM Players WHERE FirstName='Vasilije' AND LastName='Micic'),
             (SELECT PlayerID FROM Players WHERE FirstName='Walter' AND LastName='Tavares'),
             (SELECT CoachID FROM Coaches WHERE LastName='Ataman')),

('2018-2019', (SELECT TeamID FROM Teams WHERE TeamName='CSKA Moscow'),
             (SELECT PlayerID FROM Players WHERE FirstName='Jan' AND LastName='Vesely'),
             (SELECT PlayerID FROM Players WHERE FirstName='Walter' AND LastName='Tavares'),
             (SELECT CoachID FROM Coaches WHERE LastName='Itoudis')),

('2017-2018', (SELECT TeamID FROM Teams WHERE TeamName='Real Madrid'),
             (SELECT PlayerID FROM Players WHERE FirstName='Luka' AND LastName='Doncic'),
             (SELECT PlayerID FROM Players WHERE FirstName='Kyle' AND LastName='Hines'),
             (SELECT CoachID FROM Coaches WHERE LastName='Laso')),

('2016-2017', (SELECT TeamID FROM Teams WHERE TeamName='Fenerbahce Istanbul'),
             (SELECT PlayerID FROM Players WHERE FirstName='Sergio' AND LastName='Llull'),
             (SELECT PlayerID FROM Players WHERE FirstName='Adam' AND LastName='Hanga'),
             (SELECT CoachID FROM Coaches WHERE LastName='Itoudis')),

('2015-2016', (SELECT TeamID FROM Teams WHERE TeamName='Real Madrid'),
             (SELECT PlayerID FROM Players WHERE FirstName='Nando' AND LastName='De Colo'),
             (SELECT PlayerID FROM Players WHERE FirstName='Kyle' AND LastName='Hines'),
             (SELECT CoachID FROM Coaches WHERE LastName='Obradovic')),

('2014-2015', (SELECT TeamID FROM Teams WHERE TeamName='Maccabi Tel Aviv'),
             (SELECT PlayerID FROM Players WHERE FirstName='Nemanja' AND LastName='Bjelica'),
             (SELECT PlayerID FROM Players WHERE FirstName='Bryant' AND LastName='Dunston'),
             (SELECT CoachID FROM Coaches WHERE LastName='Laso'));

INSERT INTO Sponsors (SponsorName, Industry, Country) VALUES
('Nike', 'Sportswear', 'USA'),
('Adidas', 'Sportswear', 'Germany'),
('Turkish Airlines', 'Airline', 'Turkey'),
('Coca-Cola', 'Beverages', 'USA'),
('Red Bull', 'Beverages', 'Austria'),
('Pepsi', 'Beverages', 'USA'),
('Samsung', 'Electronics', 'South Korea'),
('Heineken', 'Beverages', 'Netherlands'),
('Puma', 'Sportswear', 'Germany'),
('Qatar Airways', 'Airline', 'Qatar');

INSERT INTO TeamsSponsors (TeamID, SponsorID, SponsorshipStart, SponsorshipEnd) VALUES
(1, 1, '2022-01-01', '2025-12-31'),
(9, 1, '2020-01-01', '2024-12-31'),
(6, 1, '2023-01-01', '2026-12-31'),
(7, 2, '2021-01-01', '2024-12-31'),
(8, 2, '2023-01-01', '2026-12-31'),
(1, 3, '2020-01-01', '2025-12-31'),
(2, 3, '2021-01-01', '2025-12-31'),
(3, 4, '2022-01-01', '2025-12-31'),
(4, 4, '2022-01-01', '2025-12-31'),
(4, 5, '2022-01-01', '2024-12-31');


SELECT * FROM Coaches