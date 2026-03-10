USE Euroleague
GO


-- Numarul de jucatori inalti (peste 2 metri) pentru fiecare echipa  
SELECT
t.TeamName,
c.FirstName AS	CoachFirstName,
c.LastName as CoachLastName,
COUNT(p.PlayerID) AS TallPlayersCount
FROM Players p
JOIN Teams t on p.TeamID = t.TeamID
JOIN Coaches c on t.CoachID = c.CoachID
WHERE p.HeightCm > 200
GROUP BY t.TeamName, c.FirstName, c.LastName;


-- Jucatorii care au marcat cel putin 20 de puncte intr-un meci
SELECT 
p.FirstName,
p.LastName,
MAX(g.PlayingDate) AS Last20PointGame
FROM Players p
JOIN PlayerStats ps on p.PlayerID = ps.PlayerID
JOIN Games g on g.GameID = ps.GameID
WHERE ps.Points >= 20
GROUP BY p.FirstName, p.LastName;

-- Echipele care au castigat campionatul si au avut si MVP-ul sezonului
SELECT 
t.TeamName,
s.Years
FROM Teams t
JOIN Players p on p.TeamID = t.TeamID
JOIN Seasons s on s.WinnerID = t.TeamID
WHERE s.MVPID = p.PlayerID
GROUP BY t.TeamName, s.Years;

-- Echipele care vor fi arbitrate de arbitrii cu experienta (minim 100 de meciuri)
SELECT
t.TeamName,
COUNT(r.RefereeID) AS ExperiencedReferees
FROM Teams t
JOIN Games g on g.Team1ID = t.TeamID or g.Team2ID = t.TeamID
JOIN Referees r on r.NextMatchID = g.GameID
WHERE r.MatchesRefereed >= 100
GROUP BY t.TeamName
HAVING COUNT(r.RefereeID) >= 1;

-- Jucatorii ce au medii de peste 15 puncte in meciurile din 2025
SELECT 
p.FirstName,
p.LastName
FROM Players p
JOIN PlayerStats ps on ps.PlayerID = p.PlayerID
JOIN Games g on g.GameID = ps.GameID
WHERE YEAR(g.PlayingDate) = 2025
GROUP BY p.FirstName, p.LastName
HAVING AVG(ps.Points) > 15;

-- Toate nationalitatile diferite ale jucatorilor 
SELECT DISTINCT Nationality FROM Players


-- Toate Echipele care au un jucator care a inregistrat cel putin un block in decursul unui meci
SELECT DISTINCT
t.TeamName
FROM Teams t
JOIN Games g on t.TeamID = g.Team1ID or t.TeamID = g.Team2ID
JOIN PlayerStats ps on ps.GameID = g.GameID
WHERE ps.Blocks > 0;


-- Toti arbitrii care vor avea de arbitrat un meci in care o echipa are cel putin un jucator american
SELECT DISTINCT
r.FirstName,
r.LastName,
g.PlayingDate
FROM Referees r
JOIN Games g on r.NextMatchID = g.GameID
JOIN Teams t on g.Team1ID = t.TeamID or g.Team2ID = t.TeamID
JOIN Players p on p.TeamID = t.TeamID
WHERE p.Nationality = 'American'

-- Toate echipele care au sponsori care contin litera "o" in denumire
SELECT 
t.TeamName,
s.SponsorName
FROM Teams t
JOIN TeamsSponsors ts on t.TeamID = ts.TeamID
JOIN Sponsors s on s.SponsorID = ts.SponsorID
WHERE s.SponsorName LIKE '%o%'


-- Toti jucatorii a caror echipa este sponsorizata de un brand de haine sportive
SELECT 
p.FirstName,
p.LastName
FROM Players p
JOIN Teams t on t.TeamID = p.TeamID
JOIN TeamsSponsors ts on t.TeamID = ts.TeamID
JOIN Sponsors s on s.SponsorID = ts.SponsorID
WHERE s.Industry = 'Sportswear';
