CREATE SEQUENCE IF NOT EXISTS public.administrator_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE public.t_administrator
(
    id                  BIGINT DEFAULT nextval('administrator_seq') NOT NULL
    CONSTRAINT administrator_pk PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email_name VARCHAR(255),
    password   VARCHAR(255)
);

CREATE TABLE public.user_roles
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES public.t_administrator (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES public.t_role (id) ON DELETE CASCADE
);