FROM gvenzl/oracle-free:23-slim

ENV ORACLE_PASSWORD=cenha

COPY ddl.sql /docker-entrypoint-initdb.d/ddl.sql
COPY --chown=root:root --chmod=755 run-ddl.sh /docker-entrypoint-initdb.d/run-ddl.sh

CMD ["/bin/bash", "/docker-entrypoint-initdb.d/run-ddl.sh"]