copy ..\build\libs\app-0.1.0.jar app.jar
docker rmi jc/urlex
docker build -t jc/urlex --build-arg JAR_FILE=app.jar .