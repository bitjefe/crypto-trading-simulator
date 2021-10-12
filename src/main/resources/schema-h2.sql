DROP TABLE IF EXISTS portfolio;

CREATE SEQUENCE hibernate_sequence START WITH 100 INCREMENT BY 1;

CREATE TABLE portfolio
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    PRIMARY KEY (ID),

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
    id                    INT AUTO_INCREMENT PRIMARY KEY,
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
    id                    serial PRIMARY KEY,
    username              VARCHAR(40),
    password              VARCHAR(40)

);

CREATE TABLE portfolios_ranking
(
    id                    INT PRIMARY KEY,
    score                 VARCHAR(40)

);