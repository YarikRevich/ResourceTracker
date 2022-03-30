# 1. Aim of the project

Aim of the project - to create a cloud native tool, which tracks pointed targets with certain requests. The tool is fully integratabtle to the chosen cloud provider

# 2. System describtion

The system consists of such components:
1. Functionality of the configuration the work of the tool via YAML config file
2. Functionality of the tool controlling via CLI, GUI or WEB interfaces. It can’t replace the configuration file
3. Functionality of the caching of the configuration file to the chosen cloud provider
4. Functionality of the mailing with certain conditions with certain frequency
Design of the tool is monolith, minimal customization, maximal usefullness! 

## 2.1. Types of the external interface

Such as the system should literally deploy itself to the cloud it should define how to be run. User can only effect the tool via commands 

## 2.2. Configuring YAML config file 

- addresses:
    - tag: string, optional
      host: string, mandatory
      pathes: array, optional
- cloud:
    credentials: string, mandatory
- settings:
    report_frequency: int, optional

## 2.3. Cloud provider credentials

Each cloud provider has its own credentials file in the certain location in the OS, but user can define the path to the credentials file manually in the config file.

## 2.4. Reports

If request to the set host with pathes fails, there will be sent a message to the set email address. By default, a good report will sent each day to the set email address. Reports yet can only be sent via email.

# 2.5. Interfaces

- CLI(Command Line Interface)
- GUI(Global User Interface)
- WEB

# 3. Technology stack

* Back-end:
    - Java
    - Maven
    - AWS SDK
    - Terraform for deployment

Config file caching will be done via buckets in cloud providers.

# 4. Design requirements

There should not be a lot of information, shown in
the configugration file or in the 'help' command via CLI. Information should be as useful as possible.