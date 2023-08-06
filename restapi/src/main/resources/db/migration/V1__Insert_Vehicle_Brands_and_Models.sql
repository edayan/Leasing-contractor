CREATE TABLE vehicle_brands (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                brand_name VARCHAR(100) NOT NULL
);

-- Insert sample data into the vehicle_brands table
INSERT INTO vehicle_brands (brand_name) VALUES
                                            ('Toyota'),
                                            ('Honda'),
                                            ('Ford'),
                                            ('Chevrolet');

-- Create the vehicle_models table
CREATE TABLE vehicle_models (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                model_name VARCHAR(100) NOT NULL,
                                brand_id INT NOT NULL,
                                FOREIGN KEY (brand_id) REFERENCES vehicle_brands (id)
);

-- Insert sample data into the vehicle_models table
INSERT INTO vehicle_models (model_name, brand_id) VALUES
                                                      ('Corolla', 1),
                                                      ('Civic', 2),
                                                      ('F-150', 3),
                                                      ('Silverado', 4),
                                                      ('Camry', 1);