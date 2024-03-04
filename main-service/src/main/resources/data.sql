--Это дело нужно, что погонять тесты в постмане, так как по целевому процессу дата события минимум через час/два

insert into users (email, name)
values ('email1@mail.ru', 'name1');
insert into users (email, name)
values ('email1234@mail.ru', 'name1234312');
insert into users (email, name)
values ('email1234312@mail.ru', 'name1234231');
insert into users (email, name)
values ('email1234321@mail.ru', 'name1234312');

insert into category (name)
values ('category123');

insert into location (lat, lon)
values (1.123, 2.234);

insert into events
(annotation, category_id, count_dislike, count_like, created_at, description, event_date, initiator_id, location_id,
 paid, participant_limit, request_moderation, state, title, views)
values ('dwadwa', 1, 0, 0, CURRENT_TIMESTAMP, 'dawawd', '2023-03-01 00:00:00', 1, 1, true, 1000, false, 'PUBLISHED',
        'daw', 1);

insert into events
(annotation, category_id, count_dislike, count_like, created_at, description, event_date, initiator_id, location_id,
 paid, participant_limit, request_moderation, state, title, views)
values ('dwadwa123', 1, 0, 0, CURRENT_TIMESTAMP, 'dawawd', '2050-03-06 00:00:00', 1, 1, true, 1000, false, 'PUBLISHED',
        'daw', 1);

insert into events
(annotation, category_id, count_dislike, count_like, created_at, description, event_date, initiator_id, location_id,
 paid, participant_limit, request_moderation, state, title, views)
values ('dwadwa', 1, 0, 0, CURRENT_TIMESTAMP, 'dawawd', '2050-03-01 00:00:00', 1, 1, true, 1000, false, 'PENDING',
        'daw', 1);

INSERT INTO requests (created_at, event_id, requester_id, status)
VALUES (CURRENT_TIMESTAMP, 1, 2, 'CONFIRMED');

INSERT INTO requests (created_at, event_id, requester_id, status)
VALUES (CURRENT_TIMESTAMP, 1, 3, 'CONFIRMED');

INSERT INTO requests (created_at, event_id, requester_id, status)
VALUES (CURRENT_TIMESTAMP, 1, 4, 'CONFIRMED');

INSERT INTO requests (created_at, event_id, requester_id, status)
VALUES (CURRENT_TIMESTAMP, 2, 2, 'CONFIRMED');

INSERT INTO requests (created_at, event_id, requester_id, status)
VALUES (CURRENT_TIMESTAMP, 2, 3, 'CANCELED');