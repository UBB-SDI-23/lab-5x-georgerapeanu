docker stop sdi-postgres;
cd /home/ubuntu/lab-5x-georgerapeanu/;
git pull

docker start sdi-postgres;
sleep 5;

cd backend
./mvnw clean package;

java -jar ./target/*.jar &


