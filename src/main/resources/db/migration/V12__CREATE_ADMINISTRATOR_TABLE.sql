CREATE SEQUENCE IF NOT EXISTS public.administrator_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE public.t_administrator
(
    id                  BIGINT DEFAULT nextval('public.administrator_seq') NOT NULL,
    uin                 VARCHAR(255),
    password            VARCHAR(255),
    is_deleted          BOOLEAN NOT NULL,
    created_by          VARCHAR(255) NOT NULL,
    created_date        TIMESTAMP NOT NULL,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_date  TIMESTAMP NOT NULL,
    version             INTEGER NOT NULL,
    CONSTRAINT administrator_pk PRIMARY KEY (id)
);
