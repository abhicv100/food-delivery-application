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
    ('renuka', '1234', 'Renuka Sharma', '67 Nehru Road, 2nd Cross, Koramangala, Bengaluru, Karnataka 560095, India', '+91 44444 55555', 'renuka@gmail.com', NULL, 1),
    ('vijay', '1234', 'Vijay Kumar Reddy', '88 Race Course Road, 1st Floor, Sector 5, Coimbatore, Tamil Nadu 641018, India', '+91 55555 66666', 'vijay@gmail.com', NULL, 2),
    ('tanvi', '1234', 'Tanvi Mishra', '45 Jubilee Hills, Block B, Hyderabad, Telangana 500033, India', '+91 66666 77777', 'tanvi@gmail.com', NULL, 1),
    ('arjunr', '1234', 'Arjun Ramesh', '10 Marine Drive, Churchgate, Mumbai, Maharashtra 400020, India', '+91 77777 88888', 'arjunr@gmail.com', 1, 3),
    ('kanika', '1234', 'Kanika Khanna', '22 Cuffe Parade, Flat 15B, Colaba, Mumbai, Maharashtra 400005, India', '+91 88888 99999', 'kanika@gmail.com', NULL, 2),
    ('ritesh', '1234', 'Ritesh Agarwal', '109 Salt Lake, Sector 3, Bidhannagar, Kolkata, West Bengal 700091, India', '+91 99999 00000', 'ritesh@gmail.com', NULL, 1),
    ('meghad', '1234', 'Megha Deshpande', '5 Gandhi Chowk, Laxmi Nagar, Nagpur, Maharashtra 440010, India', '+91 10101 10101', 'meghad@gmail.com', 1, 3),
    ('anupama', '1234', 'Anupama Nair', '56 Lutyens Bungalow Zone, New Delhi, Delhi 110011, India', '+91 20202 20202', 'anupama@gmail.com', NULL, 1),
    ('shivraj', '1234', 'Shivraj Singh', '34 Civil Lines, Near MG Road, Jaipur, Rajasthan 302006, India', '+91 30303 30303', 'shivraj@gmail.com', NULL, 2),
    ('pradeepk', '1234', 'Pradeep Kumar', '78 Beach Road, Puducherry, Puducherry 605001, India', '+91 40404 40404', 'pradeepk@gmail.com', NULL, 1),
    ('vimalj', '1234', 'Vimal Joshi', '90 Ring Road, Near Rajpath Club, Ahmedabad, Gujarat 380015, India', '+91 50505 50505', 'vimalj@gmail.com', 1, 3),
    ('keerthiv', '1234', 'Keerthi Varma', '40 MG Marg, Near Mallital, Nainital, Uttarakhand 263001, India', '+91 60606 60606', 'keerthiv@gmail.com', NULL, 1),
    ('neha_s', '1234', 'Neha Singh', '77 Sector 62, Noida, Uttar Pradesh 201301, India', '+91 70707 70707', 'neha_s@gmail.com', NULL, 2),
    ('aradhana', '1234', 'Aradhana Mukherjee', '32 Park Street, Block A, Kolkata, West Bengal 700016, India', '+91 80808 80808', 'aradhana@gmail.com', NULL, 1),
    ('saurabhb', '1234', 'Saurabh Bhatia', '120 Hauz Khas Village, South Delhi, New Delhi, Delhi 110016, India', '+91 90909 90909', 'saurabhb@gmail.com', NULL, 2),
    ('madhurima', '1234', 'Madhurima Iyer', '67 Gandhi Road, Near Anna Nagar, Chennai, Tamil Nadu 600040, India', '+91 10101 11111', 'madhurima@gmail.com', 1, 3);

