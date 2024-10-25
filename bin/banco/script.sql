CREATE DATABASE IF NOT EXISTS Biblioteca;

CREATE TABLE editora (
id int auto_increment not null primary key,
nome varchar(100) not null,
endereco varchar(45),
status varchar(45) default "ATIVO"
);

CREATE TABLE area (
id int auto_increment not null primary key,
descricao varchar(100) not null,
status varchar(45) default "ATIVO"
);

CREATE TABLE autor (
id int auto_increment not null primary key,
nome varchar(100) not null,
endereco varchar(45),
status varchar(45) default "ATIVO"
);

CREATE TABLE tipo_usuario (
id int auto_increment not null primary key,
descricao varchar(100) not null,
dias_emprestimo int not null,
multa_diaria float not null
);

CREATE TABLE usuario (
	id int auto_increment not null primary key,
	nome varchar(100) not null,
	email varchar(100) not null,
	senha varchar(32) not null,
	tipo_usuario_id int,
    status varchar(45) default 'ATIVO',
    FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id)
);

CREATE TABLE livro (
	id int auto_increment not null primary key,
    titulo varchar(100) not null,
    edicao int,
    resumo varchar(5000),
	ano_publicacao int,
    codigo varchar(45),
    quantidade int not null,
    quantidade_disponivel int,
    img varchar(100),
    editora_id int,
    area_id int,
    status varchar(45) default "ATIVO",
    foreign key (editora_id) references editora(id),
    foreign key (area_id) references area(id)
);

CREATE TABLE livro_autor (
	id int auto_increment not null primary key,
    autor_id int not null,
    livro_id int not null,
    foreign key (autor_id) references autor(id),
    foreign key (livro_id) references livro(id) 
);

CREATE TABLE emprestimo (
	id int auto_increment not null primary key,
	data_emprestimo date not null,
    data_previsao date not null,
    data_entrega date,
    multa float,
    livro_id int not null,
    usuario_id int not null,
    status varchar(45) default "ATIVO",
    foreign key (livro_id) references livro(id),
    foreign key (usuario_id) references usuario(id)
);

INSERT INTO editora (nome, endereco) values 
	("Editora 1", "Rua dos Campos, 1001, Centro, Jaquarita"),
	("Editora 2", "Rua dos Principes, 500, Centro, São Paulo"),
	("Editora 3", "Rua dos Marsupiais, 666, Centro, Curitiba");

INSERT INTO autor (nome, endereco) values 
	("Edmilson Gonsalves", "Rua Rui Bento, Centro, Jardopolis"),
    ("Amanda de Lima", "Rua Rui Bento, Centro, Jardopolis");
    
INSERT INTO area (descricao) 
values 
	("Saúde"),
	("Exatas"),
    ("Humanas"),
    ("Tecnologia da Informação"),
    ("Engenharias");
    
INSERT INTO livro (titulo, edicao, resumo, ano_publicacao, editora_id, area_id, codigo, quantidade, quantidade_disponivel) values
	("Código Limpo", 1, "Esse livro te ensina a programar com um código limpo", 2000, 1, 4, "abcdefgh", 1, 1),
	("Como Programar Bem", 2, "Esse livro te ensina a programar bem", 2014, 2, 4, "oiagskco2", 2, 2),
	("Farmacia Entenda Tudo Sobre", 1, "Esse livro te ensina farmácia", 2008, 1, 1, "kjshg1kj2", 3, 3),
    ("O Corpo Humano", 1, "Esse livro te sobre o corpo humano", 2008, 1, 1, "kjshg1kj2", 3, 3);

INSERT INTO livro_autor (autor_id, livro_id) values 
	(1, 1),
    (1, 2),
    (2, 3),
    (2, 4);

INSERT INTO tipo_usuario (descricao, dias_emprestimo, multa_diaria) 
values
	("ADMIN", 0, 0),
	("PROFESSOR", 7, 2.50),
    ("ALUNO", 15, 3.50);

INSERT INTO usuario (nome, email, senha, status, tipo_usuario_id) values 
	("Aluno 1", "aluno@teste.com", "12345", "ATIVO" , 2);
    
INSERT INTO usuario (nome, email, senha, tipo_usuario_id) values
("Aluno Teste", "aluno1@teste.com", "1234", 2);

