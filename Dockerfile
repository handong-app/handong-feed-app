FROM amazoncorretto:17-alpine

ARG JAR_FILE

# 필요한 패키지 설치 및 infisical 설치
RUN apk add --no-cache bash curl \
    && curl -1sLf 'https://dl.cloudsmith.io/public/infisical/infisical-cli/setup.alpine.sh' | bash \
    && apk add infisical

# Timezone 설정 (이미지에 반영)
ENV TZ=Asia/Seoul

# jar 파일을 컨테이너에 복사
COPY ${JAR_FILE} /runme.jar

# 포트 노출
EXPOSE 8080

# infisical run을 통해 서비스 시작
CMD ["sh", "-c", "infisical run --projectId=${INFISICAL_PROJECT_ID} --domain=${INFISICAL_DOMAIN} --env=${INFISICAL_ENV} -- java -jar /runme.jar"]
