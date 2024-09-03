INSERT INTO public.t_benefit_attributes
(benefit_name, benefit_type, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('SkillsFuture Credits', 'INT', false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_benefit_attributes_values
(benefit_value, benefit_attributes_id, scheme_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('500.00', (SELECT id FROM public.t_benefit_attributes tba where tba.benefit_name = 'SkillsFuture Credits'), (SELECT id FROM public.t_schemes ts where ts.scheme_name = 'REA_SCH'), false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_benefit_attributes
(benefit_name, benefit_type, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('School Meal Voucher', 'INT', false, 'SYSTEM', now(), 'SYSTEM', now(), 1);

INSERT INTO public.t_benefit_attributes_values
(benefit_value, benefit_attributes_id, scheme_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('200.00', (SELECT id FROM public.t_benefit_attributes tba where tba.benefit_name = 'School Meal Voucher'), (SELECT id FROM public.t_schemes ts where ts.scheme_name = 'REA_SCH'), false, 'SYSTEM', now(), 'SYSTEM', now(), 1);
