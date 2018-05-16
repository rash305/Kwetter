# Kwetter backend
An twitter like application developed in Java EE with a REST API. 

Installation notes:
- Tested with a Payara 5.0 server
- An Needs an locally installed MySQL server.


# API
The API BASE URL: http://localhost:8080/kwetter-1.0-SNAPSHOT/api/
The API consumes and produces JSON.

To get all users with a page index (50 users per page)
GET
- api/users/{page}

To get all users
GET
-api/users

To create an new users
POST
- api/users/create

To get a account by id
GET
- api/users/get/{id}

To remove a account by id
DELETE
- api/users/remove/{id}

To update a account
- api/users/{id}
BODY: USER JSON object

To get all followers
GET
- api/users/followers/{id}

To get all following
GET
- api/users/following/{id}

To follow a account
POST
- api/users/followers/{id}/add/{myId}

To unfollow a account
POST
- api/users/followers/{id}/remove/{myId}


To get all available roles
GET
- api/users/roles

To approve a role to a account
PUT
- api/users/{userid}/role
BODY: A JSON role object

To remove a role of a account
DELETE
- api/users/{userid}/role/remove/{roleid}
