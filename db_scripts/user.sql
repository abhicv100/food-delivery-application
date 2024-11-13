-- VEHICLE TYPE

DROP TABLE IF EXISTS public.vehicle_type CASCADE;

CREATE TABLE public.vehicle_type (
    id integer,
    name text,
    CONSTRAINT vehicle_type_id_pk PRIMARY KEY (id)
);

ALTER TABLE public.vehicle_type OWNER to postgres;  

INSERT INTO public.vehicle_type (id, name) VALUES 
    ('1', 'two-wheeler');

-- USER TABLE

DROP TABLE IF EXISTS public.user CASCADE;
DROP SEQUENCE IF EXISTS public.user_id_seq;

CREATE SEQUENCE public.user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE public.user (
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    username text,
    password text,
    full_name text,
    address text,
    phone text,
    email text,
    vehicle_type_id integer,
    role_id integer,
    CONSTRAINT user_id_pk PRIMARY KEY (id),
    CONSTRAINT vehicle_type_id_fk FOREIGN KEY (vehicle_type_id) REFERENCES public.vehicle_type (id),
);

ALTER TABLE public.user OWNER to postgres;  

INSERT INTO public.user (username, password, full_name, address, phone, email, vehicle_type_id, role_id) VALUES
    ('abhicv', '1234', 'Abhijith C V', '221B Baker Street, Block C, MG Road, Bengaluru, Karnataka 560001, India', '+91 11111 11111', 'abhicv@gmail.com', NULL, 1),
    ('akshara', '1234', 'Akshara Anilkumar', '45 Residency Road, 3rd Floor, Sector 17, Gurgaon, Haryana 122001, India', '+91 22222 22222', 'akshara@gmail.com', NULL, 2),
    ('saradhi', '1234', 'Kunda Ojass Tejas C Saradhi', '12 Park Avenue, Flat 4A, Andheri West, Mumbai, Maharashtra 400053, India', '+91 33333 44444', 'saradhi@gmail.com', 1, 3),
    ('admin', 'admin', 'Administrator', NULL, NULL, 'admin@gmail.com', NULL, 4);
