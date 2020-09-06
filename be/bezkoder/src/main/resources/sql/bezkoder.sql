DROP SCHEMA IF EXISTS `bezkoder`;

CREATE SCHEMA `bezkoder`;
USE `bezkoder` ;

create table role (
id integer NOT NULL AUTO_INCREMENT, 
name varchar(20), 
user_id bigint, 
primary key (id))
ENGINE=InnoDB
AUTO_INCREMENT = 1;

create table user (
id bigint NOT NULL AUTO_INCREMENT, 
email varchar(50), 
password varchar(120), 
username varchar(20), 
primary key (id))
ENGINE=InnoDB
AUTO_INCREMENT = 1;

alter table role add constraint FK_UR foreign key (user_id) references user(id);
create table file_info (id varchar(255) not null, data blob, name varchar(255), type varchar(255), primary key (id));