# Lab 1 assignment

**Points**: 1

**Deadline**: Week 2

**Last chance deadline and penalties**: Week 4, -0.3 points / week delayed

----

Choose any programming language and a **backend** framework and setup a development environment in it. Create an
application with CRUD functionalities for a single entity of your choice. The entity should have at least 5 attributes,
not counting the ID. You do not need any validations.

Remarks:

- Your application should either expose a REST API (like Django REST Framework, Flask) or a webpage that can be accessed
  in the browser (like Django)
- Console apps are **not** allowed
- Frontend frameworks / libraries (React, Angular) are **not** allowed
- You should be able to explain basic things about your framework of choice (where routes are defined, how the models
  are handled etc.)
- You will also be marked on good coding practices and design principles, as they have been taught in Fundamentals of
  Programming and Object Oriented Programming and as they apply to your choice of framework

Suggestions:

- You can probably find libraries in your favorite language that help you build Web applications and REST APIs
- I suggest you make a REST API, as that will become mandatory later on anyway
- Java has Spring, Python has Django, C# has WebApi etc. The keywords to search for are your language + rest api
- Make sure you get comfortable with reading your choice's documentation and that you pick something with an active
  community, as you will be spending a lot of time studying stuff on your own

# Lab 2 assignment

**Points**: 1

**Deadline**: Week 3

**Last chance deadline and penalties**: Week 5, -0.3 points / week delayed

----

Continue working on the application from the previous assignment. Push your project to this repository.

You will need to:
- Add at least 2 more entities with at least `5` attributes each: you should have at least `3` entities in total.
- Add a **1 to many** relation between two of your entities. Some of the entities can be unrelated, but be prepared to also add a many to many relation soon. Choose your entities accordingly.
- Add storage to a database if you don't already have it: any database type is allowed. Rerunning the application should keep previously existing data. It's ok if changes to the data model drops and recreates the database (you do not need migrations yet).
- Implement CRUD for at least one more entity and filtering on a numeric field for at least one entity. The filtering should return all entities with the numeric field higher than a given value.
- Have a REST API usable in Postman.
