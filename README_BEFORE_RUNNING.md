# HOW TO RUN THIS PROJECT  

Running this project is very simple - just run the "ProjectApplication" file.

Tests are found in "ProjectApplicationTests" - you can run them as well by running this file.

In "data.sql" you will find the queries, which occupies the database whenever the project starts, 
you can add/remove objects by changing that file.

In "dataForTests" you will find the queries which are run before each test. 

# What's in it for me?
This project exposes an API (no UI) in which you can add/retrieve/update/delete Employees in/from an H2 database.
Every Employee has general details(name, age, etc.), and may contain Addresses, Children, and a spouse.

The API exposes GET/POST/PUT/PATCH/DELETE entry points.
A link to POSTMAN Collection (including Swagger documentation) can be found here (import it using POSTMAN): https://www.getpostman.com/collections/5f822861f2b152916c2d .
 
 