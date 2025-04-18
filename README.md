# Handong Feed APP

**한동대학교의 소식을 한 번에 볼 수 있게 도와줄 Handong Feed 입니다.**

분산되어 있어 보기 어려운 한동대학교의 소식을 한 곳에서 쉽고 빠르게 볼 수 있는 서비스입니다.

사이트 주소: https://feed.handong.app \
관련 리포지토리 모음: https://github.com/orgs/handong-app/repositories?q=handong-feed

## 소개 영상 (뮤직비디오)

[![Youtube Thumbnail](https://github.com/user-attachments/assets/6028ccdb-4c9f-406b-baf9-16566bdf513e)](https://www.youtube.com/watch?v=xpXz107p8Gw)

**[2024 HGU SW Festival Grand Award (대상)] HandongFeed (한동피드) 소개영상**  
https://www.youtube.com/watch?v=xpXz107p8Gw

## 개발 진행상황 : 2025년 1학기 (2025년 1월~6월)

[🛠 프로젝트 페이지](https://github.com/orgs/handong-app/projects/2/views/1) 에서 자세히 확인하실 수 있습니다.

### 핵심 진행 중인 기능
- [ ] LLM 기반 뉴스레터 자동 생성 (기획 완료, 프로토타입 테스트 중)
- [ ] 학년별 인기글 자동 분류 시각화 (관리자 페이지 프로토타입 완료, 이용자 페이지 개발 중)

## 요구사항 포스터 (2024년 2학기)

![A1 (300 DPI)](https://github.com/user-attachments/assets/e997e40b-d013-4f96-aaef-9fb646ff3838)

## 개발 스택

- Spring Boot
- React
- MYSQL

## 개발환경 - 실행 방법

### IntelliJ 사용

gradle dependencies 설치 후에 `RealsprApplication.java` 실행.

### 직접 실행

```sh
./gradlew bootRun
```

### 프론트 실행 방법

[/src/main/front](https://github.com/handong-app/handong-feed-app/tree/main/src/main/front) 에서 `npm start` 이후 http://localhost:3000 바로 접속.

## 배포용 JAR 다운로드 방법

```sh
wget -O handong-feed-0.0.1-SNAPSHOT.jar https://github.com/handong-app/handong-feed-app/releases/latest/download/handong-feed-0.0.1-SNAPSHOT.jar
```

#2024-하계 웹캠프  
#2024-1 코딩클리닉 - 실전코딩/서버편  
#2024-2 캡스톤1
