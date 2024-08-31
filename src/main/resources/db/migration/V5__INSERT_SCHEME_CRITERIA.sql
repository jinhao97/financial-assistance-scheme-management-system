INSERT INTO public.t_criteria_attributes
(criteria_name, criteria_type, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('employment_status', 'STRING', false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_criteria_attributes
(criteria_name, criteria_type, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('has_children', 'BOOLEAN', false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_criteria_attributes
(criteria_name, criteria_type, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('school_level', 'STRING', false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_criteria_attributes_values
(criteria_value, criteria_attributes_id, scheme_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('unemployed', (SELECT id FROM public.t_criteria_attributes tca where tca.criteria_name = 'employment_status'), (SELECT id FROM public.t_schemes ts where ts.scheme_name = 'REA_SCH'), false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_criteria_attributes_values
(criteria_value, criteria_attributes_id, scheme_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('true', (SELECT id FROM public.t_criteria_attributes tca where tca.criteria_name = 'has_children'), (SELECT id FROM public.t_schemes ts where ts.scheme_name = 'REA_SCH'), false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_criteria_attributes_values
(criteria_value, criteria_attributes_id, scheme_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('primary', (SELECT id FROM public.t_criteria_attributes tca where tca.criteria_name = 'school_level'), (SELECT id FROM public.t_schemes ts where ts.scheme_name = 'REA_SCH'), false, 'SYSTEM', now(), 'SYSTEM', now(), 1);