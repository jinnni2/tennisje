# [Reserve Tennis Court]
 
## 클라우드 네이티브 아키텍처 (IaaS)
- 클라우드 아키텍처 구성, MSA 아키텍처 구성도


## 클라우드 네이티브 모델링 (Biz)
- 도메인분석 - 이벤트스토밍
<img width="1108" alt="스크린샷 2024-09-24 오전 12 18 26" src="https://github.com/user-attachments/assets/3ec332f7-e55b-4369-afac-483d13d19ef1">
1) 코트를 예약한다 <br>
1-1) 볼머신이 청소된다<br>
1-1-1) 예약가능한 코트수가 줄어든다<br>
1-1-2) 예약가능한 코트가 없으면 예약상태코드를 갱신한다<br>
2) 코트를 취소한다<br>
2-1) 예약가능한 코트수가 증가한다<br>


## 클라우드 네이티브 개발 MSA (Dev)
- 분산트랜잭션 - Saga<br>

1)코트정보를 생성한다<br>
  
http POST localhost:8084/courts courtName="serve" qty="2"<br>
![스크린샷 2024-09-24 오전 12 30 55](https://github.com/user-attachments/assets/dfa15d40-d07b-4ce4-9bc1-2b2a72211b76) <br>

  2)코트를 예약하면, 볼머신이 청소되고 사용가능한 코트가 줄어든다<br>
  
http POST localhost:8082/reserves userId="1" courtId="1" courtName="serve" <br>
![스크린샷 2024-09-24 오전 12 37 33](https://github.com/user-attachments/assets/310cf5e9-dcc8-4dd2-99b4-dc1a85ded72f) <br>

http get localhost:8083/machines<br>
![스크린샷 2024-09-24 오전 12 41 09](https://github.com/user-attachments/assets/dcb52070-8232-4501-8846-cfd1bddd8e61) <br>

http get localhost:8084/courts<br>
![스크린샷 2024-09-24 오전 12 43 09](https://github.com/user-attachments/assets/c3c5ce29-4907-4c80-b1b8-418a07bb14b7) <br>

  3)코트를 취소하면, 사용가능한 코트가 늘어난다<br>
  
http PATCH localhost:8082/reserves/1 status="end"<br>
![스크린샷 2024-09-24 오전 12 46 38](https://github.com/user-attachments/assets/7f7d312b-c231-4ffa-a420-af36bd5696f1) <br>

http get localhost:8084/courts<br>
![스크린샷 2024-09-24 오전 12 47 44](https://github.com/user-attachments/assets/865871e6-fb06-4ade-85c2-0331a04e5eb9) <br>

- 보상처리 - Compensation<br>

1)예약가능한 코트가 없는 경우, 예약이 실패처리된다<br>

http POST localhost:8082/reserves userId="2" courtId="1" courtName="serve" <br>
http POST localhost:8082/reserves userId="3" courtId="1" courtName="serve" <br>
http POST localhost:8082/reserves userId="4" courtId="1" courtName="serve" <br>
http get localhost:8082/reserves<br>
![스크린샷 2024-09-24 오전 12 52 15](https://github.com/user-attachments/assets/684970d3-a2bc-4ded-9fe2-f147c7f4ffdc) <br>

http get localhost:8082/reserves/4<br>
![스크린샷 2024-09-24 오전 12 53 58](https://github.com/user-attachments/assets/ea6e7c54-d779-4628-9e9d-d6d75c230502) <br>

- 단일 진입점 - Gateway<br>

1)8088 port로 예약 정보 얻기<br>

http get localhost:8088/reserves/1<br>
![스크린샷 2024-09-24 오전 1 27 58](https://github.com/user-attachments/assets/6d92410e-4195-4c96-80a1-f43e2e8c510d)<br>

2)8088 port로 볼머신 정보 얻기<br>

http get localhost:8088/machines/1<br>
![스크린샷 2024-09-24 오전 1 26 52](https://github.com/user-attachments/assets/02c505ca-72fc-4c84-a458-30437ed88f89)<br>

3)8088 port로 코트 정보 얻기<br>

http get localhost:8088/courts/1<br>
![스크린샷 2024-09-24 오전 1 26 20](https://github.com/user-attachments/assets/d9984735-54a8-4848-b142-7f879e01ca48)<br>

- 분산 데이터 프로젝션 - CQRS<br>

1)코트를 예약하면, myPage에 상태 "R"로 등록된다.<br>

http get localhost:8085/reservationLists<br>
![스크린샷 2024-09-24 오전 12 44 43](https://github.com/user-attachments/assets/785b1625-ff03-41f7-bd09-3498c2866a11) <br>
    
2)코트를 취소하면, myPage에 상태 "C"로 갱신된다.<br>
  
http get localhost:8085/reservationLists<br>
![스크린샷 2024-09-24 오전 12 49 01](https://github.com/user-attachments/assets/b0e88999-67a0-4995-8e35-4e28d9f7be96) <br>

## 클라우드 네이티브 운영 (Ops, PaaS)
- 클라우드 배포 - Container 운영<br>
1)docker build & push
```
mvn package -B -Dmaven.test.skip=true
docker build -t user12.azurecr.io/court:v1 .
docker push user12.azurecr.io/court:v1
az acr build --registry user12 --image user12.azurecr.io/court:v1 .
```
![image](https://github.com/user-attachments/assets/43ec1dfe-1b6b-4120-9695-732b68c15141)<br>

2)서비스 배포<br>
```
kubectl create deploy court --image user12.azurecr.io/court:v1 --namespace tennisje
kubectl expose deploy court --type=LoadBalancer --port=8080 --namespace tennisje
kubectl get service
```
![image](https://github.com/user-attachments/assets/e107cb0e-294e-4aaf-a89f-d726574ef615)<br>

3)Jenkins<br>
공용IP : 4.230.17.134 <br>


- 컨테이너 자동확장 (HPA) <br>
1)siege pod 생성
```
kubectl apply --namespace tennisje -f - <<EOF
apiVersion: v1
kind: Pod
metadata:
  name: siege
spec:
  containers:
  - name: siege
    image: apexacme/siege-nginx
EOF
pod/siege created
```
2)오토스케일링 설정 명령어 호출<br>
```
kubectl top pods --namespace tennisje
kubectl autoscale deployment court --cpu-percent=20 --min=1 --max=3 --namespace tennisje
kubectl get hpa --namespace tennisje
```
![image](https://github.com/user-attachments/assets/5fc4dd36-a91b-4163-8ad5-20b564af7dbf) <br>
![image](https://github.com/user-attachments/assets/f88723e4-984c-4806-a196-b5b72e72b7e9)

3)deployment.yaml 수정 후 다시배포<br>
```
      containers:
        - name: court
          image: user12.azurecr.io/court:v1
          ports:
            - containerPort: 8080
          resources:
            requests:
              cpu: "200m"       
```
```
kubectl apply -f court-deploy.yaml
```
![image](https://github.com/user-attachments/assets/4c2217a5-9568-4df2-870f-67c0402cf2eb)<br>

3)부하테스트<br>
```
kubectl exec --namespace tennisje -it siege -- /bin/bash
siege -c20 -t20S -v http://20.249.191.3:8080/courts
```
> pod 생성<br>
![image](https://github.com/user-attachments/assets/9bfeb530-b089-49ea-be89-8b4e1838f4d0)<br>
```
kubectl get hpa
```
![image](https://github.com/user-attachments/assets/0f4ad2c8-8b03-4aff-9e17-5077f9f00fe4)<br>
<br>

- 컨테이너로부터 환경분리 - ConfigMap/Secret<br>

1)Configmap 생성<br>
```
kubectl create configmap my-config --from-literal=key1=value1 --from-literal=key2=value2 --namespace tennisje
```
2)배포yaml 변경<br>
```
spec:
      containers:
        - name: court
          image: user12.azurecr.io/court:v1
          ports:
            - containerPort: 8080
          env:
          - name: PROFILE
            valueFrom:
              configMapKeyRef:
                name: my-config
                key: key1
```
3)pod 환경변수 확인<br>
```
kubectl exec --namespace tennisje pod/court-595dcffddf-t5w46 -- env
```
![image](https://github.com/user-attachments/assets/e44607c0-bcfc-43d8-8220-5d1ac5d5308f)<br>


- 클라우드 스토리지 확인 - PVC<br>
1)PVC생성<br>
![image](https://github.com/user-attachments/assets/6c98b517-4395-4c8d-ae6b-9689e20fed02)<br>
![image](https://github.com/user-attachments/assets/1bf9f07c-bb86-44f8-835d-874051fc430d) <br>

2)NFS볼륨을 가지는 서비스 배포<br>
```
kubectl apply --namespace tennisje -f - <<EOF
apiVersion: "apps/v1"
kind: "Deployment"
metadata: 
  name: court
  labels: 
    app: "court"
spec: 
  selector: 
    matchLabels: 
      app: "court"
  replicas: 1
  template: 
    metadata: 
      labels: 
        app: "court"
    spec: 
      containers: 
      - name: "court"
        image: "user12.azurecr.io/court:v1"
        ports: 
          - containerPort: 8080
        volumeMounts:
          - mountPath: "/mnt/data"
            name: volume
      volumes:
      - name: volume
        persistentVolumeClaim:
          claimName: azurefile  
EOF
```
3)마운트확인<br>
![image](https://github.com/user-attachments/assets/4f70c37d-011f-4a12-a37c-8b9fb8b206d3)<br>
> 다른 Pod에서 파일 확인<br>
![image](https://github.com/user-attachments/assets/12f5f190-91d5-4d91-b391-e226dfff0f95)<br>

- 셀프 힐링/무정지배포 - Liveness/Rediness Probe<br>
1)설정 없이 배포시 중단됨<br>
![image](https://github.com/user-attachments/assets/b9bd64cf-679a-4a2a-99a4-caee2b804176)<br>
2)Rediness Probe 설정<br>
![image](https://github.com/user-attachments/assets/d609a5dc-c312-43e8-97fd-01fea58d8492)<br>
3)중단없이 배포 완료<br>
![image](https://github.com/user-attachments/assets/04a7f8c6-6922-4bf5-90d3-555a49ceae23)<br>
...
![image](https://github.com/user-attachments/assets/96a914f3-2883-4c7f-8624-780471ef77ee)<br>
![image](https://github.com/user-attachments/assets/374236cc-6a7b-4669-86f2-fcbf8e9ecc10)<br>

- 서비스 메쉬 응용 - Mesh<br>
1)istio설치<br>
![image](https://github.com/user-attachments/assets/5c3370c6-72bf-46ae-a51c-a265a5879314)<br>
2)Istioctl kube-inject<br>
```
kubectl apply --namespace tennisje -f <(istioctl kube-inject -f court-deploy.yaml)
istioctl kube-inject -f court-deploy.yaml > output.yaml
kubectl label namespace tennisje istio-injection=enabled
```
3)injection 확인<br>
![image](https://github.com/user-attachments/assets/3bf9e2a2-e563-41d0-8c1e-5f50f379bdc3)<br>
![image](https://github.com/user-attachments/assets/15b85a9d-9124-4593-8922-d3e5d4f8f466)<br>

- 통합모니터링 -Loggregation/Monitoring
1)Grafana 서비스 open<br>
```
kubectl patch service/grafana -n istio-system -p '{"spec": {"type": "LoadBalancer"}}'
```
2)모니터링<br>
![image](https://github.com/user-attachments/assets/651849ff-fec9-4901-984c-aeb27f88c070)<br>

















































# 

## Model
www.msaez.io/#/182455792/storming/tennisje

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- reserve
- machine
- court
- mypage


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- reserve
```
 http :8088/reserves id="id" userId="userId" courtId="courtId" courtName="courtName" status="status" 
```
- machine
```
 http :8088/machines id="id" courtId="courtId" status="status" reserveId="reserveId" 
```
- court
```
 http :8088/courts id="id" courtName="courtName" qty="qty" reserveId="reserveId" 
```
- mypage
```
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

