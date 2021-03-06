CREATE SEQUENCE hibernate_sequence START WITH 100 INCREMENT BY 1;

CREATE TABLE user_auth_sql
(
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    username              VARCHAR(40),
    password              VARCHAR(65)
);

CREATE TABLE portfolio
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    balance    DECIMAL(10, 2),
    starting_balance    DECIMAL(10, 2),
    start_date TIMESTAMP WITH TIME ZONE,
    end_date   TIMESTAMP WITH TIME ZONE,
    name   VARCHAR2(100),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user_auth_sql(id)
);

CREATE TABLE cryptocurrency
(
    ticker VARCHAR(6) PRIMARY KEY
);

CREATE TABLE crypto_transaction
(
    id                    uuid DEFAULT random_uuid(),
    portfolio_id          INT,
    cryptocurrency_ticker VARCHAR(6),
    quantity              DECIMAL(10, 5),
    price_per_coin    DECIMAL(10, 2),
    trade_date    TIMESTAMP WITH TIME ZONE,
    is_purchase           BOOLEAN,
    FOREIGN KEY (cryptocurrency_ticker) REFERENCES cryptocurrency (ticker),
    FOREIGN KEY (portfolio_id) REFERENCES portfolio (id)
);

CREATE TABLE portfolios_ranking
(
    score                 DECIMAL(10, 2) PRIMARY KEY,
    id                    INT,
    name                  VARCHAR2(100)
);

CREATE TABLE admin_metric
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       varchar2(40),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
);

create table users_authority
(
    authority_id int,
    user_id      int
);

create table authority (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name varchar(15)
);