#Pasos basicos despliegue manual
-------------------------------------------
- Crear network 
	docker network create lxfutbol-network

- Crear contenedor mysql

	docker run --detach --env MYSQL_ROOT_PASSWORD=lxfutbolPass --env MYSQL_USER=lxfutbol --env MYSQL_PASSWORD=lxfutbolPass --env MYSQL_DATABASE=lxfutbol --name mysql --publish 3306:3306 --network=lxfutbol-network --volume mysql-database-volume:/var/lib/mysql mysql:5.7
	
- Crear imagen de proyecto Provider
	mvn clean package -DskipTests
	docker build -t lxfutbol/provider-service:dockerfile1 .
	
- Correr imagen de proyecto	Provider
	docker container run -p 8080:8080 -d --network=lxfutbol-network --name=providerService -e RDS_HOSTNAME=mysql lxfutbol/provider-service:dockerfile1
	
- Crear imagen de proyecto Integrator
	clean package -DskipTests
	docker build -t lxfutbol/integrator-service:dockerfile1 .	
			
- Correr imagen de proyecto	Integrator		
	docker container run -p 8081:8080 -d --network=lxfutbol-network --name=integratorService  -e RDS_HOSTNAME=mysql lxfutbol/integrator-service:dockerfile1
	
- crear imagen de proyecto eureka 
	clean package -DskipTests	
	
- Correr imagen proyecto eureka	
	docker container run -p 8761:8761 --network=lxfutbol-network --name=eurekadel lxfutbol/eureka-microservice:0.0.1-SNAPSHOT
