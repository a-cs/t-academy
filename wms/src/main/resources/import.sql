insert into role(authority) values (0);
insert into role(authority) values (1);
insert into role(authority) values (2);
insert into role(authority) values (3);
insert into role(authority) values (4);

insert into user(username, password, enabled) values ('client_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true)
insert into user(username, password, enabled) values ('operator_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true)
insert into user(username, password, enabled) values ('branch_manager_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true)
insert into user(username, password, enabled) values ('manager_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true)
insert into user(username, password, enabled) values ('admin_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true)

insert into tb_user_roles(user_id, role_id) values (1, 1)
insert into tb_user_roles(user_id, role_id) values (2, 2)
insert into tb_user_roles(user_id, role_id) values (3, 3)
insert into tb_user_roles(user_id, role_id) values (4, 4)
insert into tb_user_roles(user_id, role_id) values (5, 5)