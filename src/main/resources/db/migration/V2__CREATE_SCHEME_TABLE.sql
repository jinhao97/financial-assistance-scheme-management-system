-- Create sequence if it doesn't already exist
CREATE SEQUENCE IF NOT EXISTS public.scheme_seq
    START WITH 1
    INCREMENT BY 1;

-- Create the table if it doesn't already exist
CREATE TABLE IF NOT EXISTS public.t_schemes
(
    id                  BIGINT DEFAULT nextval('public.scheme_seq') NOT NULL
    CONSTRAINT scheme_pk PRIMARY KEY,
    scheme_name         VARCHAR(255),
    description         TEXT,
    display_name        TEXT,
    start_date          DATE NOT NULL,
    end_date            DATE NOT NULL,
    is_deleted          BOOLEAN NOT NULL,
    created_by          VARCHAR(255) NOT NULL,
    created_date        TIMESTAMP NOT NULL,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_date  TIMESTAMP NOT NULL,
    version             INTEGER NOT NULL
    );
