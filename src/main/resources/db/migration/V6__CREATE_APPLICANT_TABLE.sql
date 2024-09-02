-- Create sequence if it doesn't already exist
CREATE SEQUENCE IF NOT EXISTS public.applicant_seq
    START WITH 1
    INCREMENT BY 1;

-- Create the table if it doesn't already exist
CREATE TABLE IF NOT EXISTS public.t_applicant
(
    id                  BIGINT DEFAULT nextval('public.applicant_seq') NOT NULL
    CONSTRAINT applicant_pk PRIMARY KEY,
    name                VARCHAR(255),
    sex                 VARCHAR(255),
    date_of_birth       DATE,
    uin                 VARCHAR(255),
    employment_status   VARCHAR(255),
    relationship        VARCHAR(255),
    household_id        BIGINT,
    is_deleted          BOOLEAN NOT NULL,
    created_by          VARCHAR(255) NOT NULL,
    created_date        TIMESTAMP NOT NULL,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_date  TIMESTAMP NOT NULL,
    version             INTEGER NOT NULL,

    -- Foreign key constraint for the self-referencing relationship
    CONSTRAINT fk_household
    FOREIGN KEY (household_id)
    REFERENCES public.t_applicant (id)
    ON DELETE CASCADE
    );
