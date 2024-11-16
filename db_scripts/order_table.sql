-- Create the public schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS orderschema;

-- Enum for OrderStatus
CREATE TYPE orderschema.order_status AS ENUM (
    'PLACED',
    'ACCEPTED',
    'PREPARING',
    'READY_FOR_PICKUP',
    'OUT_FOR_DELIVERY',
    'DELIVERED',
    'REJECTED',
    'CANCELLED'
);

DROP SEQUENCE IF EXISTS orderschema.order_id_seq;

CREATE SEQUENCE orderschema.order_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE orderschema.order_id_seq OWNER TO postgres;

-- Create Order table
CREATE TABLE orderschema."order" (
    order_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    items TEXT NOT NULL,
    total_amt FLOAT NOT NULL,
    restaurant_disc_id VARCHAR(255),
    restaurant_disc_amt FLOAT,
    final_amt FLOAT NOT NULL,
    order_status VARCHAR NOT NULL,
    start_time TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_time TIMESTAMP NOT NULL DEFAULT NOW(),
    end_time TIMESTAMP,
    expected_time TIMESTAMP NOT NULL,
    address TEXT NOT NULL,
    kilometers INT NOT NULL,
    delivery_personnel_id INT
);


INSERT INTO orderschema."order" (user_id, restaurant_id, items, total_amt, restaurant_disc_id, restaurant_disc_amt, final_amt, order_status, start_time, modified_time, expected_time, address, kilometers, delivery_personnel_id)
VALUES 
(1, 1, '[{"id": 1, "name": "Margherita Pizza", "quantity": 3}, {"id": 2, "name": "Pasta Carbonara", "quantity": 7}, {"id": 3, "name": "Vegetable Spring Rolls", "quantity": 2}]', 35.99, 'DISC10', 3.60, 32.39, 'PLACED', '2024-03-05 12:30:00', '2024-03-05 12:45:00', '2024-03-05 13:15:00', 'Mumbai, Maharashtra, India', 5, null), 
(5, 1, '[{"id": 4, "name": "Chicken Tikka Masala", "quantity": 5}, {"id": 5, "name": "Beef Burger", "quantity": 6}]', 42.50, 'DISC15', 6.38, 36.12, 'ACCEPTED', '2024-06-21 17:10:00', '2024-06-21 17:30:00', '2024-06-21 18:10:00', 'Delhi, India', 8, null),
(12, 2, '[{"id": 11, "name": "Mango Sticky Rice", "quantity": 4}, {"id": 13, "name": "Tandoori Chicken", "quantity": 1}]', 29.75, NULL, NULL, 29.75, 'PREPARING', '2023-12-12 14:45:00', '2023-12-12 15:00:00', '2023-12-12 15:30:00', 'Pune, Maharashtra, India', 4, null),
(5, 2, '[{"id": 15, "name": "Pork Ramen", "quantity": 8}, {"id": 18, "name": "Coconut Curry", "quantity": 3}, {"id": 20, "name": "Vegetable Fried Rice", "quantity": 6}]', 48.90, 'DISC20', 9.78, 39.12, 'OUT_FOR_DELIVERY', '2024-02-17 13:20:00', '2024-02-17 13:35:00', '2024-02-17 14:15:00', 'Hyderabad, Telangana, India', 6, 11),
(7, 3, '[{"id": 23, "name": "Spicy Tuna Tartare", "quantity": 2}, {"id": 25, "name": "Ribeye Steak", "quantity": 5}]', 53.00, 'DISC5', 2.65, 50.35, 'PLACED', '2024-08-09 11:30:00', '2024-08-09 11:45:00', '2024-08-09 12:30:00', 'Kolkata, West Bengal, India', 7, null),
(16, 3, '[{"id": 26, "name": "Vegetable Samosas", "quantity": 9}, {"id": 28, "name": "Pesto Pasta", "quantity": 3}, {"id": 30, "name": "Lamb Gyro", "quantity": 7}]', 40.45, NULL, NULL, 40.45, 'READY_FOR_PICKUP', '2023-11-28 10:10:00', '2023-11-28 10:25:00', '2023-11-28 11:05:00', 'Chennai, Tamil Nadu, India', 10, null),
(10, 4, '[{"id": 34, "name": "Creamy Tomato Soup", "quantity": 6}, {"id": 36, "name": "Seafood Paella", "quantity": 4}]', 37.00, 'DISC10', 3.70, 33.30, 'DELIVERED', '2024-01-22 15:50:00', '2024-01-22 16:00:00', '2024-01-22 16:40:00', 'Jaipur, Rajasthan, India', 9, 11),
(1, 5, '[{"id": 41, "name": "Bruschetta", "quantity": 3}, {"id": 45, "name": "Mediterranean Bowl", "quantity": 2}]', 22.99, NULL, NULL, 22.99, 'PLACED', '2024-04-15 09:00:00', '2024-04-15 09:15:00', '2024-04-15 09:40:00', 'Mumbai, Maharashtra, India', 5, null),
(7, 6, '[{"id": 54, "name": "Baklava", "quantity": 4}, {"id": 56, "name": "Caprese Salad", "quantity": 6}, {"id": 59, "name": "Gnocchi", "quantity": 1}]', 45.20, 'DISC15', 6.78, 38.42, 'ACCEPTED', '2024-05-29 18:20:00', '2024-05-29 18:30:00', '2024-05-29 19:10:00', 'Bangalore, Karnataka, India', 6, null),
(12, 7, '[{"id": 67, "name": "Mushroom Risotto", "quantity": 7}, {"id": 70, "name": "Baked Ziti", "quantity": 8}]', 39.80, 'DISC10', 3.98, 35.82, 'PREPARING', '2024-09-10 12:50:00', '2024-09-10 13:00:00', '2024-09-10 13:40:00', 'Ahmedabad, Gujarat, India', 7, null);


DROP SEQUENCE IF EXISTS orderschema.orderdetails_id_seq;

CREATE SEQUENCE orderschema.orderdetails_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE orderschema.orderdetails_id_seq OWNER TO postgres;

-- Create OrderDetails table
CREATE TABLE orderschema.orderdetails (
    order_details_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    order_month INT,
    order_year INT,
    order_id INT NOT NULL,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id) 
        REFERENCES orderschema."order" (order_id) 
        ON DELETE CASCADE
);

INSERT INTO orderschema.orderdetails (user_id, restaurant_id, item_id, quantity, order_month, order_year, order_id)
VALUES 
(1, 1, 1, 3, 3, 2024, 1),
(1, 1, 2, 7, 3, 2024, 1),
(1, 1, 3, 2, 3, 2024, 1),

(5, 1, 4, 5, 6, 2024, 2),
(5, 1, 5, 6, 6, 2024, 2),

(12, 2, 11, 4, 12, 2023, 3),
(12, 2, 13, 1, 12, 2023, 3),

(5, 2, 15, 8, 2, 2024, 4),
(5, 2, 18, 3, 2, 2024, 4),
(5, 2, 20, 6, 2, 2024, 4),

(7, 3, 23, 2, 8, 2024, 5),
(7, 3, 25, 5, 8, 2024, 5),

(16, 3, 26, 9, 11, 2023, 6),
(16, 3, 28, 3, 11, 2023, 6),
(16, 3, 30, 7, 11, 2023, 6),

(10, 4, 34, 6, 1, 2024, 7),
(10, 4, 36, 4, 1, 2024, 7),

(1, 5, 41, 3, 4, 2024, 8),
(1, 5, 45, 2, 4, 2024, 8),

(7, 6, 54, 4, 5, 2024, 9),
(7, 6, 56, 6, 5, 2024, 9),
(7, 6, 59, 1, 5, 2024, 9),

(12, 7, 67, 7, 9, 2024, 10),
(12, 7, 70, 8, 9, 2024, 10);