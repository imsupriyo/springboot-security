create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

select * from users join authorities a on users.username = a.username;

insert into users values('admin','{bcrypt}$2a$10$DHmE5B1yedr194wBmfZfyOwDmZfMPcnGYJ7ThGjmBE1MCE75QU.by',true);
insert into authorities values ('admin','admin');

update users set password='{bcrypt}$2a$10$UT2ldJqbdNTF/6lMXg4p3Ok4HEvb6l5S4wNGa5QU/t8p778hIhDXC' where username='admin'

update authorities set authority='ROLE_ADMIN' where username='admin';

create table customer(id serial, name varchar(20), email varchar(30), password varchar(80), role varchar(10));

describe customer;

insert into customer(name, email, password, role) values ('test','test@gmail.com', '{noop}test', 'raed');
delete from customer where name='test';

select * from customer;