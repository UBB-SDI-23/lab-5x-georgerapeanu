cd /home/ubuntu/lab-5x-georgerapeanu/;
git pull
cd backend
./mvnw clean package;
cd ../frontend;
IP=$(curl -s http://169.254.169.254/latest/meta-data/public-ipv4);
API_URL="http://$IP:8080";

echo "export const environment = {
    apiURL: '$API_URL'
};
" > ./src/environments/environment.production.ts

ng build --prod --configuration=production;
cd ..;
docker start sdi-postgres;
sleep 5;
cd backend;
java -jar ./target/*.jar&;
sleep 5;
cd ../frontend;
cd dist/frontend;
sudo http-server -p 80 -o&;

