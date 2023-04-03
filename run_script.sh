docker stop sdi-postgres;
cd /home/ubuntu/lab-5x-georgerapeanu/;
git pull

cd frontend;
npm install;
IP=$(curl -s http://169.254.169.254/latest/meta-data/public-ipv4);
API_URL="http://$IP:8080";

echo "export const environment = {
    apiURL: '$API_URL'
};
" > ./src/environments/environment.production.ts
ng build --configuration=production;

docker start sdi-postgres;
cd ../;
cd backend
./mvnw clean package;
cd ..;

sleep 5;
cd backend;
java -jar ./target/*.jar &
sleep 5;
cd ../frontend;
cd dist/frontend;
sudo http-server -p 80 -o &

