/**
 * Author:  ingda
 * Created: 30/10/2021
 */
DROP TABLE IF EXISTS IP_INFO;

CREATE TABLE IP_INFO (
  ID            INT,
  IPADDRESS     VARCHAR(100) NOT NULL,
  STATUS        VARCHAR(10)  NOT NULL,
);

ALTER TABLE BLACKLIST
  ADD CONSTRAINT PK_IP_INFO PRIMARY KEY (ID);

CREATE SEQUENCE SQ_IP_INFO;
