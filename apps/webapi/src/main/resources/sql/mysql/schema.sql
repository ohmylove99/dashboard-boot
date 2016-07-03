drop table if exists db_user;
drop table if exists db_role;
drop table if exists db_user2role;

drop table if exists db_task;

create table db_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	register_date timestamp not null default 0,
	primary key (id)
) engine=InnoDB;

create table db_role (
	id bigint auto_increment,
	name varchar(64) not null unique,
	primary key (id)
) engine=InnoDB;

create table db_user2role (
	id bigint auto_increment,
	user_id bigint,
	role_id bigint,
    primary key (id)
) engine=InnoDB;

create table db_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;
