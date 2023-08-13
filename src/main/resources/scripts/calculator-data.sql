--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-08-12 16:11:40

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE calculator;
--
-- TOC entry 3359 (class 1262 OID 32836)
-- Name: calculator; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE calculator WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


\connect calculator

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 33019)
-- Name: app_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.app_user (
    balance integer NOT NULL,
    last_login timestamp(6) without time zone,
    user_id bigint NOT NULL,
    password character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    last_top_up_time timestamp(6) without time zone,
    CONSTRAINT app_user_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying])::text[])))
);


--
-- TOC entry 214 (class 1259 OID 33018)
-- Name: app_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.app_user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 214
-- Name: app_user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.app_user_user_id_seq OWNED BY public.app_user.user_id;


--
-- TOC entry 217 (class 1259 OID 33031)
-- Name: operation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.operation (
    cost integer NOT NULL,
    operation_id bigint NOT NULL,
    operation_type character varying(255) NOT NULL,
    CONSTRAINT operation_operation_type_check CHECK (((operation_type)::text = ANY ((ARRAY['ADDITION'::character varying, 'SUBTRACTION'::character varying, 'MULTIPLICATION'::character varying, 'DIVISION'::character varying, 'SQUARE_ROOT'::character varying, 'RANDOM_STRING'::character varying, 'TOP_UP'::character varying])::text[])))
);


--
-- TOC entry 216 (class 1259 OID 33030)
-- Name: operation_operation_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.operation_operation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 216
-- Name: operation_operation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.operation_operation_id_seq OWNED BY public.operation.operation_id;


--
-- TOC entry 219 (class 1259 OID 33039)
-- Name: record; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.record (
    balance_after_operation integer NOT NULL,
    balance_before_operation integer NOT NULL,
    deleted boolean NOT NULL,
    date_time timestamp(6) without time zone NOT NULL,
    operation_id bigint NOT NULL,
    record_id bigint NOT NULL,
    user_id bigint NOT NULL,
    operation_response character varying(255)
);


--
-- TOC entry 218 (class 1259 OID 33038)
-- Name: record_record_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.record_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 218
-- Name: record_record_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.record_record_id_seq OWNED BY public.record.record_id;


--
-- TOC entry 220 (class 1259 OID 33055)
-- Name: user_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_role (
    role character varying(20) NOT NULL,
    user_id bigint NOT NULL,
    granted_date timestamp(6) without time zone NOT NULL
);


--
-- TOC entry 3187 (class 2604 OID 33022)
-- Name: app_user user_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.app_user ALTER COLUMN user_id SET DEFAULT nextval('public.app_user_user_id_seq'::regclass);


--
-- TOC entry 3188 (class 2604 OID 33034)
-- Name: operation operation_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operation ALTER COLUMN operation_id SET DEFAULT nextval('public.operation_operation_id_seq'::regclass);


--
-- TOC entry 3189 (class 2604 OID 33042)
-- Name: record record_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.record ALTER COLUMN record_id SET DEFAULT nextval('public.record_record_id_seq'::regclass);


--
-- TOC entry 3348 (class 0 OID 33019)
-- Dependencies: 215
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.app_user VALUES (20, '2023-08-12 15:22:49.914069', 1, '$2y$10$M2Zr9N8//OT2AwfrDHzfduvnLPxUQCH5mtsx3yuqF1etQmGeBTXhu', 'ACTIVE', 'admin@calculator.com', '2023-08-12 15:22:49.914069');
INSERT INTO public.app_user VALUES (20, '2023-08-12 15:22:49.914069', 2, '$2y$10$eRCxekkX30688K1iV4bJ.ufJCqSxZYXQ9.Yrg/ewxFXQO1llZ4GIm', 'ACTIVE', 'guest@calculator.com', '2023-08-12 15:22:49.914069');


--
-- TOC entry 3350 (class 0 OID 33031)
-- Dependencies: 217
-- Data for Name: operation; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.operation VALUES (1, 1, 'ADDITION');
INSERT INTO public.operation VALUES (1, 2, 'SUBTRACTION');
INSERT INTO public.operation VALUES (2, 3, 'MULTIPLICATION');
INSERT INTO public.operation VALUES (2, 4, 'DIVISION');
INSERT INTO public.operation VALUES (3, 5, 'SQUARE_ROOT');
INSERT INTO public.operation VALUES (4, 6, 'RANDOM_STRING');


--
-- TOC entry 3352 (class 0 OID 33039)
-- Dependencies: 219
-- Data for Name: record; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3353 (class 0 OID 33055)
-- Dependencies: 220
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.user_role VALUES ('ADMIN', 1, '2023-08-12 15:23:45.559046');
INSERT INTO public.user_role VALUES ('GUEST', 2, '2023-08-12 15:23:45.559046');


--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 214
-- Name: app_user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.app_user_user_id_seq', 6, true);


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 216
-- Name: operation_operation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.operation_operation_id_seq', 7, true);


--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 218
-- Name: record_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.record_record_id_seq', 1, false);


--
-- TOC entry 3193 (class 2606 OID 33027)
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3195 (class 2606 OID 33029)
-- Name: app_user app_user_username_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_username_key UNIQUE (username);


--
-- TOC entry 3197 (class 2606 OID 33037)
-- Name: operation operation_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operation
    ADD CONSTRAINT operation_pkey PRIMARY KEY (operation_id);


--
-- TOC entry 3199 (class 2606 OID 33044)
-- Name: record record_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.record
    ADD CONSTRAINT record_pkey PRIMARY KEY (record_id);


--
-- TOC entry 3201 (class 2606 OID 33059)
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (role, user_id);


--
-- TOC entry 3204 (class 2606 OID 33060)
-- Name: user_role fkg7fr1r7o0fkk41nfhnjdyqn7b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkg7fr1r7o0fkk41nfhnjdyqn7b FOREIGN KEY (user_id) REFERENCES public.app_user(user_id);


--
-- TOC entry 3202 (class 2606 OID 33050)
-- Name: record fkm0r6sybq8av5j6ff5gjqdmt6p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.record
    ADD CONSTRAINT fkm0r6sybq8av5j6ff5gjqdmt6p FOREIGN KEY (user_id) REFERENCES public.app_user(user_id);


--
-- TOC entry 3203 (class 2606 OID 33045)
-- Name: record fkw9jt06n3r0bbig1iy99cce6t; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.record
    ADD CONSTRAINT fkw9jt06n3r0bbig1iy99cce6t FOREIGN KEY (operation_id) REFERENCES public.operation(operation_id);


-- Completed on 2023-08-12 16:11:40

--
-- PostgreSQL database dump complete
--

