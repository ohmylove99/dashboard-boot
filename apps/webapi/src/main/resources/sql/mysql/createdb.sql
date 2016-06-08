create database recruit;
grant all on recruit.* to recruit@localhost identified by 'recruit';
GRANT ALL PRIVILEGES ON recruit.* TO 'recruit'@'%' IDENTIFIED BY 'recruit' WITH GRANT OPTION; 
set password for 'recruit'@'localhost' =password('recruit');
flush privileges;