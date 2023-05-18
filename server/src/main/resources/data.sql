CREATE TYPE currency AS ENUM ('CZK', 'EUR', 'USD');
CREATE TYPE transaction_type AS ENUM ('Income', 'Expense');

CREATE TABLE Client (
                        client_id SERIAL PRIMARY KEY,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Wallet (
                        wallet_id SERIAL PRIMARY KEY,
                        amount NUMERIC(19, 2) NOT NULL,
                        currency currency NOT NULL,
                        client INTEGER NOT NULL,
                        name VARCHAR(255) NOT NULL ,
                        FOREIGN KEY (client) REFERENCES client (client_id)
);

CREATE TABLE Category (
                          category_id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE Transactions (
                              transactions_id SERIAL PRIMARY KEY,
                              description VARCHAR(255) NOT NULL,
                              money NUMERIC(19, 2) NOT NULL,
                              type transaction_type NOT NULL,
                              category INTEGER NOT NULL,
                              wallet INTEGER NOT NULL,
                              budget_limit NUMERIC(19, 2),
                              trans_date TIMESTAMP NOT NULL DEFAULT current_timestamp,
                              FOREIGN KEY (category) REFERENCES Category (category_id),
                              FOREIGN KEY (wallet) REFERENCES Wallet (wallet_id)
);

CREATE TABLE Goals (
                       money_goal NUMERIC(19, 2) NOT NULL,
                       goal VARCHAR(255) NOT NULL
);
