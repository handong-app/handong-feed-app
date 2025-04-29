FROM amazoncorretto:17-alpine

ARG JAR_FILE

# 필요한 패키지 설치 및 infisical 설치
RUN apk add --no-cache bash curl \
    && curl -1sLf 'https://dl.cloudsmith.io/public/infisical/infisical-cli/setup.alpine.sh' | bash \
    && apk add --no-cache infisical \
    && apk del curl bash \
    && rm -rf /var/cache/apk/*

# Timezone 설정 (이미지에 반영)
ENV TZ=Asia/Seoul

# jar 파일을 컨테이너에 복사
COPY ${JAR_FILE} /runme.jar

# 포트 노출
EXPOSE 8080

# 비특권 사용자 생성 및 디렉토리 권한 설정
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app

# 비특권 사용자로 전환
USER appuser

# 헬스체크 설정
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget -q --spider http://localhost:8080/api/health || exit 1

# infisical run을 통해 서비스 시작
CMD ["sh", "-c", "infisical run --projectId=${INFISICAL_PROJECT_ID} --domain=${INFISICAL_DOMAIN} --env=${INFISICAL_ENV} -- java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -jar /runme.jar"]
