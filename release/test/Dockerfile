FROM openjdk:8-jre-alpine
LABEL maintainer="Vodacom CVM"
LABEL service="Vodacom CVM ${artifactId}"
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
EXPOSE 8080
ADD VODACOMROOTCA.crt $JAVA_HOME/jre/lib/security/VODACOMROOTCA.crt
RUN \
   cd $JAVA_HOME/jre/lib/security \
   && keytool -import -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -alias VODACOMROOTCA -import -file VODACOMROOTCA.crt \
   && update-ca-certificates

ADD http://nexus-cvm-microservices-prod.app.prod.tc.ocp.vodacom.corp/repository/appdynamics/opt/apdynamics/AppServerAgentt.zip /opt/appdynamics/
COPY cp-${artifactId}.jar  /opt/appdynamics/cp-${artifactId}.jar
RUN chmod -R o+x /opt/appdynamics/
RUN apk add --no-cache curl
WORKDIR /opt/appdynamics/
RUN unzip AppServerAgentt.zip
RUN rm AppServerAgentt.zip
CMD java \
        ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom \
         -javaagent:/opt/appdynamics/ver20.4.0.29862/javaagent.jar \
         -Dappdynamics.controller.hostName=vodacomsa-dev.saas.appdynamics.com \
         -Dappdynamics.controller.port=443 \
         -Dappdynamics.controller.ssl.enabled=true \
         -Dappdynamics.agent.accountName=vodacomsa-dev \
         -Dappdynamics.agent.accountAccessKey=533775dd-231b-4e75-85ee-6f19d2ec62b3 \
         -Dappdynamics.agent.reuse.nodeName=true \
         -Dappdynamics.agent.auto.node.prefix=CP_${project.name}_TEST\
         -Dappdynamics.agent.applicationName=CVM_Openshift_TEST \
         -Dappdynamics.agent.tierName=CP_${project.name}_TEST \
         -Dappdynamics.bciengine.limit.package.qualifiers=7 \
        -jar -Djavax.net.ssl.trustStore=$JAVA_HOME/jre/lib/security/cacerts /opt/appdynamics/cp-${artifactId}.jar
