insert into role(authority) values (0);
insert into role(authority) values (1);
insert into role(authority) values (2);
insert into role(authority) values (3);
insert into role(authority) values (4);

insert into user(username, password, email, enabled, access_level_id) values ('client_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG','client_user@mail.com', true, 1)
insert into user(username, password, email, enabled, access_level_id) values ('operator_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG','operator_user@mail.com', true, 2)
insert into user(username, password, email, enabled, access_level_id) values ('branch_manager_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG','branch_manager_user@mail.com', true, 3)
insert into user(username, password, email, enabled, access_level_id) values ('manager_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG','manager_user@mail.com', true, 4)
insert into user(username, password, email, enabled, access_level_id) values ('admin_user', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG','admin_user@mail.com', true, 5)

insert into address(street, number, city, state, zip_code) values ("Rua Exemplo da Costa", "999", "Brasília", "DF", "12345678")
insert into address(street, number, city, state, zip_code) values ("Rua Exemplo da Silva", "123", "São José dos Campos", "SP", "12345678")
insert into address(street, number, city, state, zip_code) values ("Rua Exemplo da Cunha", "777", "Rondonópolis", "MT", "12345678")

insert into branch(max_columns, max_rows, name, address_id) values (10, 10, "Unidade Brasília", 1)
insert into branch(max_columns, max_rows, name, address_id) values (10, 10, "Unidade SP", 2)
insert into branch(max_columns, max_rows, name, address_id) values (10, 10, "Unidade MT", 3)

insert into measurement_unit (description,symbol) values ("kilogram", "kg")
insert into measurement_unit (description,symbol) values  ("liters", "l")
insert into measurement_unit (description,symbol) values ("unit", "un")

insert into category (name) values ("food")
insert into category (name) values ("beverages")
insert into category (name) values ("clothes")

insert into sku (name, category_id, measurement_unit_id) values ("corn", 1, 1)
insert into sku (name, category_id, measurement_unit_id) values ("rice", 1, 1)
insert into sku (name, category_id, measurement_unit_id) values ("beans", 1, 1)
insert into sku (name, category_id, measurement_unit_id) values ("coke", 2, 3)
insert into sku (name, category_id, measurement_unit_id) values ("fanta", 2, 3)
insert into sku (name, category_id, measurement_unit_id) values ("pepsi", 2, 3)

insert into client (cnpj, email, name, address_id, user_id) values ("0123456789ABCD", "joão@email.com", "João", 1, 1)
insert into client (cnpj, email, name, address_id, user_id) values ("0123456789ABCD", "maria@email.com", "Maria", 1, 1)

insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "A", 1, null, 10, 1, 1)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "B", 1, null, 10, 1, 2)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "C", 1, null, 10, 1, 3)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "D", 1, null, 10, 1, 1)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "E", 1, null, 10, 2, 2)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "F", 1, null, 10, 2, 3)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "G", 1, null, 10, 2, 1)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "H", 1, null, 10, 2, 2)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "I", 1, null, 10, 2, 3)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (1, "J", 1, null, 10, 2, 1)

insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "A", 2, null, 10, 1, 1)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "B", 2, null, 10, 1, 2)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "C", 2, null, 10, 1, 3)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "D", 2, null, 10, 1, 1)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "E", 2, null, 10, 2, 2)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "F", 2, null, 10, 2, 3)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "G", 2, null, 10, 2, 1)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "H", 2, null, 10, 2, 2)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "I", 2, null, 10, 2, 3)
insert into warehouse_slot(branch_id, aisle, bay, arrival_date, quantity, client_id, sku_id) values (2, "J", 2, null, 10, 2, 1)
