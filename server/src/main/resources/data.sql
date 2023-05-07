CREATE TYPE currency AS ENUM ('CZK', 'EUR', 'USA');
CREATE TYPE transaction_type AS ENUM ('Income', 'Expense');

CREATE TABLE client (
                      id SERIAL PRIMARY KEY,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Wallet (
                        id SERIAL PRIMARY KEY,
                        amount NUMERIC(19, 2) NOT NULL,
                        currency currency NOT NULL,
                        user_id INTEGER NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES client (id)
);

CREATE TABLE Category (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          parent_id INTEGER,
                          FOREIGN KEY (parent_id) REFERENCES Category (id)
);

CREATE TABLE Transactions (
                             id SERIAL PRIMARY KEY,
                             money NUMERIC(19, 2) NOT NULL,
                             type transaction_type NOT NULL,
                             category_id INTEGER NOT NULL,
                             wallet_id INTEGER NOT NULL,
                             FOREIGN KEY (category_id) REFERENCES Category (id),
                             FOREIGN KEY (wallet_id) REFERENCES Wallet (id)
);