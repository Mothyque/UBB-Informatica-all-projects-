CREATE TABLE profesori
(
id SERIAL PRIMARY KEY,
nume VARCHAR(50),
varsta INT
);
CREATE TABLE materii
(
id SERIAL PRIMARY KEY,
id_profesor INT,
nume VARCHAR(50),
credite INT,
FOREIGN KEY (id_profesor) REFERENCES profesori(id) ON DELETE CASCADE
);

CREATE TABLE studenti
(
id SERIAL PRIMARY KEY,
nume VARCHAR(50),
varsta INT
);

CREATE TABLE studenti_materii
(
id_s INT NOT NULL,
id_m INT NOT NULL,
PRIMARY KEY (id_s, id_m),
FOREIGN KEY (id_s) REFERENCES studenti(id) ON DELETE CASCADE,
FOREIGN KEY (id_m) REFERENCES materii(id) ON DELETE CASCADE
);

INSERT INTO profesori (nume, varsta) VALUES
('Mihai Popescu', 45),
('Elena Ionescu', 38),
('Radu Dumitrescu', 52);

INSERT INTO studenti (nume, varsta) VALUES
('Ana Stan', 20),
('Vlad Marin', 21),
('Cristina Voicu', 20),
('Andrei Radu', 22);

INSERT INTO materii (id_profesor, nume, credite) VALUES
(1, 'Sisteme de Gestiune a Bazelor de Date', 6),
(2, 'Programare Orientată pe Obiecte (Java)', 5),
(2, 'Structuri de Date', 5),
(3, 'Criptografie', 4);

INSERT INTO studenti_materii (id_s, id_m) VALUES
(1, 1), (1, 2),
(2, 1), (2, 4),
(3, 2), (3, 3),
(4, 1), (4, 2), (4, 3);

SELECT * FROM studenti