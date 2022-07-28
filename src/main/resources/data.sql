INSERT INTO person (id_person, name, gender, age, identification, address, phone) VALUES (1, 'Carlos Maldonado', 'Masculino', 23, '1726037722', 'Quito', '1234567890');

INSERT INTO client (client_id, password, status) VALUES (1, '12345', true);

INSERT INTO account (id_account, account_number, account_type, initial_balance, account_status, client_id) VALUES (1, '98776', 'Ahorros', 100.50, true, 1);

INSERT INTO movement (id_movement, create_time, movement_type, movement_initial_balance, movement_value, balance, id_account) VALUES (1, '2022-11-01', 'Deposito', 100.50, 50.25, 45.60, 1);
INSERT INTO movement (id_movement, create_time, movement_type, movement_initial_balance, movement_value, balance, id_account) VALUES (2, '2022-11-01', 'Retiro', 55.50, -50.25, 80.60, 1);