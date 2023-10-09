cd ../server/sales/
mvn clean package docker:build

cd ../../infra/
docker compose up cassandra -d
sleep 40
docker compose up cqlsh -d
sleep 40
docker compose up backend -d
docker compose up frontend -d
