DROP DATABASE IF EXISTS restaurace;

CREATE DATABASE IF NOT EXISTS restaurace DEFAULT CHARACTER SET utf8mb4;
USE restaurace;

CREATE TABLE IF NOT EXISTS objednavky (
	id INT NOT NULL AUTO_INCREMENT,
	stav INT NOT NULL DEFAULT 1,
	PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS polozky (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	jmeno VARCHAR(45) NOT NULL,
	cena INT UNSIGNED NOT NULL,
	typ INT UNSIGNED NOT NULL,
	PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS objednavky_polozky (
	id INT UNSIGNED AUTO_INCREMENT,
	objednavky_id INT NOT NULL,
	polozky_id INT UNSIGNED NOT NULL,

	FOREIGN KEY (objednavky_id) REFERENCES objednavky(id)
	ON DELETE CASCADE,

	FOREIGN KEY (polozky_id) REFERENCES polozky(id)
	ON DELETE CASCADE
) engine = InnoDB;

INSERT INTO polozky (jmeno, cena, typ)
VALUES
("Hranolky",25,0),
("Salat",15,0),
("Rustic Fries",30,0),
("McChicken",35,1),
("Tasty Chicken",37,1),
("Chickenburger",28,1),
("Crispy Chicken Deluxe",60,1),
("Maestro Fresh Chicken",55,1),
("Kecup",12,2),
("Horcice",15,2),
("Tatarska omacka",10,2),
("Sladkokysela omacka",17,2),
("Barbecue omacka",20,2),
("Coca-Cola",30,3),
("Fanta",30,3),
("Vinea",35,3),
("Coca-Cola Zero",40,3),
("Sodova voda",25,3),
("Lipton Ice Tea Citron",30,3),
("Bez okurky",0,4),
("Extra syr",10,4),
("Bez rajcete",0,4),
("Extra maso",15,4),
("Extra salat",5,4),
("Bez salatu",0,4);

INSERT INTO objednavky (stav)
VALUES 
(1),
(1),
(2);

INSERT INTO objednavky_polozky (objednavky_id, polozky_id)
VALUES 
(1, 2),
(1, 3),
(1, 2),
(2, 4),
(2, 5),
(2, 6),
(3, 7),
(3, 8),
(3, 9);