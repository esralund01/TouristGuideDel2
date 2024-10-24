CREATE SCHEMA touristguidedb;
USE touristguidedb;

CREATE TABLE cities(
	cityId INT AUTO_INCREMENT,
	cityName VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY(cityId)
);

CREATE TABLE attractions(
	attractionId INT AUTO_INCREMENT,
    attractionName VARCHAR(100) NOT NULL UNIQUE,
    attractionDescription VARCHAR(100) NOT NULL,
    city INT NOT NULL,
    PRIMARY KEY(attractionId),
    FOREIGN KEY(city) REFERENCES cities(cityId)
);

CREATE TABLE tags(
	tagId INT AUTO_INCREMENT,
	tagName VARCHAR(100) NOT NULL UNIQUE,
	PRIMARY KEY(tagId)
);

CREATE TABLE attractions_tags(
	attractionId INT,
    tagId INT,
    PRIMARY KEY(attractionId, tagId),
	FOREIGN KEY (attractionId) REFERENCES attractions(attractionId) ON DELETE CASCADE,
    FOREIGN KEY (tagId) REFERENCES tags(tagId)
);


INSERT INTO cities (cityName) VALUES ('København');
INSERT INTO cities (cityName) VALUES ('Odense');
INSERT INTO cities (cityName) VALUES ('Kongens Lyngby');
INSERT INTO cities (cityName) VALUES ('Albertslund');

INSERT INTO tags (tagName) VALUES('Museum');
INSERT INTO tags (tagName) VALUES('Kunst');
INSERT INTO tags (tagName) VALUES('Børnevenlig');
INSERT INTO tags (tagName) VALUES('Natur');
INSERT INTO tags (tagName) VALUES('Gratis');