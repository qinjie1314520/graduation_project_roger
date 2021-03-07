FROM registry.cn-chengdu.aliyuncs.com/qinjie/jdk1.8
ARG JAR_FILE
ARG EXPOSE_PORT
ARG EXPOSE_PORT
ARG FILE_FOLDER
ADD ${FILE_FOLDER}/target/${JAR_FILE} app.jar
EXPOSE ${EXPOSE_PORT}
ENTRYPOINT ["java","-jar","app.jar"]

