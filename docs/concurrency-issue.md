# 동시성 문제

## 1. 구매 기능 구현
  - 구매 API를 개발, 시퀀스는 아래와 같습니다.<br>
![OrderPlantUML](./images/OrderPlantUML.png)
  - 이러한 시퀀스를 통해 구매가 이루어지고 재고가 차감이 됩니다.

## 2. 대규모 구매 테스트

### 1. 테스트 시나리오
  - 쇼핑몰에서 이벤트를 열어서 다수의 유저가 선착순으로 구매하는 상황을 테스트로 진행하였습니다.

### 2. 테스트 개요
  - **테스트 환경(nGrinder)**:
      - 재고: 5000개
      - nGrinder 설정:
          - Agent: 1
          - Vuser per agent: 50
          - Run Count: 1000
       
![nGrinder-PurchaseTest](./images/nGrinder-PurchaseTest.png)

### 3. 테스트 결과
  - 구매 기록 : 15,668건의 주문이 기록되었습니다. (구매 성공 비율: 약 1,567%)
  - 재고 상태:  설정한 재고는 5,000개였지만, 426개의 재고가 남아 있는 상황입니다.
      - 판매된 재고: 4,574개 (판매된 재고 비율: 약 91.48%)
      - 남은 재고: 426개 (남은 재고 비율: 약 8.52%)

![nGrinder-PurchaseTest-Report](./images/nGrinder-PurchaseTest-Report.png)

```mysql
SELECT 
    (SELECT COUNT(*) FROM orders WHERE order_date > '2024-12-01 02:07:00') AS order_count,
    (SELECT stock FROM products WHERE id = 1) AS product_stock;
```
![nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock](./images/nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock.png)

### 4. 문제 분석
  - **동시성 문제**: 여러 사용자가 동시에 주문을 시도하면서, 재고가 부족한 상태에서도 주문이 처리되는 문제가 발생하였습니다.
  - **원인**: 주문 처리 로직에서 재고를 확인하고 감소시키는 과정이 동시에 이루어지지 않아, 중복된 주문이 발생하였습니다.

다음편 : DB락을 통한 동시성 제어
