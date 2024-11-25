# API Documentation

이 문서는 상품 관련 API에 대한 설명을 제공합니다. 각 API 엔드포인트의 요청 메서드, URI, 기능을 정리하였습니다.

## 상품 API

### 엔드포인트

| HTTP 요청 메서드 | URI                     | 기능               |
|------------------|------------------------|--------------------|
| `GET`            | `/products`            | 상품 목록 조회     |
| `GET`            | `/products/{productId}` | 상품 상세 조회     |
| `POST`           | `/products`            | 상품 추가          |
| `PUT`            | `/products/{productId}` | 상품 수정          |
| `DELETE`         | `/products/{productId}` | 상품 삭제          |

## 사용 예시

### 상품 목록 조회

- **요청**:
  ```http
    GET /products
    응답:
    json
    
    
    {
        "products": [
            {
                "id": 1,
                "name": "Product 1",
                "price": 100,
                "stock": 20
            },
            {
                "id": 2,
                "name": "Product 2",
                "price": 150,
                "stock": 15
            }
        ]
    }
    상품 추가
    요청:
    
    http
    
    
    POST /products
    Content-Type: application/json
    
    {
        "name": "New Product",
        "price": 200,
        "stock": 10
    }
    응답:
    
    json
    
    
    {
        "id": 3,
        "name": "New Product",
        "price": 200,
        "stock": 10
    }
  ```
