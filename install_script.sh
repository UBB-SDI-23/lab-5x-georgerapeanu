sudo apt-get update;
sudo apt-get install -y openjdk-19-jdk-headless;
curl -fsSL https://deb.nodesource.com/setup_18.x | bash - &&\
apt-get install -y nodejs
npm install npm@latest -g
npm install npm@latest -g
docker create -d \
  --name sdi-postgres \
  -e POSTGRES_PASSWORD=sdi-postgres \
  -p 5432:5432 \
  -v sdi-volume:/var/lib/postgresql/data \
  sdi-postgres;
curl -O https://go.dev/dl/go1.20.2.linux-amd64.tar.gz;
rm -rf /usr/local/go;
tar -C /usr/local -xzf go1.20.2.linux-amd64.tar.gz;
rm go1.20.2.linux-amd64.tar.gz;
echo 'export PATH=$PATH:/usr/local/go/bin' >> ~/.profile;
/usr/local/go/bin install github.com/hairyhenderson/gomplate/v4/cmd/gomplate@latest;
npm install http-server -g;
echo 'Logout to complete installation';
