INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurants (name, description)
VALUES ('Philibert', 'French cuisine'),
       ('Palermo', 'Italian cuisine'),
       ('Ditay', 'Chinese cuisine'),
       ('Ronny', 'Japanese cuisine');

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('Onion soup', 410.00, 1),
       ('Salad', 660.00, 1),
       ('Tomato soup', 260.00, 2),
       ('Salad with chicken', 290.00, 2),
       ('Soup with duck', 250.00, 3),
       ('Funchose', 360.00, 3),
       ('Ramen', 350.00, 4),
       ('Sushi', 350.00, 4);

INSERT INTO dish_dates_use (date_use, dish_id)
VALUES ('2021-01-05', 1),
       (now(), 1),
       ('2021-01-05', 2),
       (now(), 2),
       ('2021-01-05', 3),
       ('2021-01-05', 4),
       ('2021-01-05', 5),
       ('2021-01-05', 6),
       ('2021-01-05', 7),
       ('2021-01-05', 8);

INSERT INTO votes (date, user_id, restaurant_id)
VALUES (now(), 1, 1),
       (now(), 2, 2);