CREATE SEQUENCE IF NOT EXISTS public.role_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE public.t_role
(
    id                  BIGINT DEFAULT nextval('role_seq') NOT NULL
    CONSTRAINT role_pk PRIMARY KEY,
    role_name VARCHAR(255)
);
