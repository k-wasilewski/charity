
INSERT INTO institutions(name, description) VALUES("Dbam o Zdrowie", "Cel i misja: Pomoc dzieciom z ubogich rodzin.");
INSERT INTO institutions(name, description) VALUES("Dla dzieci", "Cel i misja: Pomoc osobom znajdującym się w trudnej sytuacji życiowej.");
INSERT INTO institutions(name, description) VALUES("A kogo", "Cel i misja: Pomoc wybudzaniu dzieci ze śpiączki.");
INSERT INTO institutions(name, description) VALUES("Bez domu", "Cel i misja: Pomoc dla osób nie posiadających miejsca zamieszkania");

INSERT INTO donations(quantity, institution_id, pick_up_time, pick_up_date, created, street, city, zip_code) VALUES(5, 1, "12:32", "2020-12-01", "2020-12-01", "frefr", "Warszawa", "02-776");
INSERT INTO donations_categories VALUES(1, 1);
INSERT INTO donations(quantity, institution_id, pick_up_time, pick_up_date, created) VALUES(2, 1, "12:32", "2020-12-01", "2020-12-01");
INSERT INTO donations(quantity, institution_id, pick_up_time, pick_up_date, created) VALUES(7, 2, "12:32", "2020-12-01", "2020-12-01");

INSERT INTO categories(name) VALUES("zabawki");
INSERT INTO categories(name) VALUES("ubrania");
INSERT INTO categories(name) VALUES("jedzenie");

INSERT INTO role(name) VALUES("ROLE_USER");
INSERT INTO role(name) VALUES("ROLE_ADMIN");
INSERT INTO role(name) VALUES("ROLE_INSTITUTION");