DROP TABLE IF EXISTS portfolio;

CREATE SEQUENCE hibernate_sequence START WITH 100 INCREMENT BY 1;

CREATE TABLE portfolio (
  id INT AUTO_INCREMENT  PRIMARY KEY,    
    PRIMARY KEY (ID),

  balance DECIMAL(10,2),
  start_date TIMESTAMP WITH TIME ZONE,
  end_date TIMESTAMP WITH TIME ZONE
);

CREATE TABLE crypto_transaction (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             portfolio_id INT
);