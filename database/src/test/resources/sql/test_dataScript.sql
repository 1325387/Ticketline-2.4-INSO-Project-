BEGIN TRANSACTION

----------------------------------- News -----------------------------------

INSERT INTO news(id, submittedon, title, newstext) VALUES (1, '2013-06-18 00:00:00.000', 'News 1', 'Text 1');
INSERT INTO news(id, submittedon, title, newstext) VALUES (2, '2013-06-18 00:00:00.000', 'News 2', 'Text 2');
INSERT INTO news(id, submittedon, title, newstext) VALUES (3, '2013-06-30 00:00:00.000', 'News 3', 'Text 3');
INSERT INTO news(id, submittedon, title, newstext) VALUES (4, '2013-06-30 00:00:00.000', 'News 4', 'Text 4');
INSERT INTO news(id, submittedon, title, newstext) VALUES (5, '2013-07-05 00:00:00.000', 'News 5', 'Text 5');
INSERT INTO news(id, submittedon, title, newstext) VALUES (6, '2013-07-08 00:00:00.000', 'News 6', 'Text 6');
INSERT INTO news(id, submittedon, title, newstext) VALUES (7, '2013-07-08 00:00:00.000', 'News 7', 'Text 7');
INSERT INTO news(id, submittedon, title, newstext) VALUES (8, '2013-07-08 00:00:00.000', 'News 8', 'Text 8');
INSERT INTO news(id, submittedon, title, newstext) VALUES (9, '2013-07-08 00:00:00.000', 'News 9', 'Text 9');
INSERT INTO news(id, submittedon, title, newstext) VALUES (10, '2013-07-09 00:00:00.000', 'News 10', 'Text 10');
COMMIT
----------------------------------- Row ------------------------------------
BEGIN TRANSACTION
INSERT INTO row(id, description, name, sequence) VALUES (1, 'a row', 'row1', 1);
INSERT INTO row(id, description, name, sequence) VALUES (2, 'a row', 'row2', 2);
INSERT INTO row(id, description, name, sequence) VALUES (3, 'a row', 'row3', 3);
COMMIT
----------------------------------- Category --------------------------------
BEGIN TRANSACTION
INSERT INTO category(id, description, name) VALUES (1, 'a category', 'category1');
COMMIT
------------------------------------ Location --------------------------------
BEGIN TRANSACTION
INSERT INTO location(id, city, country, postalcode, street, description, name, owner) VALUES (1, 'a city', 'a country', 'a postalcode', 'a street', 'a description', 'location 1', 'a owner');
COMMIT
------------------------------------ Room -----------------------------------
BEGIN TRANSACTION
INSERT INTO room(id, description, name, location_id) VALUES (1, 'a room', 'room1', 1);
INSERT INTO room(id, description, name, location_id) VALUES (2, 'a room', 'room2', 1);
COMMIT
------------------------------------ Seat -----------------------------------
BEGIN TRANSACTION
INSERT INTO seat(id, name, description, sequence, category_id, row_id, gallery_id, room_id) VALUES (1, 'seat 1', 'a description', 1, 1, 1, null, 1);
INSERT INTO seat(id, name, description, sequence, category_id, row_id, gallery_id, room_id) VALUES (2, 'seat 2', 'a description', 2, 1, 2, null, 1);
INSERT INTO seat(id, name, description, sequence, category_id, row_id, gallery_id, room_id) VALUES (3, 'seat 3', 'a description', 1, 1, 1, null, 2);
INSERT INTO seat(id, name, description, sequence, category_id, row_id, gallery_id, room_id) VALUES (4, 'seat 4', 'a description', 2, 1, 1, null, 2);
INSERT INTO seat(id, name, description, sequence, category_id, row_id, gallery_id, room_id) VALUES (5, 'seat 5', 'a description', 3, 1, 1, null, 2);
COMMIT
----------------------------------- Performance  -----------------------------------
BEGIN TRANSACTION
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (1, 'Performance 1', '120', 'Performancename 1', 'MOVIE');
INSERT INTO performance(id, description, duration, name, performancetype) VALUES (2, 'Performance 2', '120', 'Performancename 2', 'MOVIE');
COMMIT
----------------------------------- Show  -----------------------------------
BEGIN TRANSACTION
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (1, FALSE, '2013-06-18 00:00:00.000',1 ,1);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (2, FALSE, '2013-06-18 00:00:00.000',1 ,1);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (3, FALSE, '2013-06-18 00:00:00.000',2 ,2);
INSERT INTO show(id, canceled, dateofperformance, performance_id, room_id) VALUES (4, FALSE, '2013-06-18 00:00:00.000',2 ,2);
COMMIT
----------------------------------- Ticket  -----------------------------------
BEGIN TRANSACTION
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (1, 'Ticket 1', '8', 1, 1);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (2, 'Ticket 2', '8', 2, 1);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (3, 'Ticket 3', '8', 1, 2);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (4, 'Ticket 4', '8', 2, 2);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (5, 'Ticket 5', '8', 3, 3);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (6, 'Ticket 6', '8', 4, 3);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (7, 'Ticket 7', '8', 5, 3);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (8, 'Ticket 8', '8', 3, 4);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (9, 'Ticket 9', '8', 4, 4);
INSERT INTO ticket(id, description, price, seat_id, show_id) VALUES (10, 'Ticket 10', '8', 5, 4);


COMMIT