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

- 보상처리 - Compensation
  1)예약가능한 코트가 없는 경우, 예약이 실패처리된다<br>

http POST localhost:8082/reserves userId="2" courtId="1" courtName="serve" <br>
http POST localhost:8082/reserves userId="3" courtId="1" courtName="serve" <br>
http POST localhost:8082/reserves userId="4" courtId="1" courtName="serve" <br>
http get localhost:8082/reserves<br>
![스크린샷 2024-09-24 오전 12 52 15](https://github.com/user-attachments/assets/684970d3-a2bc-4ded-9fe2-f147c7f4ffdc) <br>

  http get localhost:8082/reserves/4<br>
![스크린샷 2024-09-24 오전 12 53 58](https://github.com/user-attachments/assets/ea6e7c54-d779-4628-9e9d-d6d75c230502) <br>

- 단일 진입점 - Gateway
  ???
- 분산 데이터 프로젝션 - CQRS
  1)코트를 예약하면, myPage에 상태 "R"로 등록된다.<br>

http get localhost:8085/reservationLists<br>
![스크린샷 2024-09-24 오전 12 44 43](https://github.com/user-attachments/assets/785b1625-ff03-41f7-bd09-3498c2866a11) <br>
    
  2)코트를 취소하면, myPage에 상태 "C"로 갱신된다.<br>
  
http get localhost:8085/reservationLists<br>
![스크린샷 2024-09-24 오전 12 49 01](https://github.com/user-attachments/assets/b0e88999-67a0-4995-8e35-4e28d9f7be96) <br>





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

