## [Reserve Tennis Court]

## 클라우드 네이티브 아키텍처 (IaaS)
### 1) 클라우드 아키텍처 구성, MSA 아키텍처 구성도


## 클라우드 네이티브 모델링 (Biz)
- 도메인분석 - 이벤트스토밍
<img width="1108" alt="스크린샷 2024-09-24 오전 12 18 26" src="https://github.com/user-attachments/assets/3ec332f7-e55b-4369-afac-483d13d19ef1">
1) 코트를 예약한다
2) 볼머신이 청소된다
3-1) 예약가능한 코트수가 줄어든다
3-2) 예약가능한 코트가 없으면 예약상태코드를 갱신한다
4) 코트를 취소한다
5) 예약가능한 코트수가 증가한다


## 클라우드 네이티브 개발 MSA (Dev)
### 1) 분산트랜잭션 - Saga
### 2) 보상처리 - Compensation
### 3) 단일 진입점 - Gateway
### 4) 분산 데이터 프로젝션 - CQRS





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

