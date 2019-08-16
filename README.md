# microservices-demo

this is my microservices demo application, which consists of following components:

- config-server
- eureka-server
- nginx
- api-gateway-zuul-server
- user-dashboard
- user-service
- postgres

----------------

- "**user-service**" is the bottom-most layer, which queries postgres directly
- "**user-dashboard**" is the layer above "**user-service**", which uses Feign as the declarative REST client for "**user-service**"
- "**api-gateway-zuul-server**" does Service Discovery via Eureka and does Load Balancing to other services
    - currently it is configured to spawn 3 instances in "**nginx.conf**"
    - currently the only services configured to route is "**user-dashboard**"
- "**nginx**" is the singe point of entry, which does load balancing traffic to "**api-gateway-zuul-server**"
    - update "**nginx.conf**" if you wish to change the setting of 3 api-gateway-zuul-server instances

----------------

## usage
`docker-compose up --build --scale api-gateway-zuul-server=3 --scale user-dashboard=3 --scale user-service=3`

`docker-compose down`

`docker image prune`


### Register User
`POST http://localhost:19999/auth/register`
```
{
	"username": "user1",
	"password": "pass1",
	"firstname": "Bear",
	"lastname": "Claw",
	"email": "bclaw@email.com"
}
```

### Login
`http://localhost:19999/auth/login`
```
{
	"username": "user1",
	"password": "pass1"
}
```

### Get Users
`http://localhost:19999/userUI/user-dashboard/users/`

`http://localhost:19999/userUI/user-dashboard/users/1`

----------------

Inspired by following articles:

`https://dzone.com/articles/buiding-microservice-using-springboot-and-docker`

`https://dzone.com/articles/buiding-microservice-using-spring-boot-and-docker?fromrel=true`

----------------
