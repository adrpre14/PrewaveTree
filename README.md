# Interview Development Task Solution
This is a solution to the development task for the interview process at Prewave.
The task is to create a simple REST API for a tree-structure of nodes which represent companies and the connection between them.
The connection between the nodes is called "Edge" and it is a directed connection from one node to another.

The API should be able to:
- Create a new edge
- Delete an edge
- Get a tree by node id

All the requests and responses are in JSON format.\
I used .env file to store the environment variables, but you can use the environment variables in the run configuration.

## Pre-requisites
The project is recommended to be used on IntelliJ IDEA (that is because the run configuration are already stored in the project under /.run).
To run the project, you need:
- Java 22 installed on your machine
- Docker installed on your machine
- a .env file in the root (or simply directly in the run configuration) with the following variables:
    - DB_PORT
    - DB_URL (example: jdbc:postgresql://localhost, no port or db name)
    - DB_NAME
    - DB_USER
    - DB_PASSWORD

## Running the project
1. First you need to run the docker-compose file to start the database. 
(if you are not using .env file, you need to change the docker-compose.yml file by removing/modifying the env-file line)
2. Now you need to run the src/main/resources/sql/create-table.sql file to create the table and additional functions in the database.
3. After that you need to generate the jooq classes by running the configuration in the IDE 
or by running it manually (don't forget the env variables).
4. Finally, you can start the project by running the configuration in the IDE or by running the main method in the Main class.\
If any errors occur, please check the environment variables.\
It might also be possible that the generated jooq code in the target/ directory
is marked as "excluded" in the IDE. In that case, you need to mark it as "generated sources root".
