-- create delivery details table
CREATE TABLE public.delivery_details (
    order_id integer,
    delivery_person_id integer,
    delivered BOOLEAN DEFAULT FALSE,
    delivery_message VARCHAR(255) DEFAULT 'Not picked up',
    delivery_accepted BOOLEAN DEFAULT FALSE,
    CONSTRAINT delivery_person_id_fk FOREIGN KEY (delivery_person_id) REFERENCES public.user (id) 
);

-- insert sample details into delivery details table
INSERT INTO public.delivery_details (order_id,delivery_person_id,delivered,delivery_accepted) VALUES 
    ('1','3',false,'false');
INSERT INTO public.delivery_details (order_id,delivery_person_id,delivered,delivery_accepted) VALUES 
    ('2','3',false,'false');