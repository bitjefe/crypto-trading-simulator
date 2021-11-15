insert into user_auth_sql values (1 ,'Chris', '$2a$10$VG2soMyVWF8B2Iux3eusF.qtjTMDgJzTz9XxR665wjf6F1Ea0PMMm');
insert into user_auth_sql values (2 ,'Jeff', '$2a$10$VG2soMyVWF8B2Iux3eusF.qtjTMDgJzTz9XxR665wjf6F1Ea0PMMm');
insert into user_auth_sql values (3 ,'Mohammad', '$2a$10$VG2soMyVWF8B2Iux3eusF.qtjTMDgJzTz9XxR665wjf6F1Ea0PMMm');

insert into portfolio values (null, 94363.00, 100000.00, '2021-02-03 19:20:21-06:00', '2021-02-04 19:20:21-06:00', 'Only Bitcoin', 1),
                             (null, 100000.00, 100000.00, '2021-04-13 19:20:21-06:00', '2021-04-14 19:20:21-06:00', 'High Risk Coins', 1),
                             (null, 100000.00, 100000.00, '2021-10-13 19:20:21-06:00', '2021-11-14 19:20:21-06:00', 'High Risk Coins', 1),
                             (null, 100000.00, 100000.00, '2021-07-23 19:20:21-06:00', '2021-07-23 19:25:21-06:00', 'Low Risk Coins', 2),
                             (null, 100000.00, 100000.00, '2021-07-23 19:20:21-06:00', '2021-07-23 19:25:21-06:00', 'Totally Random', 2);

insert into cryptocurrency values ('BTC'),
                                  ('ETH'),
                                  ('BNB'),
                                  ('ADA'),
                                  ('USDT'),
                                  ('XRP'),
                                  ('SOL'),
                                  ('USDC'),
                                  ('DOGE');

insert into crypto_transaction values ('a2ea81a2-3f2b-11ec-9bbc-0242ac130002', 1, 'BTC', 1.00, 5637.00, '2021-02-04 19:20:21-06:00', true);

insert into admin_metric values (null, 'sign_up', '2021-11-05 19:20:21-06:00');
insert into admin_metric values (null, 'sign_up', '2021-11-05 19:20:21-06:00');
insert into admin_metric values (null, 'sign_in', '2021-11-05 19:20:21-06:00');
insert into admin_metric values (null, 'sign_in', '2021-10-13 19:20:21-06:00');

INSERT INTO authority(name, id) VALUES ('ROLE_ADMIN', 1);
INSERT INTO authority(name, id) VALUES ('ROLE_USER', 2);

INSERT INTO users_authority(authority_id, user_id) VALUES (1, 1);
INSERT INTO users_authority(authority_id, user_id) VALUES (2, 1);

INSERT INTO users_authority(authority_id, user_id) VALUES (1, 2);
INSERT INTO users_authority(authority_id, user_id) VALUES (2, 2);

INSERT INTO users_authority(authority_id, user_id) VALUES (1, 3);
INSERT INTO users_authority(authority_id, user_id) VALUES (2, 3);