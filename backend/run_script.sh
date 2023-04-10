if [ -f .env ] ; then
  source .env;
fi;

if [ -z $WEBHOOK_URL ] ; then
  echo '$WEBHOOK_URL should be set!'
  exit 1;
fi;

docker stop sdi-postgres;
cd /home/ubuntu/lab-5x-georgerapeanu/;
git pull

docker start sdi-postgres;
sleep 5;

cd backend
./mvnw clean package;
java -jar ./target/*.jar &

curl -X POST -d "http://$(curl http://169.254.169.254/latest/meta-data/public-ipv4):8080" $WEBHOOK_URL
