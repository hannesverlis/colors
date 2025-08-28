-- Lihtsam variant - sisestame ainult põhivärvid
-- Mixed_color andmed lisame hiljem käsitsi või API kaudu

-- Põhivärvid (ID-d 0-3)
INSERT INTO color (name, r, g, b, hex) VALUES ('Punane', 255, 0, 0, '#FF0000');
INSERT INTO color (name, r, g, b, hex) VALUES ('Roheline', 0, 255, 0, '#00FF00');
INSERT INTO color (name, r, g, b, hex) VALUES ('Sinine', 0, 0, 255, '#0000FF');
INSERT INTO color (name, r, g, b, hex) VALUES ('Kollane', 255, 255, 0, '#FFFF00');

-- Testimiseks: kontrolli ID väärtusi
-- Andmebaasi käivitamise järel saad IntelliJ IDEA-s vaadata:
-- SELECT * FROM color ORDER BY id;