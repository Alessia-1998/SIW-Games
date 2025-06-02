-- Inserimento degli autori nella tabella autore
INSERT INTO autore (id, nome, cognome, data_di_nascita, data_di_morte, nazionalita, image) VALUES (1, 'Umberto', 'Eco', '1932-01-05', '2016-02-19', 'Italia', '/images/eco.jpg');
INSERT INTO autore (id, nome, cognome, data_di_nascita, data_di_morte, nazionalita, image) VALUES (2, 'Jane', 'Austen', '1775-12-16', '1817-07-18', 'Regno Unito', '/images/austen.jpg');
INSERT INTO autore (id, nome, cognome, data_di_nascita, data_di_morte, nazionalita, image) VALUES (3, 'Haruki', 'Murakami', '1949-01-12', NULL, 'Giappone', '/images/murakami.jpg');




-- Inserimento dei libri nella tabella libro
INSERT INTO libro (id, titolo, anno, image) VALUES (1, 'Il Nome della Rosa', 1980, '/images/nome_rosa.jpg');
INSERT INTO libro (id, titolo, anno, image) VALUES (2, 'Orgoglio e Pregiudizio', 1813, '/images/orgoglio_pregiudizio.jpg'); 
INSERT INTO libro (id, titolo, anno, image) VALUES (3, 'Kafka sulla spiaggia', 2002, '/images/kafka.jpg');

-- Collegamento libri-autori
INSERT INTO libro_autori (libri_id, autori_id) VALUES (1, 1); -- Umberto Eco -> Il Nome della Rosa --
INSERT INTO libro_autori (libri_id, autori_id) VALUES (2, 2); -- Jane Austen -> Orgoglio e Pregiudizio --
INSERT INTO libro_autori (libri_id, autori_id) VALUES (3, 3); -- Haruki Murakami -> Kafka sulla spiaggia --

 

-- Inserimento delle recensioni nella tabella recensione
INSERT INTO recensione (id, titolo, testo, voto,  libro_id, autore) VALUES (1, 'Un classico senza tempo', 'Un romanzo storico straordinario, pieno di intrighi e mistero.', 5, 1, 'Monica_78'); 
INSERT INTO recensione (id, titolo, testo, voto,  libro_id, autore) VALUES (2, 'Perfetto per gli amanti del romance', 'Personaggi affascinanti e una storia coinvolgente.', 4, 2, 'Madman'); 
INSERT INTO recensione (id, titolo, testo, voto,  libro_id, autore) VALUES (3, 'Surreale e ipnotico', 'Murakami riesce a trasportarti in un mondo onirico incredibile.', 5, 3, 'ILoveBooks31');

insert into users(id, name, surname, email) values(1, 'alessia', 'alessia', 'alessia@gmail.com');
insert into credentials(id, password, role, username, user_id) values(1, '$2a$10$oKs4yhhyDX4IKhMZKHsA1eygRwX/v1LrzlteENsoUeNjo43pquQlq', 'ADMIN', 'alessia',1);
-- password è "alessia"

INSERT INTO users(id, name, surname, email) VALUES(2, 'default', 'user', 'default@example.com');
INSERT INTO credentials(id, password, role, username, user_id) VALUES(2, '$2a$10$oKs4yhhyDX4IKhMZKHsA1eygRwX/v1LrzlteENsoUeNjo43pquQlq', 'DEFAULT', 'default_user', 2);


SELECT setval('autore_seq', (SELECT MAX(id) FROM autore));
SELECT setval('libro_seq', (SELECT MAX(id) FROM libro));
SELECT setval('recensione_seq', (SELECT MAX(id) FROM recensione));
SELECT setval('users_seq', (SELECT MAX(id) FROM users));
SELECT setval('credentials_seq', (SELECT MAX(id) FROM credentials));

