INSERT INTO public.t_applicant
("name", sex, date_of_birth, uin, employment_status, relationship, household_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('Suzy', 'FEMALE', '1980-05-01', 'S6336574B', 'UNEMPLOYED', NULL, NULL, false, 'SYSTEM', now(), 'SYSTEM', now(), 1);
INSERT INTO public.t_applicant
("name", sex, date_of_birth, uin, employment_status, relationship, household_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('Michelle', 'FEMALE', '1999-12-01', 'S9127893F', 'UNEMPLOYED', 'DAUGHTER', (SELECT id FROM public.t_applicant ta where ta.uin = 'S6336574B'), false, 'SYSTEM', now(), 'SYSTEM',now(), 1);
INSERT INTO public.t_applicant
("name", sex, date_of_birth, uin, employment_status, relationship, household_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('Ivan', 'MALE', '1992-08-01', 'S5094134E', 'UNEMPLOYED', 'SON', (SELECT id FROM public.t_applicant ta where ta.uin = 'S6336574B'), false, 'SYSTEM', now(), 'SYSTEM', now(), 1);
INSERT INTO public.t_applicant
("name", sex, date_of_birth, uin, employment_status, relationship, household_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('John', 'MALE', '1964-12-21', 'S2545687D', 'UNEMPLOYED', NULL, NULL, false, 'SYSTEM', now(),'SYSTEM', now(), 1);
INSERT INTO public.t_applicant
("name", sex, date_of_birth, uin, employment_status, relationship, household_id, is_deleted, created_by, created_date, last_modified_by, last_modified_date, "version")
VALUES('Jessie', 'FEMALE', '1960-10-21', 'S3849873H', 'EMPLOYED', NULL, NULL, false, 'SYSTEM', now(), 'SYSTEM', now(), 1);