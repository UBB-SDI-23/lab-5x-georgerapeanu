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

# Lab 3 assignment

**Points**: 2

**Deadline**: Week 4

**Last chance deadline and penalties**: Week 6, -0.5 points / week delayed

----

Continue working on the application from the previous assignment. Push your project to this repository.

- Add a **many to many** relation between `2` entities. This should be added as a separate entity containing the two related entities or their IDs and at least 2 additional attributes. For example: `Transaction` with `Product` and `Client` as the related entities and `Date` and `Volume` as the additional attributes.
- Make sure all of your entities are part of at least one `1 to 1`, `1 to many` or `many to many` relation.
- Add CRUD for all entities.
- Add at least `10` realistic-looking records for each of your models / tables.
- Use Trello or ClickUp and set up a Kanban board (with at least `Todo`, `Doing`, `Done` or equivalent lists). You will need to update this for all functionalities you do from now on. Your lab instructor will give you feedback on how to improve its setup.
- Create a UML diagram for your application and a database diagram for your database. 
- Add a statistical report involving two entities. For example: show all movies ordered by the average age of their actors. Such reports **need** to show the computed field: the average age of the actors in this case. You probably want to use a `DTO` class for this.

For this and all future assignments, unless otherwise specified, you are not allowed to write any raw SQL queries: it must all be done through an ORM.
