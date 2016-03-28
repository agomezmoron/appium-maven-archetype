# Oracle Java 8 installation

Depending on your Linux distribution you have to install Java 8 in two ways:

###### Debian/Ubuntu
```
# removing maven2
sudo apt-get update
sudo apt-get install -y oracle-java8-installer
# defining java 8 as default (optional)
sudo apt-get install -y oracle-java8-set-default
```
###### CenOS/RedHat
```
# Getting java-8.rpm
wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_8_CENTOS_VERSION-b27/jdk-$JAVA_8_CENTOS_VERSION-linux-x64.rpm" -O java-8.rpm
# Installing it
sudo yum localinstall -y java-8.rpm
rm java-8.rpm
sudo yum install -y oracle-java8-set-default
```
