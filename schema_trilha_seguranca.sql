create database bdusuario;
use bdusuario;
create table dadosusuario (id int(10) auto_increment primary key, usuario VARCHAR(60) , senha CHAR(32));

INSERT INTO dadosusuario (usuario,senha) VALUES ("testando","1817baed7670edc97916d2e36336db06");

select * from dadosusuario;


DROP DATABASE bdusuario





