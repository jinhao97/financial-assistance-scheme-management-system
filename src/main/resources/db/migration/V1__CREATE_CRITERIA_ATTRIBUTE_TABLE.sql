-- Create sequence if it doesn't already exist
CREATE SEQUENCE IF NOT EXISTS public.criteria_attributes_seq
    START WITH 1
    INCREMENT BY 1;

-- Create the table if it doesn't already exist
CREATE TABLE IF NOT EXISTS public.t_criteria_attributes
(
    id                  BIGINT DEFAULT nextval('public.criteria_attributes_seq') NOT NULL
    CONSTRAINT criteria_attributes_pk PRIMARY KEY,
    criteria_name       VARCHAR(255) NOT NULL,
    criteria_type       VARCHAR(255) NOT NULL,
    is_deleted          BOOLEAN NOT NULL,
    created_by          VARCHAR(255) NOT NULL,
    created_date        TIMESTAMP NOT NULL,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_date  TIMESTAMP NOT NULL,
    version             INTEGER NOT NULL
    );
