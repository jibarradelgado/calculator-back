INSERT INTO public.user_role(
	role, user_id, granted_date)
	VALUES ('ADMIN', 1, NOW());
INSERT INTO public.user_role(
	role, user_id, granted_date)
	VALUES ('GUEST', 2, NOW());