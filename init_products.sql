CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE orders(
	id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    order_date DATETIME NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

DROP TABLE orders;

INSERT INTO products (name, price, stock) VALUES
('사과', 1000, 50),
('바나나', 800, 30),
('오렌지', 1200, 20),
('포도', 2500, 15),
('딸기', 3000, 10),
('당근', 700, 40),
('배추', 1200, 25),
('토마토', 1000, 18),
('양파', 900, 12),
('피망', 1500, 8),
('닭고기', 5000, 20),
('소고기', 15000, 10),
('돼지고기', 12000, 15),
('오리 고기', 18000, 5),
('양고기', 20000, 3),
('생선', 7000, 7);

INSERT INTO orders (product_id, quantity, total, order_date) VALUES (1, 1, 1000, '2024-11-22 19:41:00');
