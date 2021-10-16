CREATE SEQUENCE hibernate_sequence START WITH 100 INCREMENT BY 1;

CREATE TABLE portfolio
(
    id         SERIAL PRIMARY KEY,

    balance    DECIMAL(10, 2),
    starting_balance    DECIMAL(10, 2),
    start_date TIMESTAMP WITH TIME ZONE,
    end_date   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE cryptocurrency
(
    ticker VARCHAR(6) PRIMARY KEY
);

CREATE TABLE crypto_transaction
(
    id                    SERIAL PRIMARY KEY,
    portfolio_id          INT,
    cryptocurrency_ticker VARCHAR(6),
    quantity              DECIMAL(10, 5),
    price_per_coin    DECIMAL(10, 2),
    is_purchase           BOOLEAN,
    FOREIGN KEY (cryptocurrency_ticker) REFERENCES cryptocurrency (ticker),
    FOREIGN KEY (portfolio_id) REFERENCES portfolio (id)
);

CREATE TABLE user_auth_sql
(
    id                    SERIAL PRIMARY KEY,
    username              VARCHAR(40),
    password              VARCHAR(40)
);

CREATE TABLE portfolios_ranking
(
    score                 VARCHAR(40) PRIMARY KEY,
    id                    INT
);

CREATE TABLE admin_metric
(
    id         SERIAL PRIMARY KEY,
    name       varchar2(40),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
);
