BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$10$Y5W4EEPKzM74ecV5elzFveaYt93aESEY2Q9TatTAB8IhJh7WjJUoe','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('demo_employee','$2a$10$RB5RHukIyOkETKdr9TNIjOsB5C6Yyet4stE9Aoo1Z1MytvTnjntiq','ROLE_DEMO_EMPLOYEE');
INSERT INTO users (user_id, username, password_hash, role) VALUES (-1,'Guest','$2a$10$hbY/I/JxFCgxME3aGnurGOFaoQPdy7giSzchdfKWVD.X2UFVXzGpC','ROLE_GUEST');

INSERT INTO user_data (user_id) VALUES (1);
INSERT INTO user_data (user_id, name, email, address, phone) VALUES (2, 'Employee', 'demo_employee@example.com', '123 Somewhere Dr.|||Columbus|||Ohio|||12345', '1234567890');
INSERT INTO user_data (user_id) VALUES (-1);

INSERT INTO toppings (name, available, price) VALUES ('Mozzarella Cheese', true, 0.50); -- 1 --
INSERT INTO toppings (name, available, price) VALUES ('Pepperoni', true, 0.50); -- 2 --
INSERT INTO toppings (name, available, price) VALUES ('Ham', true, 0.75); -- 3 --
INSERT INTO toppings (name, available, price) VALUES ('Pineapple', true, 0.50); -- 4 --
INSERT INTO toppings (name, available, price) VALUES ('Bacon', true, 1.25); -- 5 --
INSERT INTO toppings (name, available, price) VALUES ('Steak', true, 1.25); -- 6 --
INSERT INTO toppings (name, available, price) VALUES ('Chicken', true, 1.25); -- 7 --
INSERT INTO toppings (name, available, price) VALUES ('Onions', true, 0.50); -- 8 --
INSERT INTO toppings (name, available, price) VALUES ('Mushrooms', true, 0.50); -- 9 --
INSERT INTO toppings (name, available, price) VALUES ('Olives', true, 0.50); -- 10 --
INSERT INTO toppings (name, available, price) VALUES ('Green Peppers', true, 0.50); -- 11 --
INSERT INTO toppings (name, available, price) VALUES ('Spinach', true, 0.50); -- 12 --
INSERT INTO toppings (name, available, price) VALUES ('Feta Cheese', true, 0.75); -- 13 --
INSERT INTO toppings (name, available, price) VALUES ('Parmesan Cheese', true, 0.50); -- 14 --
INSERT INTO toppings (name, available, price) VALUES ('Tomatoes', true, 0.50); -- 15 --
INSERT INTO toppings (name, available, price) VALUES ('Banana Peppers', true, 0.50); -- 16 --
INSERT INTO toppings (name, available, price) VALUES ('Jalapeno Peppers', true, 0.50); -- 17 --
INSERT INTO toppings (name, available, price) VALUES ('Artichokes', true, 0.75); -- 18 --
INSERT INTO toppings (name, available, price) VALUES ('Kale', true, 0.75); -- 19 --
INSERT INTO toppings (name, available, price) VALUES ('Red Pepper', true, 0.50); -- 20 --
INSERT INTO toppings (name, available, price) VALUES ('Honey Drizzle', true, 0.50); -- 21 --
INSERT INTO toppings (name, available, price) VALUES ('Kalamata Olives', true, 1.00); -- 22 --
INSERT INTO toppings (name, available, price) VALUES ('Basil', true, 0.75); -- 23 --
INSERT INTO toppings (name, available, price) VALUES ('Olive Oil', true, 0.50); -- 24 --
INSERT INTO toppings (name, available, price) VALUES ('Sausage', true, 0.75); -- 25 --
INSERT INTO toppings (name, available, price) VALUES ('Ground Beef', true, 1.00); -- 26 --
INSERT INTO toppings (name, available, price) VALUES ('Garlic', true, 0.50); -- 27 --

INSERT INTO sauces (name, available, price) VALUES ('Marinara', true, 0.50); -- 1 --
INSERT INTO sauces (name, available, price) VALUES ('Alfredo', true, 1.00); -- 2 --
INSERT INTO sauces (name, available, price) VALUES ('BBQ', true, 1.00); -- 3 --
INSERT INTO sauces (name, available, price) VALUES ('Buffalo', true, 1.00); -- 4 --
INSERT INTO sauces (name, available, price) VALUES ('Garlic Butter', true, 0.50); -- 5 --
INSERT INTO sauces (name, available, price) VALUES ('Pesto', true, 1.00); -- 6 --

INSERT INTO crusts (name, available, price) VALUES ('Hand-Tossed', true, 0.50); -- 1 --
INSERT INTO crusts (name, available, price) VALUES ('Cheesy', true, 1.50); -- 2 --
INSERT INTO crusts (name, available, price) VALUES ('Cauliflower', true, 1.50); -- 3 --
INSERT INTO crusts (name, available, price) VALUES ('Pan', true, 1.50); -- 4 --
INSERT INTO crusts (name, available, price) VALUES ('Flatbread', true, 2.00); -- 5 --
INSERT INTO crusts (name, available, price) VALUES ('Thin', true, 2.00); -- 6 --

INSERT INTO sizes (name, available, price) VALUES ('Extra Large', true, 20.00); -- 1 --
INSERT INTO sizes (name, available, price) VALUES ('Large', true, 14.00); -- 2 --
INSERT INTO sizes (name, available, price) VALUES ('Medium', true, 8.00); -- 3 --
INSERT INTO sizes (name, available, price) VALUES ('Small', true, 5.00); -- 4 --






INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,1,1); -- Pepperoni Pizza --
INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,2,1); -- Classic Cheese Pizza --
INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,1,5); -- Hawaiian Luau --
INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,1,5); -- Veggie Supreme --
INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,6,5); -- Margaret's Margherita --
INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,1,6); -- Pesto Paradise --
INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,1,5); -- Mediterranean Sunshine --
INSERT INTO pizzas (size_id, crust_id, sauce_id) VALUES (2,1,1); -- Meat Lover's Dream --

INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1001,1); -- Pepperoni Pizza --
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1001,2);

INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1002,1); -- Classic Cheese Pizza --

INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1003,1); -- Hawaiian Luau --
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1003,3);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1003,4);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1003,5);

INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1004,1); -- Veggie Supreme Delight --
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1004,8);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1004,9);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1004,10);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1004,11);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1004,12);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1004,15);



INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1005, 15); -- Margaret's Margherita --
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1005, 23);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1005, 1);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1005, 24);

INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1006, 7); -- Pesto Paradise --
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1006, 15);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1006, 1);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1006, 27);

INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1007, 15); -- Mediterranean Sunshine --
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1007, 22);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1007, 18);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1007, 8);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1007, 13);

INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1008,2); -- Meat Lover's Dream --
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1008,26);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1008,5);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1008,25);
INSERT INTO pizzas_toppings (pizza_id, topping_id) VALUES (1008,1);






INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Pepperoni Pizza', true, 14.00, 1001, 'https://img.freepik.com/free-photo/delicious-neapolitan-meat-pizza-pizzeria-delicious-food_78826-2833.jpg', 'Get ready to savor the irresistible allure of our Pepperoni Pizza. This pizza combines the bold flavors of zesty tomato sauce, a generous layer of perfectly seasoned pepperoni, and a blanket of bubbling mozzarella cheese. With each bite, you''ll experience a harmonious blend of spice and savory goodness that''s been a pizza lover''s favorite for generations.');
INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Classic Cheese Pizza', true, 12.50, 1002, 'https://img.freepik.com/free-photo/pizza-four-cheese-table_140725-5391.jpg', 'Indulge in the timeless perfection of our Classic Cheese Pizza. Every bite is a symphony of flavors, with a generous layer of creamy mozzarella cheese melted to golden perfection on our handcrafted crust. It''s the essence of pizza, where simplicity meets irresistible satisfaction. This pizza is a tribute to the classics, and it''s sure to steal a pizza lover''s heart.');
INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Hawaiian Luau', true, 16.00, 1003, 'https://img.freepik.com/free-photo/pizza-wooden-background_1232-2681.jpg', 'Let your taste buds surf to the tropics with our Hawaiian Luau. Sweet and savory collide as ham, juicy pineapple chunks, and mozzarella cheese combine to create a tropical paradise on our pizza crust. Aloha, flavor!');
INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Veggie Supreme', true, 15.00, 1004, 'https://img.freepik.com/free-photo/meat-pizza-bell-pepper-basil-arugula-tomato-cheese-onion-top-view_141793-2772.jpg', 'Vegetarians rejoice! Our Veggie Supreme is a garden-fresh masterpiece. Colorful bell peppers, zesty red onions, juicy tomatoes, black olives, and spinach, all generously layered with mozzarella cheese. A vegetarian delight that''s as vibrant as it is delicious.');

INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Margaret''s Margherita', true, 14.50, 1005, 'https://img.freepik.com/free-photo/freshly-baked-pizza-rustic-wooden-table-generated-by-ai_24640-81013.jpg', 'Experience pizza in its purest form with Margaret''s Margherita. Freshly sliced tomatoes, fragrant basil leaves, and creamy mozzarella cheese harmonize on a perfectly thin crust, drizzled with extra-virgin olive oil. Close your eyes and savor the taste of Italy in every bite.');
INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Pesto Paradise', true, 17.00, 1006, 'https://img.freepik.com/premium-photo/nice-presentation-pizza_948127-260.jpg', 'Indulge in our Pesto Paradise, where pesto sauce takes center stage. Tender grilled chicken, sun-dried tomatoes, roasted garlic, and a drizzle of balsamic glaze join the party. A symphony of flavors you won''t want to miss.');
INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Mediterranean Sunshine', true, 16.50, 1007, 'https://img.freepik.com/free-photo/mixed-pizza-with-various-ingridients_140725-3790.jpg', 'Transport your taste buds to the Mediterranean with our Mediterranean Sunshine pizza. Freshly picked sun-dried tomatoes, Kalamata olives, artichoke hearts, red onions, and feta cheese, all kissed by a sprinkle of oregano. It''s a sunny escape in every bite.');
INSERT INTO menu_items (name, available, price, pizza_id, image_url, description) VALUES ('Meat Lover''s Dream', true, 18.50, 1008, 'https://img.freepik.com/free-photo/freshly-baked-pizza-with-melted-mozzarella-cheese-generated-by-ai_188544-22691.jpg', 'Calling all carnivores! Our Meat Lover''s Dream is a carnivorous delight. Savor the richness of pepperoni, Italian sausage, bacon, and ground beef, generously smothered in a blanket of melted cheese. It''s meaty heaven on a pizza.');

INSERT INTO menu_items (name, available, price) VALUES ('Breadsticks', true, 5.00);






COMMIT TRANSACTION;
