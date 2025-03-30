# Interview Development Task Solution
This is a solution to the development task for the interview process at Prewave.
The task is to create a simple REST API for a tree-structure of nodes which represent companies and the connection between them.
The connection between the nodes is called "Edge" and it is a directed connection from one node to another.\
\
Just as in a tree structure, all the nodes have just one single parent node, except for the root node which has no parent. 
They can have as many children as they want. A child node cannot have another node of the tree as a child node,
but it can have a root node of another tree as a child node.

Example:
```
    A        In this tree the child B has two children: D and E.
   / \       It would not be possible for B to have another node of the tree as a child node 
  B   C      such as: A (since this is the root of the tree where B is), C or F (already children)
 / \   \     
D   E   F    

    H        The node B could have another root node of another tree as a child node such as: H
   / \       but not: I or J.
  I   J
  
       A  
      / \
     B   C
   / | \   \
  H  D  E   F
 / \
I   J        P.S: Obviously, a node cannot connect to itself.
```

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
- (If you are using .env file) I reckon you would need the EnvFile plugin. Check it out for more info [here](https://plugins.jetbrains.com/plugin/7861-envfile).

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

## Postman collection
I have created a Postman collection with all the endpoints. To create and delete edges you have to pass to the body the
"fromId" and "toId" of the nodes you want to create or delete the edge between. They both have validation as to what 
the parameters should be with corresponding error messages. The get tree endpoint returns the tree in a JSON format.\
For example if you have the following tree:
```
    1
   / \
  2   3
 / \
4   5
```
And you want to get the tree starting from node 1, the response would be:
```
{
    "root": 1,
    "children": [
        {
            "root": 2,
            "children": [
                {
                    "root": 4,
                    "children": []
                },
                {
                    "root": 5,
                    "children": []
                }
            ]
        },
        {
            "root": 3,
            "children": []
        }
    ]
}
```