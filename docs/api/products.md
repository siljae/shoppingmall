
## 상품 API
이 문서는 상품 관련 API에 대한 설명을 제공합니다. 각 API 엔드포인트의 요청 메서드, URI, 기능을 정리하였습니다.

### 엔드포인트

| HTTP 요청 메서드 | URI                     | 기능               |
|------------------|------------------------|--------------------|
| `GET`            | `/api/products`            | [상품 목록 조회](#상품-목록-조회)     |
| `POST`           | `/api/products`            | [상품 추가](#상품-추가)          |
| `PUT`            | `/api/products/{productId}` | [상품 수정](#상품-수정)          |
| `DELETE`         | `/api/products/{productId}` | [상품 삭제](#상품-삭제)          |
| `POST`           | `/api/products/{productId}/purchase` | [상품 구매](#상품-구매)   |
## 사용 예시

### 상품 목록 조회
- **요청**:
  ```javascript
    GET /api/products
  ```
- **응답**:
  ```javascript
    {
        "products": [
            {
                "id": 1,
                "name": "닭가슴살",
                "price": 100,
                "stock": 20
            },
            {
                "id": 2,
                "name": "목살",
                "price": 150,
                "stock": 15
            }
        ]
    }
  ```
  
### 상품 추가
- **요청**:
  ```javascript
    POST /api/products
  
    Content-Type: application/json
      {
        "name": "현미",
        "price": 200,
        "stock": 10
      }
  ```
- **응답**:
  ```javascript
    {
      "message": "상품 추가 완료."
    }
  ```
    
### 상품 수정
- **요청**:
  ```javascript
    PUT /api/product/{productId}
  
    Content-Type: application/json
    {
      "id": 2,
      "name": "엑스트라 버진 올리브유",
      "price": 250,
      "stock": 5
    }
  ```
- **응답**:
  ```javascript
  {
    "message": "상품 수정 완료."
  }
  
### 상품 삭제
- **요청**:
  ```javascript
   DELETE /api/products/{productId}
  ```    
- **응답**:
  ```javascript
    {
      "message": "상품 삭제 완료."
    }
  
### 상품 구매
- **요청**:
  ```javascript
    POST /api/products/{productId}/purchase
  
    Content-Type: application/json
    {
      "productId": 1  
    }
  ```
- **응답**:
  ```javascript
    {
      "message": "상품 구매 완료."
    }
