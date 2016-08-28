FROM cogniteev/oracle-java:java8

WORKDIR /app
ADD ./target/dashboard-swarm.jar /app/

ENV db_host=pgdb
ENV db_port=5432
ENV db_database=dashboard
ENV db_username=dashboard
ENV db_password=dashboard

EXPOSE 8080

CMD java -jar /app/dashboard-swarm.jar -Dswarm.ds.connection.url=jdbc:postgresql://${db_host}:${db_port}/${db_database} -Dswarm.ds.username=${db_username} -Dswarm.ds.password=${db_password}
