# 동시성 문제

## 1. 구매 기능 구현
  - 구매 API를 개발, 시퀀스는 아래와 같으며 이러한 시퀀스를 통해 구매가 이루어지고 재고가 차감이 됩니다.

![OrderPlantUML](./images/OrderPlantUML.png)

## 2. 대규모 구매 테스트

### 1. 테스트 시나리오
  - 쇼핑몰에서 이벤트를 열어서 다수의 유저가 선착순으로 구매하는 상황을 테스트로 진행하였습니다.

### 2. nGrinder Setting       
![nGrinder-PurchaseTest](./images/nGrinder-PurchaseTest.png)

### 3. 테스트 결과 (동시성 문제 발생)
  - 구매 기록 : 1,654건의 주문이 기록되었습니다. (구매 성공 비율: 약 331%)
  - 재고 상태:  설정한 재고는 500개였으며, 남은 재고는 0개입니다. (판매된 재고 비율: 100%)

![nGrinder-PurchaseTest-Report](./images/nGrinder-PurchaseTest-Report.png)

```mysql
SELECT 
    (SELECT COUNT(*) FROM orders WHERE order_date > '2024-12-01 02:07:00') AS order_count,
    (SELECT stock FROM products WHERE id = 1) AS product_stock;
```
![nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock](./images/nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock.png)

### 4. 왜 이런 일이 일어났는가?          
  - 다수의 유저가 거의 동시에 데이터베이스에 접근하면서 같은 재고를 조회하고 재고 차감하는 과정에서 재고가 차감되기 전에 비슷하게 데이터베이스에 접근한 유저가 재고 조회를 하고 재고를 차감하면서 재고가 덮어씌워지는 일이 발생했습니다.

  ![PurchaseError](./images/Order-Concurrency-Error.png)

### 5. 개선 방안  
  1. 다수의 유저가 동시에 같은 재고를 조회하고 차감할 수 없게 순차적으로 한 번에 한 명만 재고를 조회 및 차감할 수 있게 만들면 동시성 문제가 발생하지 않을 것으로 예상됩니다.
  2. DB락 중에서 배타락을 사용하여 한 번에 하나의 트랜잭션만 재고를 조회하고 차감할 수 있도록 구현한다면 동시성 문제가 해결될 것으로 예상됩니다.

다음편 : DB락을 통한 동시성 제어
