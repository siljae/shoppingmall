CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    max_stock INT NOT NULL
);

CREATE TABLE orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    order_date DATETIME NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
