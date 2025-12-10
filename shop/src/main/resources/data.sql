-- Sample data for shop tables

INSERT INTO users (full_name, email, password, created_at, updated_at) VALUES
    ('Admin User', 'admin@example.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO5lgUVJa9lhAe/de7q5por5Rm2I1J8m', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Jane Customer', 'jane@example.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO5lgUVJa9lhAe/de7q5por5Rm2I1J8m', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_roles (user_id, role) VALUES
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO categories (name, description) VALUES
    ('Books', 'Fiction and non-fiction titles'),
    ('Electronics', 'Phones, laptops and accessories'),
    ('Clothing', 'Everyday apparel for all seasons');

INSERT INTO products (category_id, name, description, price, stock, image_url) VALUES
    (1, 'Book A', 'An engaging novel to enjoy.', 10.00, 100, NULL),
    (1, 'Book B', 'A sequel with even more adventure.', 12.00, 80, NULL),
    (2, 'Phone', 'Latest smartphone with great camera.', 500.00, 50, NULL),
    (2, 'Laptop', 'Lightweight laptop for work and play.', 1000.00, 30, NULL),
    (3, 'Shirt', 'Comfortable cotton shirt.', 20.00, 200, NULL),
    (3, 'Jeans', 'Durable denim jeans.', 40.00, 150, NULL);

INSERT INTO cart_items (user_id, product_id, quantity) VALUES
    (2, 1, 1),
    (2, 3, 2);

INSERT INTO orders (user_id, total, status, created_at) VALUES
    (2, 524.00, 'PAID', CURRENT_TIMESTAMP);

INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
    (1, 3, 1, 500.00),
    (1, 5, 1, 20.00),
    (1, 1, 1, 4.00);
