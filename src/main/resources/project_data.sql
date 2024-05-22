use projects;

--		 --
--  project data --
--               --



-- Insert into the category table
INSERT INTO category (category_name) VALUES ('House');
INSERT INTO category (category_name) VALUES ('Software Development');
INSERT INTO category (category_name) VALUES ('Rideshare');
INSERT INTO category (category_name) VALUES ('Study');
INSERT INTO category (category_name) VALUES ('Car');
INSERT INTO category (category_name) VALUES ('Social Life');
INSERT INTO category (category_name) VALUES ('Education');



-- Insert into the step table
INSERT INTO step (project_id, step_text, step_order) VALUES (1, 'Remove old sink, and use glue to tie it against the wall.', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (2, 'Move sofa to the left, and put coffee table in the middle.', 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (6, 'Remove bathroom trash bag and replace it with a new one.', 3);
INSERT INTO step (project_id, step_text, step_order) VALUES (7, 'Grab a Babrbecue grille.', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (7, 'Put your meat on the grille, make sure fire is good.', 2);

INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Download the Uber driver app, if you are a driver, and create an account', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Download the Uber app, if you are a rider, and create an account', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Create a username and a password, and enter your email and phone number to receive a temporary code and newsletter', 2);

INSERT INTO step (project_id, step_text, step_order) VALUES (13, 'You could buy a coding book and self-taught learn yourself how to code', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (13, 'You could earn a 4 year Bachelor degree in Computer Science or Infromation Technology or Engineering, where they will teach you how to code', 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (12, 'Learn Java, JavaScript, Python, C#, Swift, Goland, SQL, Html,Css', 1);



-- Insert into the material table

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'new sink', 3, 23.89);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'glue', 3, 3.89);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'harmer', 1, 2.99);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'a car', 2, 2000.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'Insurance', 1, 200.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'driver license', 1, null);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (12, 'computer and Internet', 5, 400.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (12, 'table', 2, 100.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'computer', 4, 300);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'chair', 1, 500);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'grille', 2, 500.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'characol', 5, 50.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'barbecue sauce', 1, 2.99);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'onion and tomatoes', 6, 10.00);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (5, 'dish washer', 1, 199.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (5, 'fridge', 2, 399.40);

-- Insert into the join  project_category table
INSERT INTO project_category (project_id, category_id) VALUES (1, 1);
INSERT INTO project_category (project_id, category_id) VALUES (2, 1);
INSERT INTO project_category (project_id, category_id) VALUES (3, 1);
INSERT INTO project_category (project_id, category_id) VALUES (5, 1);
INSERT INTO project_category (project_id, category_id) VALUES (6, 1);
INSERT INTO project_category (project_id, category_id) VALUES (10, 1);

INSERT INTO project_category (project_id, category_id) VALUES (7, 6);
INSERT INTO project_category (project_id, category_id) VALUES (12, 2);
INSERT INTO project_category (project_id, category_id) VALUES (13, 2);


INSERT INTO project_category (project_id, category_id) VALUES (11, 7);
INSERT INTO project_category (project_id, category_id) VALUES (8, 5);
INSERT INTO project_category (project_id, category_id) VALUES (9, 3);