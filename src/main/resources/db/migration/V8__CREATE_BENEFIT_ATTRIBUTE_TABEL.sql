-- Create sequence if it doesn't already exist
CREATE SEQUENCE IF NOT EXISTS public.benefit_attributes_seq
    START WITH 1
    INCREMENT BY 1;

-- Create the table if it doesn't already exist
CREATE TABLE IF NOT EXISTS public.t_benefit_attributes
(
    id                  BIGINT DEFAULT nextval('public.benefit_attributes_seq') NOT NULL
    CONSTRAINT benefit_attributes_pk PRIMARY KEY,
    benefit_name       VARCHAR(255) NOT NULL,
    benefit_type       VARCHAR(255) NOT NULL,
    is_deleted          BOOLEAN NOT NULL,
    created_by          VARCHAR(255) NOT NULL,
    created_date        TIMESTAMP NOT NULL,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_date  TIMESTAMP NOT NULL,
    version             INTEGER NOT NULL
    );
