# ResourceTracker

[![Build](https://github.com/YarikRevich/ResourceTracker/actions/workflows/build.yml/badge.svg)](https://github.com/YarikRevich/ResourceTracker/actions/workflows/build.yml)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
[![StandWithUkraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://github.com/vshymanskyy/StandWithUkraine/blob/main/docs/README.md)

## General Information

A cloud-native tool resource state tracking.

![](./docs/high-level-design.png)

![](./docs/detailed-design.png)

## Setup

### CLI

All setup related operations are processed via **Makefile** placed in the root directory.

In order to build the project it's required to execute the following command. Initially it cleans the environment and build Java project using **Maven**
```shell
make build
```

After the execution of command given above all the executables will be generated and placed into **bin** folder in the root directory of the project

### GUI