```

 ________  ___  ___  ________  ________  ________  ___  ________   ________  _____ ______   ________  ___       ___          
|\   ____\|\  \|\  \|\   __  \|\   __  \|\   __  \|\  \|\   ___  \|\   ____\|\   _ \  _   \|\   __  \|\  \     |\  \         
\ \  \___|\ \  \\\  \ \  \|\  \ \  \|\  \ \  \|\  \ \  \ \  \\ \  \ \  \___|\ \  \\\__\ \  \ \  \|\  \ \  \    \ \  \        
 \ \_____  \ \   __  \ \  \\\  \ \   ____\ \   ____\ \  \ \  \\ \  \ \  \  __\ \  \\|__| \  \ \   __  \ \  \    \ \  \       
  \|____|\  \ \  \ \  \ \  \\\  \ \  \___|\ \  \___|\ \  \ \  \\ \  \ \  \|\  \ \  \    \ \  \ \  \ \  \ \  \____\ \  \____  
    ____\_\  \ \__\ \__\ \_______\ \__\    \ \__\    \ \__\ \__\\ \__\ \_______\ \__\    \ \__\ \__\ \__\ \_______\ \_______\
   |\_________\|__|\|__|\|_______|\|__|     \|__|     \|__|\|__| \|__|\|_______|\|__|     \|__|\|__|\|__|\|_______|\|_______|
   \|_________|                                                                                                              
                                                                                                                             

```                                                                                                                     
                                                                                                                            

# 프로젝트 개요
이 프로젝트는 동시성 테스트를 목적으로 개발된 쇼핑몰 시스템입니다.<br> 다양한 사용자 요청을 동시에 처리하는 성능과 안정성을 검증하기 위해 상품 목록 조회, 상품 조회, 상품 추가, 상품 수정, 상품 구매 기능을 포함하고 있습니다.

# 동시성 테스트 선정 이유
 - 백엔드 개발에서 안정성과 효율적인 시스템 구축이 중요하다고 생각합니다.
 - 다수의 사용자들이 동시에 쇼핑몰에서 구매 활동을 진행할 때 시스템의 성능과 안정성을 평가하기 위해 동시성 테스트를 선택했습니다

# 아키텍처
![basic](./docs/images/Architecture.PNG)

# 사용하기
## [사용방법](docs/GettingStarted)
소스 코드를 다운받아 IntelliJ, Docker 를 활용하여 서버를 구성하고 nGrinder를 통하여 직접 부하 테스트를 할 수 있습니다.

# 개발일지
## [동시성 제어의 어려움 및 개선 방안](docs/concurrency/concurrency-issue.md)
동시성 제어의 어려움 및 개선 방안에 대해 설명합니다.

## [DB락을 통한 동시성 제어](docs/concurrency/DBLock.md)
DB락을 통한 동시성 제어 및 개선 방안에 대해 설명합니다.

## [레디스 재고 관리를 통한 동시성 제어](docs/concurrency/Redis-Inventory-Control.md)
레디스 재고 관리를 통한 동시성 제어 및 데이터 유실 대책에 대해 설명합니다.

## [코드 디자인](docs/refactoring)
코드 리팩토링의 목표와 과정에 대해 설명합니다.

## [API 설계 및 개발](docs/api/products.md)
상품 관련 API에 대한 설명을 제공합니다. 각 API 엔드포인트의 요청 메서드, URI, 기능을 정리하였습니다.
