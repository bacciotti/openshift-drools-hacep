#### Login into Openshift cluster
```sh
oc login -u system:admin
eval $(minishift oc-env) 
eval $(minishift docker-env)
oc project my-kafka-project
```
#### RBAC for configmap
```sh
oc create -f kubernetes/service-account.yaml
oc create -f kubernetes/role.yaml
oc create -f kubernetes/role-binding.yaml
```
#### Build Container and deploy
In the springboot module.

note:The name of the image "quickstarter/openshift-kie-springboot"
could be changed accordingly with the name used as image in the file kubernetes/deployment.yaml

Edit the Dockerfile and assign a value to the placeholder <id_group> and <id_user>
```sh
RUN groupadd -r app -g <id_group> && useradd -u <id_user> -r -g app -m -d /app -s /sbin/nologin -c "App user" app && chmod 755 /app
```
before to build the docker container  
and in the deployment.yaml
```yaml
securityContext:
        runAsUser: <id_user>
        runAsNonRoot: true
```
before to deploy.

To find the range of the ids run the command
```sh
oc describe project <your project name>
```
The output will be something like this with id ranges for groups and user
```sh
oc describe project my-kafka-project
Name:			my-kafka-project
Created:		7 days ago
Labels:			<none>
Annotations:		openshift.io/description=
			openshift.io/display-name=
			openshift.io/requester=system:admin
			openshift.io/sa.scc.mcs=s0:c13,c2
			openshift.io/sa.scc.supplemental-groups=1000160000/10000
			openshift.io/sa.scc.uid-range=1000160000/10000
Display Name:		<none>
Description:		<none>
Status:			Active
Node Selector:		<none>
Quota:			<none>
Resource limits:	<none>
```

Feel free to change base image in the Dockerfile if you need
```sh
docker build -t quickstarter/openshift-kie-springboot:latest .
docker images | grep openshift-kie
```

By default will be created under project called "My Project"
```sh
kubectl create -f kubernetes/deployment.yaml 
kubectl create -f kubernetes/service.yaml 
oc expose service  openshift-kie-springboot
```

 ```sh
 oc get route
 
  NAME           HOST/PORT                                      PATH      SERVICES       PORT      TERMINATION   WILDCARD
  openshift-kie   openshift-kie-my-kafka-project.192.168.99.109.nip.io           openshift-kie   http                    None
  ```
    
  Your address will be
  http://openshift-kie-springboot-my-kafka-project.192.168.99.109.nip.io/rest/env/all

  
#### Build Container and deploy with fabric8
In the springboot module
##### Build docker image
```sh
mvn package docker:build
```
##### Deploy
```sh
mvn fabric8:resource fabric8:deploy
```
  
### REST API
```sh
 http://<address>/rest/env/all
```