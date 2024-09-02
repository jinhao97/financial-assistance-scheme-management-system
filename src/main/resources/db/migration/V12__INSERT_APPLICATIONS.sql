INSERT INTO public.t_applications
(applicant_id, scheme_id, status, submission_date_time, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES((SELECT id FROM public.t_applicant ta where ta.uin = 'S2545687D'), (SELECT id FROM public.t_schemes ts where ts.scheme_name = 'REA_SCH'), 'PENDING', now(), false, 'SYSTEM', now(), 'SYSTEM', now(), 1);