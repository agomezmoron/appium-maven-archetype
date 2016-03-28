# Apache Maven 3 installation

Depending on your Linux distribution you have to install Maven 3 in two ways:

###### Debian/Ubuntu
```
# removing maven2
sudo apt-get update
sudo apt-get install -y maven
```
###### CenOS/RedHat
```
sudo yum check-update
sudo yum install -y apache-maven
```

Once maven is installed, check it performing:
```
mvn -version
```
