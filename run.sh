sudo apt install mysql-server
sudo service mysql stop
sudo service mysql start &

sudo mysql < passwordChange.sql
mysql -h 127.0.0.1 -P 3306 -u root -p < initDB.sql

./mvnw clean install package
cd target
java -jar Spirent-1.0-SNAPSHOT.jar org.example.BirdSightseeingApplication