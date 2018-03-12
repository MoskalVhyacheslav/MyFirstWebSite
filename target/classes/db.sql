USE project;


CREATE TABLE IF NOT EXISTS roles (
  role_id   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  role_name VARCHAR(20)        NOT NULL UNIQUE
)
  DEFAULT CHAR SET = utf8;

INSERT INTO roles (role_id, role_name) VALUES (1, 'admin');
INSERT INTO roles (role_id, role_name) VALUES (2, 'librarian');
INSERT INTO roles (role_id, role_name) VALUES (3, 'user');

CREATE TABLE IF NOT EXISTS users (
  user_id  INT AUTO_INCREMENT    NOT NULL,
  login    VARCHAR(30)           NOT NULL UNIQUE,
  mail     VARCHAR(30)           NOT NULL UNIQUE,
  password VARCHAR(30)           NOT NULL,
  balance  DOUBLE UNSIGNED       NOT NULL         DEFAULT 0,
  credit   DOUBLE UNSIGNED       NOT NULL         DEFAULT 0,
  blocked  BOOLEAN DEFAULT FALSE NOT NULL,
  role     INT DEFAULT 3         NOT NULL,
  PRIMARY KEY (user_id),
  FOREIGN KEY role(role) REFERENCES roles (role_id)
)
  DEFAULT CHARSET = utf8;

INSERT INTO users (login, mail, password, role) VALUES ('admin', 'slavik@gmail.com', 'admin', 1);

CREATE TABLE IF NOT EXISTS books (
  book_id      INT AUTO_INCREMENT NOT NULL,
  title        VARCHAR(30)        NOT NULL,
  author       VARCHAR(30)        NOT NULL,
  edition      VARCHAR(30)        NOT NULL,
  edition_date DATE               NOT NULL,
  credit_days  INT UNSIGNED       NOT NULL,
  amount       INT UNSIGNED       NOT NULL,
  PRIMARY KEY (book_id),
  CONSTRAINT book UNIQUE (title, author)
)
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS orders (
  order_id    INT                        NOT NULL AUTO_INCREMENT,
  user_id     INT                        NOT NULL,
  book_id     INT                        NOT NULL,
  credit      DOUBLE UNSIGNED DEFAULT 0  NOT NULL,
  credit_date DATETIME                   NULL,
  update_date DATETIME                   NULL,
  FOREIGN KEY (user_id) REFERENCES users (user_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES books (book_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  PRIMARY KEY (order_id),
  CONSTRAINT pass UNIQUE (user_id, book_id)
)
  DEFAULT CHAR SET = utf8;



DELIMITER $$
CREATE EVENT check_date
  ON SCHEDULE EVERY 1 MINUTE
  STARTS CURRENT_TIMESTAMP
  ON COMPLETION PRESERVE
DO
  UPDATE orders
  SET credit = orders.credit + 50, update_date = NOW()
  WHERE (credit_date <= NOW() AND update_date IS NULL)
        or
        (TIMESTAMPDIFF(DAY,orders.update_date,NOW()) >= 1 AND update_date IS NOT NULL);



CREATE TRIGGER count_user_credit
AFTER UPDATE
  ON project.orders
FOR EACH ROW
  BEGIN

    UPDATE users c
      INNER JOIN (SELECT
                    user_id,
                    SUM(credit) AS total
                  FROM orders
                  GROUP BY user_id
                 ) x ON c.user_id = x.user_id
    SET c.credit = x.total;
  END;


