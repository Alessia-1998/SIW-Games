-- Inserimento degli studi di sviluppo nella tabella "sviluppatore" 
INSERT INTO sviluppatore (id, nome, sede, image) VALUES (1, 'Naughty Dog', 'California, USA', '/images/naughtydog.jpg');
INSERT INTO sviluppatore (id, nome, sede, image) VALUES (2, 'FromSoftware', 'Tokyo, Giappone', '/images/fromsoftware.jpg');
INSERT INTO sviluppatore (id, nome, sede, image) VALUES (3, 'CD Projekt Red', 'Varsavia, Polonia', '/images/cdprojekt.jpg');

-- Inserimento delle piattaforme nella tabella "piattaforma" 
INSERT INTO piattaforma (id, nome, descrizione, data_di_rilascio, produttore, image) VALUES (1, 'Steam', 'Negozio digitale per giochi destinati al computer', '1970-01-01', 'Valve Corporation',' /images/steam.jpg');
INSERT INTO piattaforma (id, nome, descrizione, data_di_rilascio, produttore, image) VALUES (2, 'PlayStation 5', 'Console Sony di nuova generazione', '2020-11-12', 'Sony Interactive Entertainment', '/images/ps5.jpg'); 
INSERT INTO piattaforma (id, nome, descrizione, data_di_rilascio, produttore, image) VALUES (3, 'Nintendo Switch', 'Console ibrida di Nintendo', '2017-03-03', 'Nintendo Co. Ltd.','/images/switch.jpg');


-- Inserimento dei videogiochi nella tabella "videogioco"
INSERT INTO videogioco (id, titolo, anno, descrizione, genere, sviluppatore_id, image) VALUES (1, 'The Last of Us Part II', 2020, 'Action-adventure drammatico', 'Avventura', 1, '/images/tlou2.jpg');
INSERT INTO videogioco (id, titolo, anno, descrizione, genere, sviluppatore_id, image) VALUES (2, 'Elden Ring', 2022, 'Gioco di ruolo con mondo aperto', 'RPG', 2, '/images/eldenring.jpg');
INSERT INTO videogioco (id, titolo, anno, descrizione, genere, sviluppatore_id, image) VALUES (3, 'Cyberpunk 2077', 2020, 'Gioco di ruolo ambientato nel futuro', 'RPG', 3, '/images/cyberpunk2077.jpg');



-- Associazione dei videogiochi alle piattaforme nella tabella "videogioco_piattaforma"
INSERT INTO piattaforma_videogiochi (videogiochi_id, piattaforme_id) VALUES (1, 2); -- The Last of Us su PlayStation 5
INSERT INTO piattaforma_videogiochi (videogiochi_id, piattaforme_id) VALUES (2, 1); -- Elden Ring su PC
INSERT INTO piattaforma_videogiochi (videogiochi_id, piattaforme_id) VALUES (2, 2); -- Elden Ring su PlayStation 5
INSERT INTO piattaforma_videogiochi (videogiochi_id, piattaforme_id) VALUES (3, 1); -- Cyberpunk 2077 su PC

insert into users(id, name, surname, email) values(1, 'alessia', 'alessia', 'alessia@gmail.com');
insert into credentials(id, password, role, username, user_id) values(1, '$2a$10$oKs4yhhyDX4IKhMZKHsA1eygRwX/v1LrzlteENsoUeNjo43pquQlq', 'ADMIN', 'alessia',1);
-- password è "alessia" sia su alessia (admin) sia su default_user

INSERT INTO users(id, name, surname, email) VALUES(2, 'default', 'user', 'default@example.com');
INSERT INTO credentials(id, password, role, username, user_id) VALUES(2, '$2a$10$oKs4yhhyDX4IKhMZKHsA1eygRwX/v1LrzlteENsoUeNjo43pquQlq', 'DEFAULT', 'default_user', 2);


SELECT setval('sviluppatore_seq', (SELECT MAX(id) FROM sviluppatore));
SELECT setval('videogioco_seq', (SELECT MAX(id) FROM videogioco));
SELECT setval('piattaforma_seq', (SELECT MAX(id) FROM piattaforma));
SELECT setval('users_seq', (SELECT MAX(id) FROM users));
SELECT setval('credentials_seq', (SELECT MAX(id) FROM credentials));

