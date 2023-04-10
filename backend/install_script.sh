sudo apt-get update;
sudo apt-get install -y openjdk-19-jdk-headless;
docker create  \
  --name sdi-postgres \
  -e POSTGRES_PASSWORD=sdi-postgres \
  -p 5432:5432 \
  -v sdi-volume:/var/lib/postgresql/data \
  sdi-postgres;
