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
      - 재고: 500개
      - nGrinder 설정:
          - Agent: 1
          - Vuser per agent: 50
          - Run Count: 100
       
![nGrinder-PurchaseTest](./images/nGrinder-PurchaseTest.png)

### 3. 테스트 결과
  - 구매 기록 : 1,654건의 주문이 기록되었습니다. (구매 성공 비율: 약 331%)
  - 재고 상태:  설정한 재고는 500개였으며, 남은 재고는 0개입니다. (판매된 재고 비율: 100%)

![nGrinder-PurchaseTest-Report](./images/nGrinder-PurchaseTest-Report.png)

```mysql
SELECT 
    (SELECT COUNT(*) FROM orders WHERE order_date > '2024-12-01 02:07:00') AS order_count,
    (SELECT stock FROM products WHERE id = 1) AS product_stock;
```
![nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock](./images/nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock.png)

### 4. 문제 분석
  - **동시성 문제 분석**:
      - 재고 초과 판매: 재고가 500개로 설정되었음에도 불구하고 1,654건의 주문이 발생하였고, 구매 성공 비율이 331%에 달했습니다. 이는 시스템이 재고를 초과하여 판매하는 동시성 문제를 겪었음을 나타냅니다.
      - 경쟁 상태: 여러 사용자가 동시에 구매 요청을 보낼 때, 재고를 확인하고 차감하는 과정에서 경합 상태가 발생할 수 있습니다. 이로 인해 여러 요청이 동시에 재고를 확인하고, 그 후 재고를 차감하는 과정에서 재고를 이미 소진되었음에도 불구하고 주문이 처리되는 상황이 발생했습니다.
        
  - **원인**:
      - 동시성 제어가 부족하여, 여러 사용자가 동시에 재고를 확인하고 차감하는 과정에서 경합 상태가 발생했습니다. 이로 인해 재고가 소진된 후에도 주문이 계속 처리되어, 실제 재고 수보다 많은 주문이 기록되었습니다.

다음편 : DB락을 통한 동시성 제어
