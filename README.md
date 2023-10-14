# Simple todolist-java-restAPI

This is a simple restAPI written in Java using the Spring Boot framework for a to-do list app. It includes the following features:

* User creation;
* User authentication;
* Hash of user password;
* On memory database. Intended for development use only as it resets its data everytime the app reloads.;
* Add task to to-do list;
* List tasks for a user;
* Edit a created task for a user; 

This repo was built using the MVC (Model-View-Controller) design pattern. And it also includes a Dockerfile to run the application containerized, if needed. 

Application is online at https://demo-todolist-java-api.onrender.com/ using docker on render.com free tier. (Needs to be manually restarted if idle for some time.)

---

# Application workflow

This simple to-do list java restAPI has the following structure:

User entity is modeled in the UserModel. It has simple user attributes as username, password, name, UUID, user creation time. 
UserController handles user creation and checks if a user already exists. 

Task entity is modeled in TaskModel. Has attributes like description, title, start and end date, priority.
TaskController handles CRUD operations and basic date and time checking. 

Unauthorized access is handled in FilterTaskAuth. Not allowing unauthorized user to access a task. The user can only access tasks he has created himself.

Exception is handled at ExceptionHandlerController. Informing the user about more specific error, when hitting the API's task title max size. The title size could be bigger, but it is artificially limited to show a simple exception handling capability.

Application properties are set at the resources package. 

---

# How to run

Ensure you have maven and java-17 available. 
To start the application just run:

`mvn spring-boot:run`

 Access the application at `https://localhost:8080/users/` to create the first user. 
 
___
# Screenshots

![Image shows a User being created using the /users/ path to the https://demo-todolist-java-api.onrender.com website. The http method used is POST and the request provides a name, username and a password. The response HTTP code is 201, indicating the user was created, showing the user's id, username, name, hashed password and creation date and time.](assets/Screenshot from 2023-10-14 12-48-50.png "User creation through API")