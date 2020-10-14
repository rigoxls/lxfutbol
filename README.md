#Ejecución con docker compose
1) Instalar docker 
2) clonar proyectos https://github.com/rigoxls/lxfutbol
3) Abrir un cmd e ir a las carpetas de los proyectos y hacer lo siguiente 
    - \eurekaService
        - mvn clean package -DskipTests        
    - \zuulService
        - mvn clean package -DskipTests    
    - \providerService
        - mvn clean package -DskipTests    
        - docker build -t lxfutbol/provider-service:dockerfile1 . 
    - \integratorService
        - mvn clean package -DskipTests    
        - docker build -t lxfutbol/integrator-service:dockerfile1 .  
4) Ir a la carpeta base de los proyectos \lxfutbol
        - docker-compose up
        - Esperar unos 4 minutos hasta que levanten las dependencias
5) Verificar que los proyectos se registren en eureka 
        - http://192.168.99.100:8761/
6) Probar comunicación 
        - http://192.168.99.100:8080/provider/get/1 // da null
7) Ingresar a la db y crear registros 


#Pasos basicos despliegue manual
-------------------------------------------
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
	
- Crear imagen de proyecto hoteleria
	clean package -DskipTests
	docker build -t lxfutbol/lodge-service:dockerfile1 .	

- Crear imagen de proyecto transporte
	clean package -DskipTests
	docker build -t lxfutbol/transport-service:dockerfile1 .
	
- Crear imagen de proyecto espectaculo
	clean package -DskipTests
	docker build -t lxfutbol/spectacle-service:dockerfile1 .	
	
- Crear imagen de proyecto transform-json
	clean package -DskipTests
	docker build -t lxfutbol/transform-json-service:dockerfile1 .	
	
- Crear imagen de proyecto transform-soap
	clean package -DskipTests
	docker build -t lxfutbol/transform-soap-service:dockerfile1 .		
			
- Correr imagen de proyecto	Integrator		
	docker container run -p 8081:8080 -d --network=lxfutbol-network --name=integratorService  -e RDS_HOSTNAME=mysql lxfutbol/integrator-service:dockerfile1
	
- crear imagen de proyecto eureka 
	clean package -DskipTests	
	
- Correr imagen proyecto eureka	
	docker container run -p 8761:8761 --network=lxfutbol-network --name=eurekadel lxfutbol/eureka-microservice:0.0.1-SNAPSHOT