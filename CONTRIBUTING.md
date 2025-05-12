# 🛠️ Handong Feed AI 프로젝트 컨벤션

이 문서는 Handong Feed AI 프로젝트에서 사용되는 **브랜치 네이밍**, **작업 방식**, **PR 작성 규칙**, **커밋 컨벤션**을 정의합니다.

---

## 📚 목차

- [📚 브랜치 네이밍 컨벤션](#-브랜치-네이밍-컨벤션)
- [🧩 작업 방식](#-작업-방식)
- [🚀 PR 작성 규칙](#-pr-작성-규칙)
- [✅ 커밋 메시지 컨벤션 (+ Gitmoji)](#-커밋-메시지-컨벤션--gitmoji)
  - [💥 BREAKING CHANGE: 하위 호환성 변경 주석 규칙](#-breaking-change-하위-호환성-변경-주석-규칙)
- [📎 기타 참고](#-기타-참고)

---

## 📚 브랜치 네이밍 컨벤션

```plaintext
<작업유형>/<이슈번호>-<간단설명>
```

### 예시
- `feature/23-llmservice-singleton-rate-limit`
- `feature/42-login-api`
- `fix/101-button-overlap`
- `chore/88-update-dependencies`

### 작업 유형

| 유형      | 설명                     |
|---------|--------------------------|
| `feature` | 새로운 기능 개발         |
| `fix`     | 버그 수정                |
| `chore`   | 빌드, 의존성 업데이트 등 |
| `refactor` | 리팩터링 (기능 변화 없음)|
| `docs`    | 문서 관련 수정           |
| `test`    | 테스트 코드 추가/수정    |
| `ci`      |  CI/CD 설정 및 워크플로우 관련 작업    |

---

## 🧩 작업 방식

1. 이슈 생성
2. 브랜치 네이밍 컨벤션에 따라 브랜치 생성
3. 구현 완료 후 PR 작성
4. coderabbitai 리뷰 확인 후 모든 요청을 resolve (Nitpick comments 되도록 모두 확인)
5. 리뷰어 중 1인 이상 지정하여 리뷰 요청
6. 리뷰어는 PR 문서, 변경 파일, 필요시 테스트 후 RC 또는 Approve
7. RC가 있다면 resolve 처리
8. Approve 완료되면 merge

---

## 🚀 PR 작성 규칙

- 포맷은 [`.github/pull_request_template.md`](./.github/pull_request_template.md)를 따릅니다.
- 관련 이슈는 다음과 같이 연결합니다

  ### 🔗 자동 이슈 연결
  - 해당 PR이 이슈를 **직접 해결할 경우**
    ```text
    resolves #이슈번호
    closes #이슈번호
    fixes #이슈번호
    ```
  
  - **Merge 시 해당 이슈가 자동으로 닫힙니다**
  
  ### 📎 관련 이슈만 언급할 경우
  ```text
  related to #이슈번호
  refs #이슈번호
  ```
  - Merge 시 해당 이슈가 자동으로 닫히지는 않습니다

  > 💥 중요한 구조 변경 또는 하위 호환성에 영향을 줄 경우, 커밋 메시지에 `BREAKING CHANGE:` 주석을 추가해 주세요.
---

## ✅ 커밋 메시지 컨벤션 (+ Gitmoji)

모든 커밋 메시지는 다음 형식을 따릅니다

```text
:emoji: type: message
```

### 커밋 타입

| 타입         | 설명                       |
|------------|----------------------------|
| `feat`     | 새로운 기능 추가             |
| `fix`      | 버그 수정                    |
| `chore`    | 설정, 빌드, 패키지 관련 작업 |
| `docs`     | 문서 수정 (README 등)        |
| `refactor` | 리팩터링 (동작 변화 없음)    |
| `test`     | 테스트 코드 추가/수정        |
| `ci`       | CI/CD, 배포, 워크플로우 관련 설정 변경|
| `wip`      | 아직 완료되지 않은 작업 커밋 (Work In Progress) |

### Gitmoji 예시

| 타입      | 이모지             | 예시 커밋 메시지                                |
|-----------|-----------------|-------------------------------------------------|
| `feat`    | ✨ `:sparkles:`  | `✨ feat: add intent detection to chatbot` |
| `fix`     | 🐛 `:bug:`      | `🐛 fix: resolve input parsing bug`        |
| `chore`   | 🔧 `:wrench:`   | `🔧 chore: update dependencies`            |
| `docs`    | 📝 `:memo:`     | `📝 docs: update project README`          |
| `refactor`| ♻️ `:recycle:`    | `♻️ refactor: restructure vector logic`    |
| `test`    | 🧪 `:test_tube:` | `🧪 test: add unit test for rag pipeline` |
| `ci`      | ⚙️ `:gear:`         | `⚙️ ci: add deployment step to GitHub Actions`|

### 기타 유용한 Gitmoji

| 목적        | 이모지  | 예시                                     |
|-----------|---------|------------------------------------------|
| 초기 커밋     | 🎉 `:tada:`      | `🎉 chore: initial project setup`             |
| UI 관련 작업  | 💄 `:lipstick:` | `💄 feat: apply new layout for chat`          |
| 성능 개선     | ⚡ `:zap:`       | `⚡ refactor: optimize FAISS query speed`     |
| 코드 제거     | 🔥 `:fire:`      | `🔥 chore: remove deprecated modules`         |
| 완료되지 않은 작업| 🚧 `:construction:`      | `🚧 wip: integrate Gemini stream API (not working yet)`         |
| 패키지 업그레이드 | ⬆️ `:arrow_up:` | `⬆️ chore: bump langchain version`           |
| 의존성 추가    | ➕ `:heavy_plus_sign:` | `➕ chore: add langchain and chromadb`       |
| 의존성 제거    | ➖ `:heavy_minus_sign:`| `➖ chore: remove unused nltk dependency`     |

### 작성 예시

```text
✨ feat: implement initial RAG-based chatbot API
🐛 fix: handle empty user input in retriever
♻️ refactor: extract document loader to service
📝 docs: add setup instructions to README
🔧 chore: configure logger and project structure
```

### 💥 BREAKING CHANGE: 하위 호환성 변경 주석 규칙

- `BREAKING CHANGE:`는 하위 호환성을 깨뜨리는 변경이 발생할 때, 커밋 메시지 본문 하단에 명시적으로 작성합니다.
- 이 주석은 이후 자동화 릴리즈 도구(예: semantic-release 등)에서 메이저 버전 상승 조건으로 인식됩니다.
#### 작성 예시
```text
⚙️ ci: migrate from GitHub Actions to CircleCI

BREAKING CHANGE: 기존 워크플로우 파일이 삭제되어 기존 배포가 중단될 수 있음
```
#### 📌 사용 시점
- API 응답 구조 변경 (기존 사용자 코드가 깨질 수 있음)
- 기존 라우트/엔드포인트 제거
- 필드 삭제, DB 스키마 변경 등
- 빌드/배포 구조 변경 (예: 기존 GitHub Actions 제거)

#### ⚠️ 주의사항
- 반드시 커밋 메시지의 본문 하단에 작성해야 합니다.
- BREAKING CHANGE: 키워드는 정확히 일치해야 힙니다.
- 릴리즈 자동화 또는 팀 공유 히스토리에 큰 의미를 갖는 주석입니다.
---

## 📎 기타 참고

- 커밋 메시지가 컨벤션에 맞지 않더라도 현재는 자동 reject 되지 않지만, 리뷰 중에 reject 될 수 있습니다.
- 브랜치 네이밍, PR 제목, 커밋 메시지 모두 통일된 스타일을 유지해 주세요.
