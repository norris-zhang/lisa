CREATE TABLE public.lisa_class
(
    clas_id bigserial NOT NULL,
    clas_name character varying(50) NOT NULL,
    clas_description character varying(200) NOT NULL,
    clas_day_of_week character(3) NOT NULL,
    clas_duration integer NOT NULL,
    PRIMARY KEY (user_id)
);

ALTER TABLE IF EXISTS public.lisa_class
    OWNER to postgres;



CREATE TABLE public.lisa_user
(
    user_id bigserial NOT NULL,
    user_login_id character varying(200) NOT NULL,
    user_password character varying(128) NOT NULL,
    user_role character varying(50) NOT NULL,
    PRIMARY KEY (user_id)
);

ALTER TABLE IF EXISTS public.lisa_user
    OWNER to postgres;

