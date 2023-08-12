CREATE TABLE leasing_contract (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  monthly_rate DECIMAL(10, 2) NOT NULL,
                                  customer_id INT,
                                  vehicle_id INT,
                                  FOREIGN KEY (customer_id) REFERENCES customer(id),
                                  FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);
