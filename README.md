## 프리온보딩 백엔드 인턴십 10월 선발 과제
* 작업 기간: 2023-10-07 ~ 2023-10-08

* 사용 기술: Java, Spring, Springboot, Spring Data JPA, Querydsl, H2 database

## 요약
* 선택사항 및 가산점요소를 포함한 모든 기능을 구현했습니다. (채용공고 등록, 수정, 삭제, 검색 목록 조회, 상세 페이지, 회사가 올린 다른 채용공고, 채용공고 지원)

* 단위 테스트로 컨트롤러, 서비스, 리포지토리 계층의 테스트를 분리했습니다.

* Git commit 메세지 컨벤션 규칙을 적용했습니다.

## 핵심 도메인 설계
<img src="https://github.com/mizuirohoshi7/wanted-pre-onboarding-backend/assets/142835195/9c6a128f-f16c-4673-89c5-594b2dce00f9"/>

## Api 명세서
* [명세서 링크](https://documenter.getpostman.com/view/26864500/2s9YJgU16D)

* Example Request 가장 오른쪽 화살표를 눌러서 성공, 실패 시의 응답 결과를 모두 볼 수 있습니다.

## 요구사항 분석 및 구현 과정
* 응답의 결과를 특정 엔티티의 DTO가 아닌 Result 객체로 통합하여 유연성을 늘렸습니다.
  Result 객체는 message, data 속성으로 이루어져 있습니다.
  message에는 성공 혹은 실패 메세지가, data에는 성공 시 요구된 데이터가 담기고 실패 시 null이 담겨 반환됩니다.

* 존재하지 않는 엔티티를 조회할 경우 DataNotFoundException이, 한 사용자가 중복된 채용공고 지원을 할 경우 DuplicateApplicationException이 발생합니다.
  예외처리를 각 컨트롤러에서 담당하면 불필요한 코드의 중복이 발생하여 @RestControllerAdvice 어노테이션을 소유한 GlobalExceptionHandler 객체로 예외를 처리했습니다.

* 기본적인 CRUD 기능은 JpaRepository가 제공하는 기능들을 사용했으나, 검색 목록 조회 기능의 경우 동적 쿼리가 필요해서 Querydsl을 사용했습니다.
  쿼리 파라미터로 제공되지 않은 검색 조건들은 모든 결과를 검색하도록 설계했습니다.

* 엔티티마다 목적에 맞는 DTO를 여럿 만들어 각각의 요구사항에 맞추어 속성을 분리했습니다. 주로 사용된 DTO의 종류는 다음과 같습니다.
  * Response: 엔티티 자체를 외부에 노출하지 않기 위해 요구되는 속성을 담은 응답 객체
  * SaveParam: 저장용 DTO, json 형식으로 주어지는 요청을 @RequestBody를 통해 객체화
  * UpdateParm: 수정용 DTO, json 형식으로 주어지는 요청을 @RequestBody를 통해 객체화
  * SearchCond: 검색용 DTO, 요청 파라미터형식으로 주어지는 데이터들을 객체화 (요청 파라미터의 snake case를 camel case로 변환하는 역할도 담당)

* TDD, BDD 중심으로 개발을 진행했습니다.
  단위 테스트를 먼저 작성한 후, 테스트를 만족시키는 코드를 작성했습니다.

## 아쉬운 점 및 개선점
* 컨트롤러, 서비스 계층 테스트에서 id와 관련된 테스트는 작성하지 못했습니다.
  id 생성은 DB에 위임하였고, 단위 테스트로 진행하여 두 계층에서는 DB에 접근하지 못했기 때문에 id를 제공받을 수 없었으나 적합한 해결 방법을 찾지 못했습니다.
  인턴십에 참여할 수 있게 된다면 시니어 개발자님에게 꼭 해결 방안을 여쭙고 싶습니다.
