
DROP TABLE IF EXISTS Teste;

CREATE TABLE Teste (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

INSERT INTO Teste (nome) VALUES ('Teste 1');
INSERT INTO Teste (nome) VALUES ('Teste 2');
INSERT INTO Teste (nome) VALUES ('Ariscleisson');
INSERT INTO Teste (nome) VALUES ('O peito do pé do pai do pedro');

