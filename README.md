# Estafet Microservices Scrum Task API
Microservices api for managing tasks and their lifecycle for the scrum demo application.
## What is this?
This application is a microservice provides an API to add tasks to a given story. The life cycle of these tasks are affected by operations such as claim task, update hours and complete task.

Each microservice has it's own git repository, but there is a master git repository that contains links to all of the repositories [here](https://github.com/Estafet-LTD/estafet-microservices-scrum).
## Getting Started
You can find a detailed explanation of how to install this (and other microservices) [here](https://github.com/Estafet-LTD/estafet-microservices-scrum#getting-started).
## API Interface

### Messaging

|Topic   |Event    |Message Type |
|--------|---------|-------------|
|new.task.topic|When a new task is created, it is published to this topic|Task JSON Object|
|update.task.topic|When an existing task has been modified, it is published to this topic|Task JSON Object|

### Task JSON object

```json
{
    "id": 1,
    "title": "this is a task",
    "description": "testing",
    "initialHours": 12,
    "remainingHours": 0,
    "status": "Completed",
    "remainingUpdated": "2017-10-06 00:00:00",
    "storyId": 1
}
```

### Restful Operations

To retrieve an example the task object (useful for testing to see the microservice is online).

```
GET http://task-api/task
```

To retrieve a task that has an id = 1

```
GET http://task-api/task/1
```

To retrieve all of the tasks for a given story.

```
GET http://task-api/story/1/tasks
```

To add a new task to an existing story. It returns the task object.

```
POST http://task-api/story/1/task
{
    "title": "this is a task",
    "description": "testing",
    "initialHours": 12,
}
```

To update the remaining hours to 5 hours for a task (id = 1).

```
PUT http://task-api/task/1/remainingHours
"5"
```

To complete a task.Returns the task object.

```
POST http://task-api/task/1/complete
```

To claim a task. Returns the task object.

```
POST http://task-api/task/1/claim
```

## Environment Variables
```
JBOSS_A_MQ_BROKER_URL
JBOSS_A_MQ_BROKER_USER
JBOSS_A_MQ_BROKER_PASSWORD

TASK_API_JDBC_URL
TASK_API_DB_USER
TASK_API_DB_PASSWORD

SPRINT_API_SERVICE_URI
```

## Domain Model States
A task has three states. It can only progress from each state via the specific actions or events illustrated.

![alt tag](https://github.com/Estafet-LTD/estafet-microservices-scrum-api-task/blob/master/TaskStateModel.png)


