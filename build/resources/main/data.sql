INSERT INTO person (id_person, name, gender, age, identification, address, phone) VALUES (1, 'Carlos Maldonado', 'Masculino', 23, '1726037722', 'Quito', '1234567890');

INSERT INTO client (id_person, password, state) VALUES (1, '12345', true);

INSERT INTO account (id_account, account_number, account_type, initial_balance, account_state) VALUES (1, '98776', 'Ahorros', 100.50, true);

INSERT INTO movement (id_movement, create_time, movement_type, movement_value, balance) VALUES (1, '2022-11-01', 'Credito', 50.25, 45.60);