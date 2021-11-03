insert into user_auth_sql values (1 ,'Chris', '1234');
insert into user_auth_sql values (2 ,'Jeff', '1234');
insert into user_auth_sql values (null ,'Mohammad', '1234');

insert into portfolio values (null, 94363.00, 100000.00, '2021-02-03 19:20:21-06:00', '2021-02-04 19:20:21-06:00', 'Only Bitcoin', 1),
                             (null, 100000.00, 100000.00, '2021-04-13 19:20:21-06:00', '2021-04-14 19:20:21-06:00', 'High Risk Coins', 1),
                             (null, 100000.00, 100000.00, '2021-10-13 19:20:21-06:00', '2021-11-14 19:20:21-06:00', 'High Risk Coins', 1),
                             (null, 100000.00, 100000.00, '2021-07-23 19:20:21-06:00', '2021-07-23 19:25:21-06:00', 'Low Risk Coins', 2),
                             (null, 100000.00, 100000.00, '2021-07-23 19:20:21-06:00', '2021-07-23 19:25:21-06:00', 'Totally Random', 2);

insert into cryptocurrency values ('BTC'),
                                  ('ETH'),
                                  ('LTC'),
                                  ('ADA'),
                                  ('BCH'),
                                  ('DOGE');

insert into crypto_transaction values (null, 1, 'BTC', 1.00, 5637.00, '2021-02-04 19:20:21-06:00', true);

insert into admin_metric values (null, 'sign_up', null);
insert into admin_metric values (null, 'sign_up', null);
insert into admin_metric values (null, 'sign_in', null);