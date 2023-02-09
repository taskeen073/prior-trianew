app=restaurantt
tag=1.0

mvn clean package -DskipTests

eval $(minikube docker-env)

docker build -t ${app}:${tag} .