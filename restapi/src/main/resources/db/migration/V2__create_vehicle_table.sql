CREATE TABLE vehicle (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 vehicle_model_id INT NOT NULL,
                                 year INT NOT NULL,
                                 vin VARCHAR(100) NOT NULL,
                                 price DECIMAL(10, 2) NOT NULL,
                                 FOREIGN KEY (vehicle_model_id) REFERENCES vehicle_models (id)
);