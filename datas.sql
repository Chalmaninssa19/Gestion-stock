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


CREATE TABLE article (
    id_article SERIAL PRIMARY KEY,
    id_categorie_article INTEGER,
    code VARCHAR(50),
    designation VARCHAR(50),
    id_unity INTEGER,
    id_unity_equivalent INTEGER,
    quantity_equivalent DOUBLE PRECISION,
    type_mvt INTEGER,
    unit_price DOUBLE PRECISION,
    status INTEGER,
    FOREIGN KEY(id_categorie_article) REFERENCES categorie_article(id_categorie_article),
    FOREIGN KEY(id_unity) REFERENCES unity(id_unity),
    FOREIGN KEY(id_unity_equivalent) REFERENCES unity_equivalent(id_unity_equivalent)
);

CREATE VIEW v_article AS SELECT a.id_article,a.code AS code_article,a.designation AS article, u.designation AS unity,c.code AS code_categorie,
c.designation AS categorie,a.type_mvt,a.unit_price,a.id_unity_equivalent,e.designation,a.quantity_equivalent FROM article a 
JOIN unity u ON a.id_unity=u.id_unity 
JOIN categorie_article c ON a.id_categorie_article=c.id_categorie_article
JOIN unity_equivalent e ON a.id_unity_equivalent=e.id_unity_equivalent;

CREATE TABLE adress (
    id_adress SERIAL PRIMARY KEY,
    designation VARCHAR(100)
);

CREATE TABLE store (
    id_store SERIAL PRIMARY KEY,
    date_creation DATE,
    name VARCHAR(50),
    id_adress INTEGER,
    surface DOUBLE PRECISION,
    status INTEGER,
    FOREIGN KEY(id_adress) REFERENCES adress(id_adress)
);

CREATE VIEW v_store AS SELECT s.id_store,s.date_creation,s.name AS store,a.designation AS adress,s.surface FROM store s
JOIN adress a ON s.id_adress=a.id_adress;

-- status = 0 -> refuser; status = 1 -> en cours; status = 10 -> valider --
CREATE TABLE enter (
    id_in SERIAL PRIMARY KEY,
    date_in DATE,
    id_article INTEGER,
    id_store INTEGER,
    quantity DOUBLE PRECISION,
    unitary_cost DOUBLE PRECISION,
    quantity_rest DOUBLE PRECISION,
    id_unity_equivalent INTEGER,
    status INTEGER,
    FOREIGN KEY(id_article) REFERENCES article(id_article),
    FOREIGN KEY(id_store) REFERENCES store(id_store),
    FOREIGN KEY(id_unity_equivalent) REFERENCES unity_equivalent(id_unity_equivalent)
 );

CREATE TABLE request_out(
    id_request_out SERIAL PRIMARY KEY,
    date_request_out DATE,
    id_article INTEGER,
    id_store INTEGER,
    quantity DOUBLE PRECISION,
    id_unity_equivalent INTEGER,
    status INTEGER,
    FOREIGN KEY(id_article) REFERENCES article(id_article),
    FOREIGN KEY(id_store) REFERENCES store(id_store),
    FOREIGN KEY(id_unity_equivalent) REFERENCES unity_equivalent(id_unity_equivalent)
);

CREATE TABLE out (
    id_out SERIAL PRIMARY KEY,
    id_in INTEGER,
    id_request_out INTEGER,
    quantity DOUBLE PRECISION,
    date_validation DATE,
    FOREIGN KEY (id_in) REFERENCES enter(id_in),
    FOREIGN KEY (id_request_out) REFERENCES request_out(id_request_out)
); 

CREATE VIEW v_out_request_out AS SELECT r.id_request_out,o.id_out,o.id_in,r.date_request_out,o.date_validation,va.id_article, va.code_article, va.article, 
va.unity AS unity,vs.id_store,vs.date_creation AS date_creation_magasin,vs.store,vs.adress,r.quantity AS quantity_global,o.quantity AS quantity_outted,
r.status
FROM request_out r JOIN out o ON r.id_request_out=o.id_request_out 
JOIN v_article va ON va.id_article=r.id_article
JOIN v_store vs ON vs.id_store=r.id_store;

CREATE VIEW v_enter_out AS SELECT vo.id_in,vo.id_out,e.date_in,vo.date_validation,vo.id_article,vo.id_store,vo.quantity_outted AS quantity_out,
e.unitary_cost 
FROM enter e JOIN v_out_request_out vo ON e.id_in = vo.id_in;

INSERT INTO unity_equivalent (designation) VALUES 
('Cageaot biere'),
('Sachet biscuit'),
('Carton fromage');

-- Sample data for categorie_article table
INSERT INTO categorie_article (code, designation) VALUES
('S001', 'Snacks'),
('B001', 'Boisson'),
('L001', 'Laitier');

-- Sample data for unity table
INSERT INTO unity (designation) VALUES
('unite');

-- Sample data for article table
INSERT INTO article (id_categorie_article, code, designation, id_unity, type_mvt, unit_price, id_unity_equivalent, quantity_equivalent, status) VALUES
(5, 'S0011', 'Biscuit', 5, 1, 10.99, 7, 50, 1),
(6, 'B0011', 'THB', 5, 2, 12.99, 6, 8, 1),
(7, 'L0011', 'Fromage', 5, 2, 3.99, 8, 10, 1);

-- Sample data for adress table
INSERT INTO adress (designation) VALUES
('ANDOHARANOFOTSY'),
('TANJOMBATO'),
('ANALAKELY'),
('67 HA'),
('ANTANIMENA');

-- Sample data for store table
INSERT INTO store (date_creation, name, id_adress, surface, status) VALUES
('2023-01-01', 'Store 1', 1, 500.0, 1),
('2023-02-01', 'Store 2', 2, 700.0, 1),
('2023-03-01', 'Store 3', 3, 600.0, 1);
