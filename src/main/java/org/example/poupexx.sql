CREATE DATABASE poupexx

SHOW DATABASES

USE poupexx

CREATE TABLE cliente(
cpf varchar (14) unique not null,
cliente varchar (56) not null, 
juros decimal not null,  
anos int(11) not null,
deposito decimal(11) not null, 
primary key (cpf))

select * from cliente



