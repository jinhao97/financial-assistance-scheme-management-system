CREATE SEQUENCE IF NOT EXISTS public.benefit_attributes_values_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS public.t_benefit_attributes_values
(
    id                 bigint      DEFAULT nextval('public.benefit_attributes_values_seq') NOT NULL CONSTRAINT benefit_attributes_values_pk PRIMARY KEY,
    benefit_value     text,
    benefit_attributes_id bigint NOT NULL
    CONSTRAINT benefit_attribute_fk REFERENCES public.t_benefit_attributes (id) ON DELETE CASCADE,
    scheme_id           bigint NOT NULL
    CONSTRAINT scheme_fk REFERENCES public.t_schemes (id) ON DELETE CASCADE,
    is_deleted         boolean      NOT NULL,
    created_by         varchar(255) NOT NULL,
    created_date       timestamp    NOT NULL,
    last_modified_by   varchar(255) NOT NULL,
    last_modified_date timestamp    NOT NULL,
    version            integer      NOT NULL
    );
