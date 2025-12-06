#!/bin/bash
# wait-for-oracle.sh
# Wait until the Oracle database is ready to accept connections
echo "Waiting for Oracle DB to start..."
until echo exit | sqlplus -s sys/${ORACLE_PASSWORD}@localhost:1521/ORCLCDB as sysdba; do
  sleep 10
done

echo "\n\n\nOracle DB is up. Running user creation commands...\n\n\n"

# Run SQL commands to create users
echo "Running ddl.sql script..."
sqlplus system/cenha@localhost:1521 @/docker-entrypoint-initdb.d/ddl.sql

echo "User creation complete."