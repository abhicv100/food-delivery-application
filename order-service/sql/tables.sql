-- Create the public schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS public;

-- Enum for OrderStatus
CREATE TYPE public.order_status AS ENUM (
    'PLACED',
    'ACCEPTED',
    'PREPARING',
    'READY_FOR_PICKUP',
    'OUT_FOR_DELIVERY',
    'DELIVERED',
    'REJECTED',
    'CANCELLED'
);

-- Create Order table
CREATE TABLE public."order" (
    order_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
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
    kilometers INT NOT NULL
);

-- Create OrderDetails table
CREATE TABLE public.orderdetails (
    order_details_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    order_month INT,
    order_year INT,
    order_id BIGINT NOT NULL,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id) 
        REFERENCES public."order" (order_id) 
        ON DELETE CASCADE
);