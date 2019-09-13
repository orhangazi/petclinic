insert into users values('user1','{bcrypt}$2a$10$vbRLjz3vW7p9xYKmDOhX7.FLrhoeRXU5jEGG4MsroeV9Hn2eQy/1C',true);
insert into users values('user2','{bcrypt}$2a$10$5eFt.jRfyCTPs6JzPCKeNebrzs07UUb.sp3m4AcdNfKtfQVhsMRWq',true);
insert into users values('user3','{bcrypt}$2a$10$Vjy.1qCztdqUA6EE7yIA4.3iiNpARfsy.1yPS1etAfEcOP7SlzeAS',true);

insert into authorities values('user1','ROLE_USER');
insert into authorities values('user2','ROLE_USER');
insert into authorities values('user2','ROLE_EDITOR');
insert into authorities values('user3','ROLE_USER');
insert into authorities values('user3','ROLE_EDITOR');
insert into authorities values('user3','ROLE_ADMIN');

insert into t_owner(id,first_name,last_name) values(1,'osman','kılıç');
insert into t_owner(id,first_name,last_name) values(2,'fadime','kılıç');
insert into t_owner(id,first_name,last_name) values(3,'oğuzhan','kılıç');
insert into t_owner(id,first_name,last_name) values(4,'orhan','kılıç');
insert into t_owner(id,first_name,last_name) values(5,'meral','doğan');

insert into t_pet(id,first_name,birth_date,owner_id) values(1,'barut','2018-04-01',1);
insert into t_pet(id,first_name,birth_date,owner_id) values(2,'tonny','2018-04-01',1);
insert into t_pet(id,first_name,birth_date,owner_id) values(3,'cessy','2018-04-01',1);
insert into t_pet(id,first_name,birth_date,owner_id) values(4,'lessy','2018-03-01',2);
insert into t_pet(id,first_name,birth_date,owner_id) values(5,'tony','2018-02-01',3);
insert into t_pet(id,first_name,birth_date,owner_id) values(6,'penny','2018-01-01',4);
insert into t_pet(id,first_name,birth_date,owner_id) values(7,'şeker','2019-02-01',5);