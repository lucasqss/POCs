-- Drop tables if they exist
DROP TABLE if exists REG_EVT_GW ;

-- Create tables
CREATE TABLE REG_EVT_GW (
                            id NUMBER PRIMARY KEY,
                            name VARCHAR2(50)
);

CREATE TABLE REG_EVT_AGR (
                             id NUMBER PRIMARY KEY,
                             description VARCHAR2(100)
);

CREATE TABLE REG_EVT_PST (
                             id NUMBER PRIMARY KEY,
                             timestamp DATE
);