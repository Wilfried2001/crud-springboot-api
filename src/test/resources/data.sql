-- SQL script to create the 'persons' table and insert sample data
CREATE TABLE personne
(
    id  INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    city VARCHAR(25) NOT NULL,
    phone_number VARCHAR(25) NOT NULL
);
-- Insert sample data into the 'persons' table
INSERT INTO personne (nom, city, phone_number)
VALUES ('John Doe', 'New York', '123-456-7890'),
         ('Jane Smith', 'Los Angeles', '987-654-3210'),
         ('Alice Johnson', 'Chicago', '555-123-4567'),
         ('Bob Brown', 'Houston', '444-987-6543');