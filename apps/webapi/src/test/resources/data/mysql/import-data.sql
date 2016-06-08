insert into ss_task (id, title, description, user_id) values(1, 'Study Play ','http://www.playframework.org/', 1);

insert into ss_user (id, login_name, name, password, salt, roles, register_date) values(1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-04 01:00:00');

insert into ss_resume (id, name, description, kvs,version) values(1, 'Ive','This is Ive'' desc', 'name:Ive;gendar:male;',1);
insert into ss_resume (id, name, description, kvs,version) values(2, 'Ive','This is Ive'' desc version2', 'name:Ive;gendar:male;',2);
insert into ss_resume (id, name, description, kvs,job_id) values(3, 'Jason','This is Jason'' desc', 'name:Jason;gendar:male;',1);
insert into ss_resumehis (id, name, description, kvs,job_id,pid,version) values(2, 'Jason','This is Jason'' desc', 'name:Jason;gendar:male;',1,1,1);

insert into ss_job (id, name, description, grade,version) values(1, '.Net Sr. Developer','http://www.playframework.org/', 'G1',1);
insert into ss_job (id, name, description, grade,version) values(2, 'C++ Sr. Developer','http://www.playframework.org/', 'G1',2);
insert into ss_jobhis (id, name, description, grade,pid,version) values(2, 'C++ Sr. Developer','http://www.playframework.org/', 'G1',2,1);

insert into ss_m_emptype (id, name) values(1, 'DH');
insert into ss_m_emptype (id, name) values(2, 'VD');

insert into ss_m_jobstatus (id, name, isactive) values(1, 'Request', 0);
insert into ss_m_jobstatus (id, name, isactive) values(2, 'RequestApproved', 0);
insert into ss_m_jobstatus (id, name, isactive) values(3, 'Open', 1);
insert into ss_m_jobstatus (id, name, isactive) values(4, 'Re-Open', 1);
insert into ss_m_jobstatus (id, name, isactive) values(5, 'Open-Offer', 1);
insert into ss_m_jobstatus (id, name, isactive) values(6, 'Open-Onboard', 1);
insert into ss_m_jobstatus (id, name, isactive) values(7, 'Close', 1);

insert into ss_m_interviewstatus (id, name) values(1, 'Pass');
insert into ss_m_interviewstatus (id, name) values(2, 'Reject');

insert into ss_m_interviewround (id, name) values(1, 'Tel-1st');
insert into ss_m_interviewround (id, name) values(2, 'Tel-2st');
insert into ss_m_interviewround (id, name) values(3, 'Tel-3st');
insert into ss_m_interviewround (id, name) values(4, 'F2F-1st');
insert into ss_m_interviewround (id, name) values(5, 'F2F-2st');
insert into ss_m_interviewround (id, name) values(6, 'F2F-3st');
insert into ss_m_interviewround (id, name) values(7, 'Final');

insert into ss_m_jobgrade (id, name) values(1, 'C8');
insert into ss_m_jobgrade (id, name) values(2, 'C9');
insert into ss_m_jobgrade (id, name) values(3, 'C10');
insert into ss_m_jobgrade (id, name) values(4, 'C11');
insert into ss_m_jobgrade (id, name) values(5, 'C12');
insert into ss_m_jobgrade (id, name) values(6, 'C13');
insert into ss_m_jobgrade (id, name) values(7, 'C14');

insert into ss_m_biz (id, name) values(1, 'dept1-team1');
insert into ss_m_biz (id, name) values(2, 'dept1-team2');
insert into ss_m_biz (id, name) values(3, 'dept2-team1');

insert into ss_m_resumestatus (id, name, isactive) values(1, 'Resume-Review', 0);
insert into ss_m_resumestatus (id, name, isactive) values(2, 'Resume-Review-Reject', 0);
insert into ss_m_resumestatus (id, name, isactive) values(3, 'Resume-Review-Pass', 0);
insert into ss_m_resumestatus (id, name, isactive) values(4, 'Interviewing', 0);
insert into ss_m_resumestatus (id, name, isactive) values(5, 'Reject', 1);
insert into ss_m_resumestatus (id, name, isactive) values(6, 'Pass', 1);
insert into ss_m_resumestatus (id, name, isactive) values(7, 'Offer', 1);
insert into ss_m_resumestatus (id, name, isactive) values(8, 'Offer-Reject', 1);
insert into ss_m_resumestatus (id, name, isactive) values(9, 'Offer-Pass', 1);
insert into ss_m_resumestatus (id, name, isactive) values(10, 'Onboarding', 1);
insert into ss_m_resumestatus (id, name, isactive) values(11, 'Onboarded', 1);

insert into ss_m_status (id, name,isactive,dtype) values(1, 'DH',1,'EMPTYPE');
insert into ss_m_status (id, name,isactive,dtype) values(2, 'VD',1,'EMPTYPE');

insert into ss_m_status (id, name,isactive,dtype) values(3, 'Request',0,'JOBSTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(4, 'Open',1,'JOBSTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(5, 'Re-Open',1,'JOBSTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(6, 'Open-Offer',1,'JOBSTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(7, 'Open-Onboard',1,'JOBSTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(8, 'Close',1,'JOBSTATUS');

insert into ss_m_status (id, name,isactive,dtype) values(9, 'Pass',1,'INTERVIEWSTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(10, 'Reject',1,'INTERVIEWSTATUS');

insert into ss_m_status (id, name,isactive,dtype) values(11, 'C8',1,'JOBGRADE');
insert into ss_m_status (id, name,isactive,dtype) values(12, 'C9',1,'JOBGRADE');
insert into ss_m_status (id, name,isactive,dtype) values(13, 'C10',1,'JOBGRADE');
insert into ss_m_status (id, name,isactive,dtype) values(14, 'C11',1,'JOBGRADE');
insert into ss_m_status (id, name,isactive,dtype) values(16, 'C12',1,'JOBGRADE');
insert into ss_m_status (id, name,isactive,dtype) values(17, 'C13',1,'JOBGRADE');
insert into ss_m_status (id, name,isactive,dtype) values(18, 'C14',1,'JOBGRADE');

insert into ss_m_status (id, name,isactive,dtype) values(19, 'Resume-Review',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(20, 'Resume-Review-Reject',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(21, 'Resume-Review-Pass',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(22, 'Interviewing',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(23, 'Interview-Reject',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(24, 'Interview-Pass',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(25, 'Offering',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(26, 'Offer-Reject',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(27, 'Offer-Taken',1,'RESUMESTATUS');
insert into ss_m_status (id, name,isactive,dtype) values(28, 'Onboard',1,'RESUMESTATUS');

insert into tt_project (id, name) values(1, 'My Project');
insert into tt_project (id, name) values(2, 'Your Project');

insert into tt_task (id, version, name) values(1, 1, 'My Task Current');
insert into tt_taskaudit (id, version, name) values(1, 1, 'My Task History1');
insert into tt_taskaudit (id, version, name) values(2, 2, 'My Task History2');
insert into tt_taskaudit (id, version, name) values(3, 3, 'My Task History3');