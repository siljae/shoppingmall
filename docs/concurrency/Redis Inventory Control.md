# 레디스 재고 관리를 통한 동시성 제어

## 1. 레디스를 통한 동시성 제어가 가능한 이유?
  - Redis(Remote Dictionary Server) key-value 기반의 인-메모리 데이터 저장소
  - 싱글 스레드이기 때문에 동시성 제어 가능
  - 다수의 유저가 거의 동시에 하나의 재고를 차감하는 것을 가능하게 함

## 2. DB 트랜잭션을 통한 재고 차감 vs 레디스를 통한 재고 차감
 - 아래의 표와 같이 레디스는 DB 트랜잭션에 비해 **경합 가능성이 있는 자원의 점유 시간**을 줄여 성능 향상을 기대할 수 있습니다.

| 항목| DB 트랜잭션| 레디스                                              |
|-----------------------|------------------------------------------------------|------------------------------------------------------|
| **구현 방식**         | - 재고 조회 시점부터 배타 락을 획득하고 재고 차감<br> - 트랜잭션이 끝날 때 락 반납 | - 레디스의 Atomic 연산으로 재고 차감 |
| **장점**              | - 보조 기억 장치에 데이터를 저장하기 때문에 데이터의 유실될 가능성이 매우 낮음 | - 기존의 트랜잭션 내에 있던 재고 차감을 레디스로 분리하여 자원 경합 시간을 줄여 성능이 높음 |
| **단점**        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       | - 경합 리소스에 대한 점유 시간이 길어서 동시 처리 성능이 떨어짐  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   | - 인-메모리 저장소이기 때문에 데이터의 유실될 가능성이 존재하며, 이에 대응하려 한다면 비즈니스 로직이 복잡해진다는 단점  |

## 3. DB 트랜잭션에서 레디스 방식으로 변경
![OrderPlantUML-Update-Redis-Inventory-Control](./images/OrderPlantUML-Update-Redis-Inventory-Control.png)

## 4. 구매 테스트
### 4-1. 테스트 시나리오
  - 쇼핑몰에서 이벤트를 열어서 다수의 유저가 선착순으로 구매하는 상황을 테스트로 진행하였습니다.

### 4.2 nGrinder Setting
![nGrinder-PurchaseTest](./images/nGrinder-PurchaseTest.png)

### 4-3. 테스트 결과
  - 구매 기록: 500건의 주문이 기록되었습니다. (구매 성공 비율: 100%)
  - 판매 수량: 500건의 판매 수량이 기록되었습니다. (판매 성공 비율: 100%)

![nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock-RedisLock](./images/nGrinder-PurchaseTest-Mysql-Orders-Count-And-Product-Stock-RedisLock.png)
![Redis-Product-Sale](./images/Redis-Product-Sale.png)
![nGrinder-PurchaseTest-RedisLock-Report](./images/nGrinder-PurchaseTest-RedisLock-Report.png)

### 4-4. 성능 상승
| Metric              | Before (TPS: 31.2) | After (TPS: 41.6) | Change             |
|---------------------|--------------------|--------------------|---------------------|
| Total Vusers        | 50                 | 50                 | -                   |
| TPS                 | 31.2               | 41.6               | **+10.4** (↑)       |
| Peak TPS            | 173.5              | 250.0              | **+76.5** (↑)       |
| Mean Test Time      | 321.85 MS          | 99.42 MS           | **-222.43 MS** (↓)  |
| Executed Tests      | 3,891              | 3,773              | **-118** (↓)        |
| Successful Tests    | 500                | 500                | -                   |
| Errors              | 3,391              | 3,273              | **-118** (↓)        |
| Run time            | 00:00:20           | 00:00:15           | -                   |

- 위의 표를 보면 전체적으로 성능이 약 33.33% 개선된 것을 확인할 수 있습니다.
    
## 5. 레디스에 의한 데이터 유실 대책 
  - 레디스는 **인-메모리 데이터 저장소**로, 메모리에 데이터를 저장하기 때문에 유실될 가능성이 있습니다.
  - 상품의 재고를 차감할 때 발생할 수 있는 데이터 유실 문제를 아래의 시퀀스와 같이 데이터를 검증하고, 유실된 데이터는 복구하는 대책을 통해 안정성을 확보하였습니다.

  - **fetchProductSaleOrInitialize 함수**: 레디스에서 데이터 취득
    - 레디스에 상품의 판매 수량을 조회합니다. 만약 레디스에 상품의 판매 수량이 존재하지 않으면 데이터 복구 절차를 진행합니다.<br>   
      ![OrderPlantUML-Update-fetchProductSaleOrInitialize](./images/OrderPlantUML-Update-fetchProductSaleOrInitialize.png)

  - **updateRedisProductSaleCount 함수**: 레디스에서 데이터 복구
    - DB의 order 테이블에서 해당 상품의 판매 수량을 집계한 뒤 레디스에 반영합니다.
    - 여러 서버에 의해 동시에 위의 과정이 진행되면 먼저 진행된 서버의 판매 수량 업데이트가 덮어씌워질 위험이 있기 때문에, 레디스 분산락을 통해 한 번에 하나의 서버에서만 접근이 가능하도록 했습니다.
    - 분산락을 획득했을 때 이미 다른 서버에 의해 레디스의 판매 수량이 업데이트된 경우 데이터 복구 과정을 중단하도록 하였습니다.<br>
      ![OrderPlantUML-Update-updateRedisProductSaleCount](./images/OrderPlantUML-Update-updateRedisProductSaleCount.png)
