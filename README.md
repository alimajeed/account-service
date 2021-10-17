# Account API 

Account API with Docker Script.

- [x] Spring Boot App
- [x] create user details
- [x] update user details
- [x] fetch user details
- [x] H2 Database (JPA) - This is only for demonstration. It can be extended to database of choice
- [x] Unit Testing (Junit, Mockito)
- [x] Consumes/Produces Json By default (spring)
- [x] Swagger Documentation
- [x] Logging
- [x] Exception Handling (ControllerAdvice)
- [x] Request payload validation
- [x] Docker Container
- [x] Docker Compose (For Multiple containerized applications)
- [x] K8 Scripts

## Package
mvn clean package

## Start
java -jar target/accountservice-0.0.1-SNAPSHOT.jar

## Sample requests
### Postman
#### Create User Details (POST)
http://localhost:8080/api/v1/create


#### Update User Details (POST)
http://localhost:8080/api/v1/update

#### Fetch User Details (GET)
http://localhost:8080/api/v1/fetch/1


## Swagger Documentation
http://localhost:8080/swagger-ui.html

## Docker
#### Build
docker build -t alimjd/account-service-image .

#### Run
docker run -d -p 8000:8000 <image_id>

#### Docker Compose
docker compose -f docker-compose.yaml up -d

## Kubernetes

#### Minikube
minikube start

#### Minikube UI
minikube dashboard

#### Kubernetes Services And Deployments

###### path : cd K8s

##### Commands

kubectl apply -f account-deployment.yaml
kubectl apply -f account-service.yaml

#### Run
minikube service <service-name>