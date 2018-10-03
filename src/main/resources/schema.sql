	create table if not exists user (
 	id int not null AUTO_INCREMENT,
 	first_name varchar(255),
    last_name varchar(255),
    email_id varchar(255) unique not null,
     password varchar(255) not null,
     PRIMARY KEY(id))
     ENGINE = INNODB;
     
    create  table IF not exists category (
    id int  not null AUTO_INCREMENT,
    category_name varchar(255),
    PRIMARY key(id)
    )
    ENGINE = INNODB;

    create table IF not exists expense(
    id int  not null AUTO_INCREMENT,
    user_id int not null,
    title varchar(255),
    category_id int,
    date Date not null,
    amount REAL,
    description text,
    PRIMARY key (id),
    foreign key (user_id) references user(id),
    foreign key (category_id) references category(id)
    )
    ENGINE = INNODB;
