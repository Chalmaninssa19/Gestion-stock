CREATE DATABASE gestion_stock;

.\c gestion_stock;

CREATE TABLE categorie_article(
    id_categorie_article SERIAL PRIMARY KEY,
    code VARCHAR(50),
    designation VARCHAR(50)
);

CREATE TABLE unity (
    id_unity SERIAL PRIMARY KEY,
    designation VARCHAR(50)
);


CREATE TABLE unity_equivalent (
    id_unity_equivalent SERIAL PRIMARY KEY,
    designation VARCHAR(50)
);

