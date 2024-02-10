# PaySharePal

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)

This project is an API built using **Java, Java Spring, H2 as the database.**

The API was developed as a personal project aiming to create an expenses division system.

## Table of Contents

- [PaySharePal](#paysharepal)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
  - [Usage](#usage)
  - [API Endpoints](#api-endpoints)
  - [Users](#users)
    - [POST /users - Register a new user](#post-users---register-a-new-user)
    - [GET /users - Get all users](#get-users---get-all-users)
  - [Database](#database)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/DavideClemente/PaySharePal.git
```

2. Install dependencies with Maven

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080

## API Endpoints

The API provides the following endpoints:

**POST USERS**

## Users

### POST /users - Register a new user

```json
{
    "name": "Xico",
    "email": "xico@email.com"
}
```

### GET /users - Get all users

```json
{
    "name": "Xico",
    "email": "xico@email.com"
}
```

TODO...

## Database

The project utilizes [H2 Database](https://www.h2database.com/html/tutorial.html) as the database.
