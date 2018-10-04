INSERT INTO user (id, first_name, last_name, email_id, password) 
VALUES  (1, 'demo', 'demo', 'demo@gmail.com', 'demo')
ON DUPLICATE KEY UPDATE first_name='demo',last_name='demo',email_id='demo@gmail.com',password='demo';
 
INSERT INTO category (id, category_name) 
VALUES  (1, 'Electricity')
ON DUPLICATE KEY UPDATE category_name='Electricity';

INSERT INTO category (id, category_name) 
VALUES  (2, 'Phone')
ON DUPLICATE KEY UPDATE category_name='Phone';

INSERT INTO category (id, category_name) 
VALUES  (3, 'Internet')
ON DUPLICATE KEY UPDATE category_name='Internet';

INSERT INTO category (id, category_name) 
VALUES  (4, 'Grocery')
ON DUPLICATE KEY UPDATE category_name='Grocery';

INSERT INTO category (id, category_name) 
VALUES  (5, 'Food')
ON DUPLICATE KEY UPDATE category_name='Food';

INSERT INTO expense (id, user_id, title, category_id, date, amount, description) 
VALUES  (1, 1, 'demo','1', '2018-09-12', 500, 'none')
ON DUPLICATE KEY UPDATE user_id = 1, title = 'demo', category_id = 1, date = '2018-09-12',
amount = 500, description = 'none' ;
