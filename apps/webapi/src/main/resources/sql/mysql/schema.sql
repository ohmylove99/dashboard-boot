drop table if exists ss_task;
drop table if exists ss_user;
drop table if exists ss_resume;
drop table if exists ss_resumehis;
drop table if exists ss_job;
drop table if exists ss_jobhis;

drop table if exists ss_interview;
drop table if exists ss_interviewhis;

drop table if exists ss_m_emptype;
drop table if exists ss_m_jobstatus;
drop table if exists ss_m_interviewstatus;
drop table if exists ss_m_interviewround;
drop table if exists ss_m_resumestatus;
drop table if exists ss_m_jobgrade;
drop table if exists ss_m_biz;

drop table if exists ss_job2resume;
drop table if exists ss_resume2interview;

drop table if exists ss_m_status;

drop table if exists tt_project;
drop table if exists tt_task;
drop table if exists tt_taskaudit;

create table ss_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table ss_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp default 0,
	primary key (id)
) engine=InnoDB;

create table ss_resume (
	id bigint auto_increment,
	name varchar(128) not null,
	description varchar(255),
	contact_info varchar(255),
	kvs varchar(255),/*Key-Value*/
	original_doc blob,
	converted_doc blob,
	status varchar(64),
	skills varchar(256),
	upload_file_name varchar(256),
	upload_file_link varchar(256),
	job_id bigint,
	version bigint,
	updated_time timestamp default 0,
	created_time timestamp default 0,
	updated_by varchar(64),
	created_by varchar(64),
    primary key (id)
) engine=InnoDB;

create table ss_resumehis (
	id bigint auto_increment,
	name varchar(128) not null,
	description varchar(255),
	contact_info varchar(255),
	kvs varchar(255),/*Key-Value*/
	original_doc blob,
	converted_doc blob,
	status varchar(64),
	skills varchar(256),
	upload_file_name varchar(256),
	upload_file_link varchar(256),
	job_id bigint,
	pid bigint,
	version bigint,
	updated_time timestamp default 0,
	created_time timestamp default 0,
	updated_by varchar(64),
	created_by varchar(64),
    primary key (id)
) engine=InnoDB;

create table ss_job (
	id bigint auto_increment,
	name varchar(64),
	description varchar(64),
	referenceid varchar(64),
	grade varchar(64), /**/
	emptype varchar(64),/*VD|DH*/
	status varchar(64),
	open_time timestamp default 0,
	closed_time timestamp default 0,
	open_by varchar(64),
	open_by_biz varchar(64),
	interviewer varchar(64),
	closed_by varchar(64),
	version bigint,
	updated_time timestamp default 0,
	updated_by varchar(64),
	primary key (id)
) engine=InnoDB;

create table ss_jobhis (
	id bigint auto_increment,
	name varchar(64),
	description varchar(64),
	referenceid varchar(64),
	grade varchar(64),
	emptype varchar(64),
	status varchar(64),
	open_time timestamp default 0,
	closed_time timestamp default 0,
	open_by varchar(64),
	open_by_biz varchar(64),
	interviewer varchar(64),
	closed_by varchar(64),
	pid bigint,
	version bigint,
	updated_time timestamp default 0,
	updated_by varchar(64),
	primary key (id)
) engine=InnoDB;

create table ss_job2resume (
	id bigint auto_increment,
	job_id bigint,
	resume_id bigint,
	primary key (id)
) engine=InnoDB;

create table ss_resume2interview (
	id bigint auto_increment,
	resume_id bigint,
	interview_id bigint,
	primary key (id)
) engine=InnoDB;

create table tt_project (
	id bigint auto_increment,
	name varchar(128),
    primary key (id)
) engine=InnoDB;

create table tt_task (
	id bigint auto_increment,
	version bigint,
	name varchar(64),
	updated_time timestamp null default CURRENT_TIMESTAMP,
	updated_by varchar(64),
    primary key (id)
) engine=InnoDB;

create table tt_taskaudit (
	id bigint auto_increment,
	version bigint,
	pid bigint,
	name varchar(64),
	updated_time timestamp null default CURRENT_TIMESTAMP,
	updated_by varchar(64),
    primary key (id)
) engine=InnoDB;

create table ss_m_emptype (
	id bigint auto_increment,
	name varchar(128),
    primary key (id)
) engine=InnoDB;

create table ss_m_jobstatus (
	id bigint auto_increment,
	isactive int,
	name varchar(128),
    primary key (id)
) engine=InnoDB;

create table ss_m_interviewstatus (
	id bigint auto_increment,
	name varchar(128),
    primary key (id)
) engine=InnoDB;

create table ss_m_interviewround (
	id bigint auto_increment,
	name varchar(128),
    primary key (id)
) engine=InnoDB;


create table ss_m_jobgrade (
	id bigint auto_increment,
	name varchar(128),
    primary key (id)
) engine=InnoDB;

create table ss_m_biz (
	id bigint auto_increment,
	name varchar(128),
    primary key (id)
) engine=InnoDB;

create table ss_m_resumestatus (
	id bigint auto_increment,
	isactive int,
	name varchar(128),
    primary key (id)
) engine=InnoDB;

create table ss_m_status (
	id bigint auto_increment,
	isactive int,
	name varchar(128),
	description varchar(128),
	dtype varchar(64),
    primary key (id)
) engine=InnoDB;

create table ss_interview (
	id bigint auto_increment,
	round varchar(128) not null,
	comments varchar(255),
	interview_by varchar(64),
	status varchar(64),
	updated_time timestamp default 0,
	updated_by varchar(64),
	version bigint,
    primary key (id)
) engine=InnoDB;

create table ss_interviewhis (
	id bigint auto_increment,
	round varchar(128) not null,
	comments varchar(255),
	interview_by varchar(64),
	status varchar(64),
	updated_time timestamp default 0,
	updated_by varchar(64),
	version bigint,
	pid bigint,
    primary key (id)
) engine=InnoDB;