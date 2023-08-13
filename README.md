# Leasing-contractor
Project Leasing contractor


##### Running application

Prerequisits

- Docker
- Jdk 11
- Gradle

1. First make sure the database is running
in root folder
```
    docker compose up database
```

2. Now run the application
```
    cd /restapi
    gradle bootRun
```

Open postman and hit
```
GET:http://localhost:8080/api
```

Look into [RestAPi-Readme](restapi/Readme.md) for more details

