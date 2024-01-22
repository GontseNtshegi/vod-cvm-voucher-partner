FROM eclipse-temurin:11-jre-alpine
LABEL maintainer="Vodacom CVM"
LABEL service="Vodacom CVM cp-voucher-partner"
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
EXPOSE 8080
ADD VODACOMROOTCA.crt $JAVA_HOME/lib/security/VODACOMROOTCA.crt
ADD gdroot-g2.crt $JAVA_HOME/lib/security/gdroot-g2.crt
ADD AppdymicsCA.crt $JAVA_HOME/lib/security/AppdymicsCA.crt
RUN \
   cd $JAVA_HOME/lib/security \
   && keytool -importcert -trustcacerts -cacerts -storepass changeit -noprompt -alias VODACOMROOTCA -file VODACOMROOTCA.crt \
   && keytool -importcert -trustcacerts -cacerts -storepass changeit -noprompt -alias gdroot-g2 -file gdroot-g2.crt \
   && keytool -importcert -trustcacerts -cacerts -storepass changeit -noprompt -alias AppdymicsCA -file AppdymicsCA.crt \
   && update-ca-certificates

ADD https://artifactory.orbit.prod.vodacom.co.za/artifactory/local-dists/appdynamics-agents/appagent-1.8-23.10.0.35234/AppServerAgent-1.8-23.10.0.35234.zip /opt/appdynamics/
COPY cp-voucher-partner.jar  /opt/appdynamics/cp-voucher-partner.jar
RUN chmod 777 -R /opt/appdynamics/
RUN apk add --no-cache curl
WORKDIR /opt/appdynamics/
RUN unzip AppServerAgent-1.8-23.10.0.35234.zip
RUN rm AppServerAgent-1.8-23.10.0.35234.zip
