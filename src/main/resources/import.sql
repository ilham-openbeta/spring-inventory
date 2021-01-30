INSERT INTO unit (is_deleted, created_date, code, description) VALUES (0, '2021-01-16', 'kg', 'Kilogram');
INSERT INTO unit (is_deleted, created_date, code, description) VALUES (0, '2021-01-16', 'pack', 'Package');
INSERT INTO unit (is_deleted, created_date, code, description) VALUES (0, '2021-01-16', 'rcg', 'Renceng');

INSERT INTO item (is_deleted, created_date, name, price, unit_id, image_url, original_filename) VALUES (0, '2021-01-16', 'Beng beng', 20000, 2, '/items/image/c45121ff-f43d-4830-af9d-6c2f80b1f0db.jpg', 'bengbeng.jpg');
INSERT INTO item (is_deleted, created_date, name, price, unit_id, image_url, original_filename) VALUES (0, '2021-01-16', 'Indomie', 100000, 2, '/items/image/b9d4a5b4-c588-4f3c-8604-af6f33553836.png', 'indomie.png');
INSERT INTO item (is_deleted, created_date, name, price, unit_id, image_url, original_filename) VALUES (0, '2021-01-16', 'Beras', 10000, 1, '/items/image/87f24b8c-5493-434e-abd1-06304ce600c3.jpg', 'beras.jpg');
INSERT INTO item (is_deleted, created_date, name, price, unit_id, image_url, original_filename) VALUES (0, '2021-01-16', 'Jeruk', 10000, 1, '/items/image/b05caf46-5be4-4ab2-8b5b-05d6e49b5da8.jpg', 'jeruk.jpg');
INSERT INTO item (is_deleted, created_date, name, price, unit_id, image_url, original_filename) VALUES (0, '2021-01-16', 'Nutrisari', 10000, 3, '/items/image/e7e7d0af-7f49-4270-84ac-752cb5837949.jpg', 'nutrisari.jpg');

INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,1,10, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,2,5, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,3,100, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,4,20, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,5,20, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,5,2, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,4,2, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,3,2, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,2,2, '2021-01-16');
INSERT INTO stock (is_deleted, item_id, quantity, created_date) VALUES (0,1,2, '2021-01-16');