#!/bin/bash
sqlplus system/your_password@localhost:1521/XEPDB1 <<EOF
CREATE USER my_new_user IDENTIFIED BY my_password;
GRANT CONNECT, RESOURCE TO my_new_user;
EOF