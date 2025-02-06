
DROP TABLE IF EXISTS Exemplo;

CREATE TABLE Exemplo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

INSERT INTO Exemplo (nome) VALUES ('Exemplo 1');
INSERT INTO Exemplo (nome) VALUES ('Exemplo 2');
INSERT INTO Exemplo (nome) VALUES ('Exemplo 3');
INSERT INTO Exemplo (nome) VALUES ('Exemplo 4');

