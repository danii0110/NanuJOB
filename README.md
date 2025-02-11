# 나누JOB 🤝
> 🏆 본 프로젝트는 2025 GDGoC New Year Hackathon: 백야톤 **우수상** 프로젝트입니다.<br/>
> 개발기간: 2025.01.18 ~ 2025.01.19
> 
![intro](/docs/images/intro.png)

<br><br>

# Overview
본 프로젝트는 **고령화와 청년 취업난 문제를 해결**하기 위해 개발되었습니다.  
시니어의 직무 경험을 AI가 정리하여 청년들에게 제공하는 **직무 경험 공유 플랫폼**입니다.

![프로젝트 배경](/docs/images/프로젝트%20배경.png)
![기획 의도](/docs/images/기획%20의도.png)
![해결 방법](/docs/images/해결%20방법.png)

<br><br>

# 주요 기능
### 1. 회원가입/로그인
시니어와 청년을 구분하여 맞춤형 서비스를 제공, 직종 선택 및 개인 정보 입력
![기능1](/docs/images/기능1.png)
![기능2](/docs/images/기능2.png)
![기능3](/docs/images/기능3.png)
### 2. 홈
사용자가 최근에 조회한 직무 경험을 다시 확인할 수 있도록 제공  
![기능4](/docs/images/기능4.png)
### 3. 채팅(시니어)
시니어가 생성형 AI와 대화하여 직무 관련 경험을 공유, AI가 대화 내용을 바탕으로 정보 추출
![기능5](/docs/images/기능5.png)
### 4. 정보 제공 및 직무 필터링(청년)
청년은 정보를 조회하고 필터링하여 원하는 직무 관련 정보 확인, 검색 기능 제공
![기능6](/docs/images/기능6.png)

<br><br>

# AI 활용
![AI 활용 및 구현 솔루션](/docs/images/AI%20활용%20및%20구현%20솔루션.png)

<br><br>

# 기대효과
![발전 가능성과 기대효과](/docs/images/발전%20가능성과%20기대효과.png)

<br><br>

# API 명세서

| API 종류  | HTTP Method | API Path                         | Description |
|-----------|------------|----------------------------------|-------------|
| **Auth** | POST       | `/api/login`                   | 사용자 로그인 |
|           | POST       | `/api/logout`                  | 사용자 로그아웃 |
| **Senior** | POST       | `/api/search-senior`           | 검색 키워드를 통한 시니어 정보 검색 |
|           | POST       | `/api/add-senior`              | 시니어 정보 생성 |
|           | GET        | `/api/get-all-senior`          | 모든 시니어 정보 조회 |
|           | GET        | `/api/filter-senior/{filter}`  | 직군을 통한 시니어 정보 검색 |
| **User** | POST       | `/api/add-user`               | 사용자 정보 생성 |
|           | GET        | `/api/find-user/{id}`          | 사용자 정보 조회 |
| **AI** | POST       | `/ai`                          | OpenAI API를 활용한 챗봇 구현 |

<br><br>

# SW Architecture
![아키텍처 구성도](/docs/images/아키텍처%20구성도.png)

<br><br>

# Stack
> Backend
* Java 21, Spring Boot 3.4.1
* Spring Security
* Firebase Admin SDK
* SpringDoc OpenAPI (Swagger)
> Frontend
* Flutter, Dart
> Etc
* GitHub, Notion, Figma