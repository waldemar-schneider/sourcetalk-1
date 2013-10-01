# -*- mode: ruby -*-
# vi: set ft=ruby :

$script_ci = <<SCRIPT
echo Provisioning VM...

echo Setting hostname...
echo ci > /etc/hostname
echo 127.0.0.1 localhost >  /etc/hosts
echo 127.0.1.1 ci        >> /etc/hosts
hostname ci

echo Installing dependencies...
apt-get -qy install git openjdk-7-jdk tomcat7

echo Installing webapps...
chmod 777 /usr/share/tomcat7
wget -q -O /var/lib/tomcat7/webapps/jenkins.war http://mirrors.jenkins-ci.org/war/latest/jenkins.war
wget -q -O /var/lib/tomcat7/webapps/nexus.war http://www.sonatype.org/downloads/nexus-latest.war
service tomcat7 restart

echo Done.
SCRIPT

$script_prod = <<SCRIPT
echo Provisioning VM...
echo Installing dependencies...
apt-get -qy install nginx openjdk-7-jdk
echo "upstream myCloud {             " >  /etc/nginx/sites-available/default
echo "  server localhost:8080;       " >> /etc/nginx/sites-available/default
echo "  server localhost:8081;       " >> /etc/nginx/sites-available/default
echo "}                              " >> /etc/nginx/sites-available/default
echo "                               " >> /etc/nginx/sites-available/default
echo "server {                       " >> /etc/nginx/sites-available/default
echo "  listen 80;                   " >> /etc/nginx/sites-available/default
echo "                               " >> /etc/nginx/sites-available/default
echo "  location / {                 " >> /etc/nginx/sites-available/default
echo "    proxy_pass http://myCloud  " >> /etc/nginx/sites-available/default
echo "  }                            " >> /etc/nginx/sites-available/default
echo "}                              " >> /etc/nginx/sites-available/default
service nginx restart
echo Done.
SCRIPT

Vagrant.configure("2") do |config|
  config.vm.box = "raring-server-cloudimg-amd64-vagrant"
  config.vm.box_url = "http://cloud-images.ubuntu.com/raring/current/raring-server-cloudimg-vagrant-amd64-disk1.box"

  config.vm.provider "virtualbox" do |vm|
    vm.customize [
                     'modifyvm', :id,
                     '--memory', '2048',
                     '--cpus', '2',
                 ]
  end

  config.vm.define "ci" do |ci|
    ci.vm.provision :shell, :inline => $script_ci
    ci.vm.network "forwarded_port", guest: 8080, host: 18080
  end

  config.vm.define "prod" do |prod|
    prod.vm.provision :shell, :inline => $script_prod
    prod.vm.network "forwarded_port", guest: 80, host: 20080
  end
end