# 쇼핑몰 프로그램

## 프로젝트 개요
이 프로젝트는 동시성 테스트를 목적으로 개발된 쇼핑몰 시스템입니다. 다양한 사용자 요청을 동시에 처리하는 성능과 안정성을 검증하기 위해, 상품 목록 조회, 상품 조회, 상품 추가, 상품 수정, 상품 구매 기능을 포함하고 있습니다.

## 아키텍처
![basic](./cos/images/Architecture.PNG)

## ERD
![shoppingmal ERD](https://github.com/user-attachments/assets/7be8a57e-3490-4250-910b-4a36e88d8b16)

## 프로젝트 구조
/src
```bash
├── main
│   ├── java
│   │   └── com
│   │       └── lys
│   │           └── shoppingmall
│   │               ├── config          // 애플리케이션 설정 관련 클래스
│   │               ├── controller      // API 요청을 처리하는 컨트롤러
│   │               ├── exception       // 사용자 정의 예외 클래스
│   │               ├── mapper          // 데이터베이스 매핑 관련 클래스
│   │               ├── model           // 데이터 모델 클래스 (엔티티)
│   │               ├── service         // 비즈니스 로직을 처리하는 서비스 클래스
│   │               └── ShoppingmallApplication.java  // 애플리케이션의 진입점
│   ├── resources
│   │   ├── static                       // 정적 파일 (CSS, JS 등)
│   │   ├── templates                    // 템플릿 파일 (HTML 등)
│   │   └── application.yml              // 애플리케이션 설정 파일
├── test
│   ├── java
│   │   └── com
│   │       └── lys
│   │           └── shoppingmall
│   │               ├── controller      // 컨트롤러 테스트
│   │               ├── database        // 데이터베이스 관련 테스트
│   │               ├── service         // 서비스 테스트
│   │               └── ShoppingmallApplicationTest.java  // 애플리케이션 진입점 테스트
```

## 기능
- [**상품 목록 조회**](docs/api/products.md#상품-목록-조회): 모든 상품의 목록을 조회할 수 있습니다.
- **상품 조회**: 특정 상품의 상세 정보를 조회할 수 있습니다.
- [**상품 추가**](docs/api/products.md#상품-추가): 새로운 상품을 목록에 추가할 수 있습니다.
- [**상품 수정**](docs/api/products.md#상품-수정): 기존 상품의 정보를 수정할 수 있습니다.
- [**상품 구매**](docs/api/products.md#상품-구매): 상품을 구매하는 기능을 제공합니다.

## API 문서화
이 프로젝트에서는 Swagger를 사용하여 API를 문서화했습니다. Swagger UI를 통해 API를 시각적으로 확인하고 테스트할 수 있습니다. 각 API 엔드포인트에 대한 자세한 설명은 [docs/api/products.md](docs/api/products.md)에서 확인할 수 있습니다.

## Swagger UI 스크린샷
![Swagger UI](https://github.com/user-attachments/assets/5305589f-d37b-4f5c-9d22-0323d36acc68)

## 동시성 테스트
이 프로젝트는 동시성 테스트를 통해 여러 사용자가 동시에 상품을 구매할 때 시스템의 성능과 안정성을 검증합니다.
현재 구매 기능에 대한 동시성 테스트를 수행하였으며, 자세한 내용은 [동시성 테스트 문서](docs/concurrency-issue.md)에서 확인할 수 있습니다.
