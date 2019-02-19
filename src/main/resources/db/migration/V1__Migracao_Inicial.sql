CREATE TABLE contato (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(150) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY nome (nome)
);

CREATE TABLE telefone (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  ddd varchar(3) NOT NULL,
  numero varchar(15) NOT NULL,
  tipo varchar(20) NOT NULL,
  id_contato bigint(20) NOT NULL,
  PRIMARY KEY (id),
  KEY id_contato (id_contato),
  CONSTRAINT FK_ID_CONTATO_TELEFONE FOREIGN KEY (id_contato) REFERENCES contato (id)
);