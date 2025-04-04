# 📅 일정 관리 API

1. 일정(캘린더)을 관리할 수 있는 RESTful API입니다.  
2. 일정을 생성, 조회, 수정, 삭제할 수 있습니다.
3. 유저를 생성, 조회, 수정, 삭제할 수 있습니다(로그인 인증). 

---

## 📌 API 명세서

### 📝 일정 관리 API

일정 테이블
| 기능 | 메서드 | URL | 요청 바디 | Response | 응답 (실패) |
|------|--------|------------|------------|------------|------------|
| **일정 생성** | `POST` | `/schedule` | `{ "title": string, "content": string, "userId": Long,  "password": string }` | `201 Created + { "id": Long,  "title": string,  "content": string,  "userId": Long,  "createdAt": string,  "updatedAt": string }` | `400 Bad Request` |
| **전체 일정 조회** | `GET` | `/schedule` | 없음 | `200 OK + [ { "id": Long,  "title": string,  "content": string,  "userId": Long,  "createdAt": string,  "updatedAt": string } ] (없으면 [])` | 없음 |
| **일정 단 건 조회** | `GET` | `/schedule/{id}` | 없음 | `200 OK + { "id": Long, "title": string, "content": string, "userId": Long, "createdAt": string, "updatedAt": string }` | `404 Not Found` |
| **선택 일정 수정** | `PATCH` | `/schedule/{id}` | `{ "title": string, "content": string, "userId": Long,  "password": string }` | `200 OK + { "id": Long, "title": string, "content": string, "userId": Long, "createdAt": string, "updatedAt": string }` | `401 Unauthorized` |
| **선택 일정 삭제** | `DELETE` | `/schedule/{id}` | `{ "password": string }` | `200 OK` | `404 Not Found` |

유저 테이블
| 기능 | 메서드 | URL | 요청 바디 | Response | 응답 (실패) |
|------|--------|------------|------------|------------|------------|
| **유저 생성** | `POST` | `/users` | `{ "username": string, "email": string, "password": string }` | `201 Created + { "id": Long, "username": string, "email": string, "createdAt": string, "updatedAt": string }` | `400 Bad Request` |
| **로그인** | `POST` | `/users/login` | `{ "usernaem":string, password": string }` | `200 OK` + {"message": "로그인 성공"} | `401 Unauthorized` + `{"timestamp": "2025-04-04T11:01:09.1456905","status": 401,"error": "이메일 또는 비밀번호가 일치하지 않습니다.","code": "C002", "message": "이메일 또는 비밀번호가 일치하지 않습니다.", "path": "/users/login"}` |
| **로그아웃** | `POST` | `/users/logout` | 없음 | `200 OK` + {"message": "로그아웃 성공"} | `401 Unauthorized` |
| **전체 유저 조회** | `GET` | `/users` | 없음 | `200 OK + [ { "id": Long, "username": string, "email": string, "createdAt": string, "updatedAt": string } ]` | 없음 |
| **선택 유저 조회** | `GET` | `/users/{id}` | 없음 | `200 OK + { "id": Long, "username": string, "email": string, "createdAt": string, "updatedAt": string }` | `404 Not Found` |
| **선택 유저 수정** | `PATCH` | `/users/{id}` | `{ "username": string, "email": string, "password": string }` | `200 OK + { "id": Long, "username": string, "email": string, "createdAt": string, "updatedAt": string }` | `401 Unauthorized` |
| **선택 유저 삭제** | `DELETE` | `/users/{id}` | `{ "password": string }` | `200 OK` | `404 Not Found` |


### 📝 ERD
![Image](https://github.com/user-attachments/assets/ac96ebb4-f6e8-45b4-8f01-012a8533ec6c)

User (1) <-----> Schedule (N)
→ 하나의 유저는 여러 개의 일정을 가질 수 있습니다 (1:N 관계).

Schedule.user_id는 User.id를 참조하는 외래키(Foreign Key)입니다

### 📝 테이블 생성에 필요한 query
```js
use schedule2;

CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(100) NOT NULL,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE schedule (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          title VARCHAR(100) NOT NULL,
                          content TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);



```
<br>

## 📌 프로젝트 기능 요구사항

### ✅ Lv 1. 일정 기능 (필수)

- 사용자는 **일정을 생성, 조회, 수정, 삭제(CRUD)** 할 수 있습니다.
- 일정은 다음과 같은 필드를 가집니다:
  - 작성 유저 (유저 ID로 연관관계 설정)
  - 할일 제목
  - 할일 내용
  - 작성일 (`@CreatedDate`)
  - 수정일 (`@LastModifiedDate`)
- 작성일, 수정일은 **JPA Auditing**을 통해 자동 관리됩니다.

---

### ✅ Lv 2. 유저 기능 (필수)

- 유저는 **회원 정보를 생성, 조회, 수정, 삭제(CRUD)** 할 수 있습니다.
- 유저는 다음과 같은 필드를 가집니다:
  - 유저명
  - 이메일
  - 작성일 (`@CreatedDate`)
  - 수정일 (`@LastModifiedDate`)
- **일정 엔티티와 유저 엔티티는 연관관계**를 갖습니다. (작성자 필드는 유저 ID로 대체)

---

### ✅ Lv 3. 회원가입 기능 (필수)

- 유저는 **회원가입**을 통해 계정을 생성할 수 있습니다.
- 유저 엔티티에 **비밀번호 필드**가 추가됩니다.
- 비밀번호는 **암호화는 선택적(도전 기능)**으로 구현합니다.

---

### ✅ Lv 4. 로그인 기능 (필수)

- 이메일과 비밀번호를 이용한 **로그인 기능**을 제공합니다.
- **Session 기반 인증**을 사용합니다.
- 인증 처리는 **Servlet Filter**를 통해 구현되며, 다음 경로는 필터에서 제외됩니다:
  - 회원가입(`/api/signup`)
  - 로그인(`/api/login`)
- 로그인 성공 시 쿠키와 세션을 활용해 인증 상태를 유지합니다.

---

## 🧰 기술 스택 및 키워드
- Java 17, Spring Boot
- JPA + Spring Data JPA
- JPA Auditing
- Spring MVC (Controller, Service, Repository 구조)
- Servlet Filter
- Session / Cookie 인증 처리

## 🚫 예외 처리

- 로그인 실패 시 (이메일/비밀번호 불일치 등) **HTTP 401 (Unauthorized)** 반환
- 예외 응답 형식 예시:
  ```json
  {
    "timestamp": "2025-03-26T14:26:45",
    "status": 400,
    "error": "BAD_REQUEST",
    "code": "C001",
    "message": "잘못된 입력값입니다",
    "path": "/api/login",
    "fieldErrors": [
      {
        "field": "username",
        "message": "아이디 입력은 필수입니다"
      }
    ]
  }
