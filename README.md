﻿# spartaScheduler_JPA
# 📅 일정 관리 프로그램

Spring JPA를 이용해 구현한 일정 관리 프로그램입니다.<br>
이메일과 비밀번호를 입력해 로그인 후 프로그램의 기능 이용이 가능합니다.<br>

- 동명이인은 가능하지만 이메일은 중복될 수 없습니다.<br> 
- 회원만 정보를 조회할 수 있습니다.<br>
- 본인의 것이 아닌 유저 정보, 일정, 댓글은 수정 및 삭제할 수 없습니다.<br><br>

## 🛠️ 기능

- 회원가입, 로그인, 로그아웃
- 유저 이름 및 비밀번호 변경, 삭제
- 일정 생성, 수정, 삭제
- 댓글 생성, 수정, 삭제
- 특정 유저가 작성한 댓글 조회
- 특정 일정에 작성된 댓글 조회
- 유저 아이디 또는 수정일에 따른 일정 조회

## 📌 API 명세서
[유저 API] https://documenter.getpostman.com/view/43241868/2sB2cU9NAg <br>
[일정 API] https://documenter.getpostman.com/view/43241868/2sB2cU9NAf <br>
[댓글 API] https://documenter.getpostman.com/view/43241868/2sB2cU9NAe <br>

## 📌 ERD
![ERD](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FLCYZc%2FbtsM8WiEKjo%2FQoIN8Yq37UcySsgMlJvkOK%2Fimg.png)

## 📌 요구사항
- 수정일과 작성일은 JPA Auditing으로 구현
### ✅ Lv 1. 일정 CRUD
`작성 유저명`, `할일 제목`, `할일 내용`, `작성일`, `수정일` (+ `작성한 댓글 개수` 추가)

### ✅ Lv 2. 유저 CRUD 
`유저명`, `이메일`, `비밀번호`, `작성일`, `수정일`, (+ `작성된 댓글 개수` 추가)
        
### ✅ Lv 3. 회원가입
`유저명`, `이메일`, `비밀번호`로 회원가입

### ✅ Lv 4. 로그인 (인증) `필수`

- `Cookie/Session`을 활용
- 필터를 활용한 인증 처리
- `@Configuration`을 통한 필터 등록

- 회원가입, 로그인 요청은 인증 처리에서 제외
- 이메일/비밀번호 불일치 시 HTTP Status 401 반환

### ✅ Lv 5. 다양한 예외처리 적용하기 

- `Validation`을 활용한 예외처리 적용
- 프로젝트 내 발생 가능한 예외사항 정의

### ✅ Lv 6. 비밀번호 암호화

`PasswordEncoder` 직접 구현 및 적용


### ✅ Lv 7. 댓글 CRUD

`댓글 내용`, `작성일`, `수정일`, `유저 고유 식별자`, `일정 고유 식별자`


### ✅ Lv 8. 일정 페이징 조회 `도전`

- Spring Data JPA를 활용한 페이징 구현
- 조회 항목: `할일 제목`, `할일 내용`, `댓글 개수`, `일정 작성일`, `일정 수정일`, `일정 작성 유저명`
- 기본 페이지 크기: 10
- 일정 `수정일` 기준으로 내림차순 정렬

## 📌 트러블 슈팅 및 회고
[블로그 링크] https://devhippo.tistory.com/100
