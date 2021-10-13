insert into portfolio values (null, 100000.00, 100000.00, '2021-02-03 19:20:21-06:00', '2021-02-04 19:20:21-06:00'),
                             (null, 700000.00, 100000.00, '2021-04-13 19:20:21-06:00', '2021-04-14 19:20:21-06:00'),
                             (null, 400000.00, 100000.00, '2021-07-23 19:20:21-06:00', '2021-07-23 19:25:21-06:00'),
                             (null, 200000.00, 100000.00, '2021-07-23 19:20:21-06:00', '2021-07-23 19:25:21-06:00');

insert into cryptocurrency values ('BTC'),
                                  ('ETH'),
                                  ('LTC'),
                                  ('ADA'),
                                  ('BCH'),
                                  ('DOGE');

insert into user_auth_sql values (Chris, 1234);
insert into user_auth_sql values (Jeff, 1234);
insert into user_auth_sql values (Mohammad, 1234);                                    

insert into crypto_transaction values (null, 1, 'BTC', 1.00, 5637.00, true);

insert into admin_metric values (null, 'sign_up', null);
insert into admin_metric values (null, 'sign_up', null);
insert into admin_metric values (null, 'sign_in', null);