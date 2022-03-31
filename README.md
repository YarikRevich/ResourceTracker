# ResourceTracker🇺🇦

[![Linux](https://svgshare.com/i/Zhy.svg)](https://svgshare.com/i/Zhy.svg)
[![macOS](https://svgshare.com/i/ZjP.svg)](https://svgshare.com/i/ZjP.svg)

A cloud-native distributed tracker for resources, which are set via configuration file

## Installation

### Linux

```
sudo apt-get install default-jre && sudo apt-get install default-jdk // installs the latest version of java

wget http://www-us.apache.org/dist/kafka/2.7.0/kafka_2.13-2.7.0.tgz // installs kafka
tar xzf kafka_2.13-2.7.0.tgz
mv kafka_2.13-2.7.0 /usr/local/kafka

sudo apt-get update && sudo apt-get install -y gnupg software-properties-common curl // installs terraform
curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -
sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"
sudo apt-get update && sudo apt-get install terraform
```

### MacOS

```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)" // installs brew
brew cask install java // installs the latest version of java
brew install kafka // installs kafka
brew install terraform // installs terraform
```

See more at ![Wiki](../../wiki)
