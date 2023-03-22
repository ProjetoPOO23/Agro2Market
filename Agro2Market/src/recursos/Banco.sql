create database market;

use market;

create table Agropecuarios(

cod_AP int not null auto_increment primary key,
cnpj varchar(19),
atividade varchar(12), 
razao_social varchar(25),
nomeAP varchar(30),
endereco varchar(40),
telefone varchar(14),
email varchar(30),
senha varchar(20),
saldo double,
ativo boolean);

create table Merceeiros(

cod_M int not null auto_increment primary key,
cnpj varchar(19),
razao_social varchar(25),
nomeM varchar(30),
endereco varchar(40),
telefone varchar(14),
email varchar(30),
senha varchar(20),
saldo double,
ativo boolean);

create table Produtos(
cod_PD int not null auto_increment primary key,
cod_AP int,
FOREIGN KEY (cod_AP) REFERENCES Agropecuarios(cod_AP),
categoria varchar(13),
nomeAP varchar(30),
nomePD varchar(25),
valor float,
quantidade int,
ativo boolean);

create table Comissoes(
cod_C int not null auto_increment primary key,
cod_P int,
FOREIGN KEY (cod_P) REFERENCES Produtos(cod_PD),
cod_AP int,
FOREIGN KEY (cod_AP) REFERENCES Agropecuarios(cod_AP),
cod_M int,
FOREIGN KEY (cod_M) REFERENCES Merceeiros(cod_M),
custo_comissao double,
quantPD int,
nomeAP varchar(30),
nomeM varchar(30),
nomePD varchar(25),
comissaoADM double);

