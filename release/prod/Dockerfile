FROM openjdk:8-jre-alpine
LABEL maintainer="Vodacom CVM"
LABEL service="Vodacom CVM cp-voucher-partner"
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
EXPOSE 8080
ADD VODACOMROOTCA.crt $JAVA_HOME/jre/lib/security/VODACOMROOTCA.crt
ADD gdroot-g2.crt $JAVA_HOME/jre/lib/security/gdroot-g2.crt
ADD AppdymicsCA.crt $JAVA_HOME/jre/lib/security/AppdymicsCA.crt
RUN \
   cd $JAVA_HOME/jre/lib/security \
   && keytool -import -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -alias VODACOMROOTCA -import -file VODACOMROOTCA.crt \
   && keytool -import -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -alias gdroot-g2 -import -file gdroot-g2.crt \
   && keytool -import -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -alias AppdymicsCA -import -file AppdymicsCA.crt \
   && update-ca-certificates
ADD http://nexus-cvm-microservices-prod.app.prod.tc.ocp.vodacom.corp/repository/appdynamics/opt/apdynamics/AppServerAgentt.zip /opt/appdynamics/
COPY cp-voucher-partner.jar  /opt/appdynamics/cp-voucher-partner.jar
RUN chmod -R o+x /opt/appdynamics/
RUN apk add --no-cache curl
WORKDIR /opt/appdynamics/
RUN unzip AppServerAgentt.zip
RUN rm AppServerAgentt.zip
CMD java \
        ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom \
         -javaagent:/opt/appdynamics/ver20.4.0.29862/javaagent.jar \
         -Dappdynamics.controller.hostName=vodacomsa-prod.saas.appdynamics.com \
         -Dappdynamics.controller.port=443 \
         -Dappdynamics.controller.ssl.enabled=true \
         -Dappdynamics.agent.accountName=vodacomsa-prod \
         -Dappdynamics.agent.accountAccessKey=2cc54bf9-b51e-41d6-81f9-c556152e9f98 \
         -Dappdynamics.agent.reuse.nodeName=true \
         -Dappdynamics.agent.auto.node.prefix=CP_VoucherPartner\
         -Dappdynamics.agent.applicationName=CVM_OPENSHIFT\
         -Dappdynamics.agent.tierName=CP_VoucherPartner\
         -Dappdynamics.bciengine.limit.package.qualifiers=7 \
        -jar -Djavax.net.ssl.trustStore=$JAVA_HOME/jre/lib/security/cacerts /opt/appdynamics/cp-voucher-partner.jar
USER 1000
