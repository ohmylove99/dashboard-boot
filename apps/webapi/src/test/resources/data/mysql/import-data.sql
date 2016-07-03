truncate db_user2role;
truncate db_user;
truncate db_role;

truncate db_task;


insert into db_user (id, login_name, name, password, salt, register_date) values(1,'admin','Admin','0DPiKuNIrrVmD8IUCuw1hQxNqZc=','7efbd59d9741d34f','2012-06-04 01:00:00');

insert into db_role (id, name) values(1,'admin');
insert into db_role (id, name) values(2,'user');
insert into db_role (id, name) values(3,'readonly');

insert into db_user2role (id, user_id,role_id) values(1,1,1);

insert into db_task (id, title, description, user_id) values(1, 'Study Spring Boot ','http://www.springframework.org/', 1);
