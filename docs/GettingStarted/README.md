# 소스코드 실행하기

## 1. 소스코드 다운받기

![Download-01](./iamges/GettingStared-Download-01.png)
Code > Download ZIP
다운로드 폴더에서 압축풀기

## 2. IntelliJ 로 실행하기
1. 인텔리J 실행하기
2. File > Open

## 3. Docker-compose 실행
services 실행
docker 가서 실행되었는지 확인하기

## 4. 서버 실행
ShoppingmallApplication 실행

## 5. ngrinder 접속
인터넷창에 localhost:7070 접속
ID: admin
PW: admin
admin 눌려서 Agent Management 로 Agent 확인하는 방법 << 요거는 고민
Script 들어가서 프로젝트 폴더 내의 ngrinder 폴더의 Purchase.groovy 파일 업로드하기
docker에서 ngridner-controller의 IP, localhost의 IP 입력하고 저장하기
Performance Test > Create Test 
Test Name: PurchaseTest

Test Configuration 에서
Agent: 1
아래 설정은 이전 스샷 따라서 적기
Save and Start 하면 테스트 가능

## 6. 결과 확인
6-1. localhost:8080 의 상품 목록에서 테스트 진행했던 상품 재고 확인
6-2. mariaDB 에서 orders 조회하기
6-3. ngrinder 결과 확인하기
6-4. redis 에서 해당 productSale 조회하기
