if [ -f .env ] ; then
  source .env;
fi;

if [ -z $WEBHOOK_URL ] ; then
  echo '$WEBHOOK_URL should be set!'
  exit 1;
fi;

cd /home/ubuntu/lab-5x-georgerapeanu/;
git pull

cd backend
./mvnw clean package -Dspring.profiles.active=prod;
java -jar ./target/*.jar --spring.profiles.active=prod &

curl -X POST -d "http://$(curl http://169.254.169.254/latest/meta-data/public-ipv4):8080" $WEBHOOK_URL
