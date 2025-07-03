
# 📌 프로젝트 개요

**뉴스 구독 웹 서비스 (NewsSubscribe)**  
사용자가 관심 키워드를 등록하면 관련 뉴스가 수집되어 주기적으로 이메일로 발송되는 개인화 뉴스 알림 시스템입니다.

---

## 🛠️ 기술 스택

| 분야 | 사용 기술 |
|------|-----------|
| 백엔드 | Spring Boot, Kotlin, JPA, MySQL |
| 프론트 | Thymeleaf |
| 배포 및 인프라 | Docker, Kubernetes (EKS) |
| CI/CD | GitHub Actions |
| 기타 | AWS SES, CloudWatch, ECR |

---

## 📁 프로젝트 구조

```
newsSubscribe/
├── src/main/java/com/newssubscribe/
│   ├── config/            # 설정 클래스
│   ├── controller/        # 사용자 요청 처리
│   ├── entity/            # JPA 엔티티
│   ├── repository/        # DB 접근
│   ├── service/           # 비즈니스 로직
│   └── util/              # 유틸 클래스
├── src/main/resources/
│   ├── templates/         # Thymeleaf HTML
│   └── static/            # 정적 리소스
├── k8s/                   # 쿠버네티스 배포 설정
├── .github/workflows/     # GitHub Actions CI/CD
├── Dockerfile             # Docker 이미지 정의
├── build.gradle.kts       # 빌드 설정
└── application.yml        # 환경 설정
```

---

## 🚀 실행 방법

### 1. 로컬 빌드 및 테스트

```bash
./gradlew build
java -jar build/libs/newsSubscribe-0.0.1-SNAPSHOT.jar
```

### 2. Docker 이미지 생성

```bash
docker build -t newssubscribe:latest .
```

### 3. Kubernetes 배포

```bash
kubectl apply -f k8s/
```

---

## 🔄 CI/CD 파이프라인 (GitHub Actions)

- PR 또는 main 브랜치 푸시 시 워크플로우 실행
- Docker 이미지 빌드 및 ECR 푸시
- Kubernetes에 배포된 애플리케이션을 Argo Rollouts로 블루/그린 방식 무중단 배포



## 📨 이메일 전송 기능

- AWS SES를 이용하여 인증 코드 및 뉴스 알림 발송
- `application.yml`에 메일 발송 관련 설정 포함

---

## 📦 주요 기능

- 🔐 이메일 인증 기반 로그인
- 📝 키워드 등록 및 조회
- 📰 뉴스 알림 시간 설정
