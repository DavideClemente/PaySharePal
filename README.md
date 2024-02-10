# PaySharePal

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)

This project is a RestFull API built using **Java, Java Spring, JWT Authentication and H2 as the database.** It also implements concepts such as hypermedia responses.

The API was developed as a personal project aiming to create an expenses division system.

## Table of Contents

- [PaySharePal](#paysharepal)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
  - [Usage](#usage)
  - [API Endpoints](#api-endpoints)
    - [Auth](#auth)
      - [POST /auth/register - Register in the system](#post-authregister---register-in-the-system)
      - [POST /auth/authenticate - Login](#post-authauthenticate---login)
    - [Users](#users)
      - [GET /users - Get all users](#get-users---get-all-users)
      - [GET /users/{userId} - Get a specific user](#get-usersuserid---get-a-specific-user)
    - [Groups](#groups)
      - [POST /groups - Add a group](#post-groups---add-a-group)
      - [GET /groups - Get all groups](#get-groups---get-all-groups)
      - [GET /groups/{groupId} - Get specific group](#get-groupsgroupid---get-specific-group)
      - [POST /groups/{groupId}/image - Add image to group](#post-groupsgroupidimage---add-image-to-group)
      - [GET /groups/{groupId}/image - Get group's image](#get-groupsgroupidimage---get-groups-image)
      - [POST /groups/{groupId}/users - Add user to group](#post-groupsgroupidusers---add-user-to-group)
  - [Database](#database)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/DavideClemente/PaySharePal.git
```

2. Install dependencies with Maven

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080/api/v1

## API Endpoints

The API provides the following endpoints:

### Auth

#### POST /auth/register - Register in the system

```json
{
    "name": "Davide",
    "email": "davide@email.com",
    "password": "davide"
}
```

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZGVAZW1haWwuY29tIiwiaWF0IjoxNzA3NTI1NTI1LCJleHAiOjE3MDc2MTE5MjV9.hTQKMxmzrVDL11tEZ5bR3Sr89RbZqQmWbGe1NT3DnJ4"
}
```

#### POST /auth/authenticate - Login

```json
{
    "email": "davide@email.com",
    "password": "davide"
}
```

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZGVAZW1haWwuY29tIiwiaWF0IjoxNzA3NTI1OTI4LCJleHAiOjE3MDc2MTIzMjh9.l_PrHu1L8bp7aTJQGJMCEk2c4FUngIoYuteqzEYAZGs"
}
```

### Users

#### GET /users - Get all users

```json
{
    "_embedded": {
        "userDtoList": [
            {
                "id": "943346af-58cf-419e-92fb-5c7b8625094d",
                "name": "Davide",
                "email": "davide@email.com",
                "_links": {
                    "self": {
                        "href": "/api/v1/users/943346af-58cf-419e-92fb-5c7b8625094d"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/users"
        }
    }
}
```

#### GET /users/{userId} - Get a specific user

```json
{
    "id": "943346af-58cf-419e-92fb-5c7b8625094d",
    "name": "Davide",
    "email": "davide@email.com",
    "_links": {
        "self": {
            "href": "/api/v1/users/943346af-58cf-419e-92fb-5c7b8625094d"
        },
        "all-users": {
            "href": "/api/v1/users"
        }
    }
}
```

### Groups

#### POST /groups - Add a group

```json
{
    "name": "Netflix"
}
```

```json
{
    "id": "f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8",
    "name": "Netflix",
    "userIds": [],
    "_links": {
        "self": {
            "href": "/groups/f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8"
        },
        "all-groups": {
            "href": "/groups"
        }
    }
}
```

#### GET /groups - Get all groups

```json
{
    "_embedded": {
        "groupDtoList": [
            {
                "id": "f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8",
                "name": "Netflix",
                "userIds": [],
                "_links": {
                    "self": {
                        "href": "/groups/f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/v1/groups"
        }
    }
}
```

#### GET /groups/{groupId} - Get specific group

```json
{
    "id": "f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8",
    "name": "Netflix",
    "userIds": [],
    "_links": {
        "self": {
            "href": "/groups/f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8"
        },
        "all-groups": {
            "href": "/groups"
        }
    }
}
```

#### POST /groups/{groupId}/image - Add image to group

```batch
 --location 'http://localhost:8080/api/v1/Groups/f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8/image' \
--form 'image=@"<image_path>"'
```

#### GET /groups/{groupId}/image - Get group's image

![Netflix](assets\netflix.png)

#### POST /groups/{groupId}/users - Add user to group

```json
{
    "userId": "943346af-58cf-419e-92fb-5c7b8625094d"
}
```

```json
{
    "id": "f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8",
    "name": "Netflix",
    "userIds": ["943346af-58cf-419e-92fb-5c7b8625094d"],
    "_links": {
        "self": {
            "href": "/groups/f3e2fbe0-f90a-4f0a-937d-eca234c7d7c8"
        },
        "all-groups": {
            "href": "/groups"
        }
    }
}
```

## Database

The project utilizes [H2 Database](https://www.h2database.com/html/tutorial.html) as the database.
