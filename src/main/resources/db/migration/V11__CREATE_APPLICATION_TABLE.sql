CREATE SEQUENCE IF NOT EXISTS public.applications_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS public.t_applications
(
    id                 bigint      DEFAULT nextval('public.applications_seq') NOT NULL CONSTRAINT applications_pk PRIMARY KEY,
    applicant_id bigint NOT NULL
    CONSTRAINT applicant_fk REFERENCES public.t_applicant (id) ON DELETE CASCADE,
    scheme_id           bigint NOT NULL
    CONSTRAINT application_scheme_fk REFERENCES public.t_schemes (id) ON DELETE CASCADE,
    status   VARCHAR(255),
    submission_date_time TIMESTAMP,
    is_deleted         boolean      NOT NULL,
    created_by         varchar(255) NOT NULL,
    created_date       timestamp    NOT NULL,
    last_modified_by   varchar(255) NOT NULL,
    last_modified_date timestamp    NOT NULL,
    version            integer      NOT NULL
    );
