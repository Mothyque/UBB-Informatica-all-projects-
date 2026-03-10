
CREATE TABLE ducks (
id INT PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
type VARCHAR(50) NOT NULL,
speed DOUBLE PRECISION NOT NULL,
endurance DOUBLE PRECISION NOT NULL
);

INSERT INTO ducks (id, username, email, password, type, speed, endurance) VALUES
(16, 'Darkwing', 'darkwing@duckmail.com', 'c5240cd310c2976451f3b9635d92c7b2a0b5eea83fba252de2386f0e645f68cd', 'FLYING_AND_SWIMMING', 45, 20),
(1, 'donald', 'donald@duckmail.com', '82d928273d067d774889d5df4249aaf73c0b04c64f04d6ed001441ce87a0853c', 'FLYING', 35.5, 12),
(17, 'Magica', 'magy@duckmail.com', 'f1c19b9ba08d4b09e431c7a074b0c4ed84a2eb764a0e5cdedd14739298dc878e', 'FLYING', 25, 10),
(3, 'scrooge', 'scrooge@duckmail.com', '24d7f03d8dc3c3666969e6fa5bb1fac4736d3f1353c28307ed51b320f9dc42d3', 'FLYING_AND_SWIMMING', 50, 25),
(4, 'launchpad', 'launchpad@duckmail.com', 'fad900280ce54a04da3b698f1a7783717b466fedd3ded41fe5fc5a3686f1b180', 'FLYING', 60.5, 18),
(5, 'howard', 'howard@duckmail.com', '38868dc905f4d6d6de71dc05a83a876faeda1c0fc998ab582ac8bf62a47c4ae8', 'SWIMMING', 15, 8),
(6, 'RataSmechere', 'cool_duck@hotmail.com', 'cd42abc7b7aa4db4320fddd25a2516b29e046c4951cb78084b45199dc6f9bae1', 'SWIMMING', 20, 9),
(14, 'Quacky', 'quacky@duckmail.com', 'b12caa46cc35b829ffe0634ec30fea92bf982fb81cb90305d964db7082df3b56', 'FLYING', 30, 11),
(15, 'Webby', 'webby@duckmail.com', 'bcf6781564c16e0337b840d55c0edc96a0889d3434764afa51389447c3bf4926', 'SWIMMING', 18, 7),
(18, 'SpeedyDuck', 'speedy@duckmail.com', '8936c5df03220c62e05ce99aa3cf9870ebf7f12fff6999f7570ded7b221fd226', 'SWIMMING', 26, 30);

CREATE TABLE persons (
id INT PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
birth_date VARCHAR(255) NOT NULL,
occupation VARCHAR(255),
empathy_level INT NOT NULL
);

INSERT INTO persons (id, username, email, password, first_name, last_name, birth_date, occupation, empathy_level) VALUES
(9, 'robert_j', 'robert@example.com', '88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589', 'Robert', 'Johnson', '01.12.1990', 'Teacher', 7),
(8, 'anna_smith', 'anna@example.com', 'd74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1', 'Anna', 'Smith', '23.09.1995', 'Doctor', 9),
(7, 'john_doe', 'john@example.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'John', 'Doe', '14.05.1998', 'Engineer', 8),
(12, 'Mothyque', 'mathyasacul@yahoo.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'Mathyas', 'Acul', '21.09.2025', 'Unemployed', 10),
(10, 'emily_b', 'emily@example.com', '21ec1efc057401ea6152ee36e4949a985c6e7579e37c0333d369cefe2211131e', 'Emily', 'Brown', '18.02.2001', 'Designer', 6),
(13, 'Mathyas', 'mathyas@gmail.com', '08d0a932aa31594e5542bc4e0e755e9960e32fc28b0a7b7a0d2e881f5fd81a89', 'Mathyas', 'Acul', '21.09.2025', 'Somer', 9);
(0, 'admin', 'admin', 'admin', 'admin', 'admin', '01.01.1970', 'Administrator', 10);

CREATE TABLE friendships (
user_id_1 INT NOT NULL,
user_id_2 INT NOT NULL,
date TIMESTAMP NOT NULL,
status VARCHAR(50) NOT NULL,
PRIMARY KEY (user_id_1, user_id_2)
);

INSERT INTO friendships (user_id_1, user_id_2, date, status) VALUES
(7, 9, '2025-12-15 12:02:35.543817', 'APPROVED'),
(3, 6, '2025-12-15 12:02:35.543817', 'APPROVED'),
(5, 8, '2025-12-15 12:02:35.543817', 'APPROVED'),
(10, 12, '2025-12-15 12:02:35.543817', 'APPROVED'),
(8, 13, '2025-12-15 12:02:35.543817', 'APPROVED'),
(8, 12, '2025-12-15 12:02:35.543817', 'APPROVED'),
(8, 18, '2025-12-18 12:17:00.439595', 'APPROVED');

CREATE TABLE messages (
id SERIAL PRIMARY KEY,
from_user_id INT NOT NULL,
from_user_type VARCHAR(50),
message_text TEXT NOT NULL,
date TIMESTAMP NOT NULL,
reply_to_message_id INT,
FOREIGN KEY (reply_to_message_id) REFERENCES messages(id) ON DELETE SET NULL
);

INSERT INTO messages (id, from_user_id, from_user_type, message_text, date, reply_to_message_id) VALUES
(1, 12, 'PERSON', 'Salut emily', '2025-12-10 20:13:13.388275', NULL),
(2, 10, 'PERSON', 'Salut frate', '2025-12-10 20:18:01.454357', NULL),
(3, 10, 'PERSON', 'Ce faci?', '2025-12-10 20:18:10.693092', NULL),
(4, 12, 'PERSON', 'Bine, tu?', '2025-12-10 20:18:55.703613', NULL),
(5, 12, 'PERSON', 'Hello', '2025-12-10 20:21:18.089521', NULL),
(6, 12, 'PERSON', 'Salutare fratilor', '2025-12-10 21:01:24.805447', NULL),
(7, 8, 'PERSON', 'Salut frate', '2025-12-10 21:02:05.777138', NULL),
(8, 8, 'PERSON', 'Hello', '2025-12-11 13:08:34.710826', NULL),
(9, 8, 'PERSON', 'Hello', '2025-12-11 13:09:24.927017', NULL),
(10, 8, 'PERSON', 'Hello', '2025-12-11 13:13:28.407905', NULL),
(11, 12, 'PERSON', '.', '2025-12-11 13:14:13.550158', NULL),
(12, 8, 'PERSON', '?', '2025-12-15 13:04:23.515683', NULL),
(13, 12, 'PERSON', 'what?', '2025-12-15 13:05:24.95963', NULL),
(14, 18, 'DUCK', 'salut anna', '2025-12-15 15:04:31.387186', NULL),
(15, 18, 'DUCK', 'salut', '2025-12-18 12:17:20.891247', NULL),
(16, 8, 'PERSON', 'salut', '2025-12-18 12:17:49.103183', NULL);

CREATE TABLE flocks (
id INT PRIMARY KEY,
name VARCHAR(255),
type VARCHAR(50) NOT NULL
);

INSERT INTO flocks (id, name, type) VALUES
(1, 'SwimMasters', 'SWIMMING'),
(2, 'FlyHighers', 'FLYING'),
(3, 'DuckAdventurers', 'FLYING_AND_SWIMMING'),
(4, 'QuackSquad', 'FLYING'),
(5, 'WaterWaddlers', 'SWIMMING'),
(6, 'TheFeatheredFriends', 'FLYING_AND_SWIMMING'),
(7, 'Car', 'SWIMMING');

CREATE TABLE flock_memberships(
flock_id INT REFERENCES flocks(id) ON DELETE CASCADE,
duck_id INT REFERENCES ducks(id) ON DELETE CASCADE,
PRIMARY KEY (flock_id, duck_id)
);

INSERT INTO flock_memberships (flock_id, duck_id) VALUES
(2, 1),
(1, 5),
(2, 4),
(3, 3),
(6, 3),
(5, 6),
(1, 15),
(2, 14),
(3, 16),
(4, 17),
(6, 16);

CREATE TABLE events (
id INT PRIMARY KEY,
type VARCHAR(50) NOT NULL,
date TIMESTAMP NOT NULL,
winner_id INT,
FOREIGN KEY (winner_id) REFERENCES ducks(id) ON DELETE SET NULL
);

INSERT INTO events (id, type, date, winner_id) VALUES
(1, 'RACE', '2025-11-20 21:25:29.682726', NULL),
(2, 'RACE', '2025-12-01 22:31:27.887993', NULL);

CREATE TABLE event_participants (
event_id INT REFERENCES events(id) ON DELETE CASCADE,
duck_id INT REFERENCES ducks(id) ON DELETE CASCADE,
PRIMARY KEY (event_id, duck_id)
);

INSERT INTO event_participants (event_id, duck_id) VALUES
(1, 18),
(2, 15),
(2, 18);

CREATE TABLE message_recipients (
message_id INT NOT NULL,
recipient_user_id INT NOT NULL,
recipient_user_type VARCHAR(50) NOT NULL,
PRIMARY KEY (message_id, recipient_user_id),
FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
);

INSERT INTO message_recipients (message_id, recipient_user_id, recipient_user_type) VALUES
(1, 10, 'PERSON'),
(2, 12, 'PERSON'),
(3, 12, 'PERSON'),
(4, 10, 'PERSON'),
(5, 8, 'PERSON'),
(6, 10, 'PERSON'),
(6, 8, 'PERSON'),
(6, 5, 'DUCK'),
(6, 13, 'PERSON'),
(7, 5, 'DUCK'),
(7, 13, 'PERSON'),
(7, 12, 'PERSON'),
(7, 10, 'PERSON'),
(8, 5, 'DUCK'),
(9, 13, 'PERSON'),
(10, 12, 'PERSON'),
(11, 8, 'PERSON'),
(12, 12, 'PERSON'),
(13, 8, 'PERSON'),
(14, 8, 'PERSON'),
(15, 8, 'PERSON'),
(16, 18, 'DUCK');

SELECT setval('messages_id_seq', (SELECT MAX(id) FROM messages));