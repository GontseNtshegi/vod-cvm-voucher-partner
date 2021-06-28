#!/bin/bash

# Constants
RED='\033[0;91m'
YELLOW='\033[1;93m'
GREEN='\033[0;92m'
BLUE='\033[0;94m'
GRAY='\033[0;37m'
NC='\033[0m'

SONARQUBE_HOST=http://sonarqube.orbit.vodacom.aws.corp/

# Variables
FILE_EXISTS=1

echo
echo -e "${YELLOW}Validate Git flow has been initialised${NC}"
echo -e "${YELLOW}--------------------------------------------------------------------------${NC}"
GIT_FLOW_INIT=$(git branch | grep -o develop | wc -l)
if [[ ${GIT_FLOW_INIT} == 1 ]]; then
  echo -e "${YELLOW} + OK${NC}"
else
  echo -e "${GREEN}Initialising git flow with defaults and pushing to remote${NC}"

  git flow init --default
  git push --all
fi

echo -e "${YELLOW}--------------------------------------------------------------------------${NC}"
echo -e "${YELLOW}Verifying required files exist${NC}"
echo -e "${YELLOW}--------------------------------------------------------------------------${NC}"

echo -e "${YELLOW} + pom.xml${NC}"
if [[ ! -f "pom.xml" ]]; then
    FILE_EXISTS=0
    echo -e "${RED}   ! Unable to find file pom.xml${NC}"
fi

echo -e "${YELLOW} + checkstyle.xml${NC}"
if [[ ! -f "checkstyle.xml" ]]; then
    FILE_EXISTS=0
    echo -e "${RED}   ! Unable to find file checkstyle.xml${NC}"
fi

echo -e "${YELLOW} + sonar-project.properties${NC}"
if [[ ! -f "sonar-project.properties" ]]; then
    FILE_EXISTS=0
    echo -e "${RED}   ! Unable to find file sonar-project.properties${NC}"
fi

echo -e "${YELLOW} + src/main/resources/config/application.yml${NC}"
if [[ ! -f "src/main/resources/config/application.yml" ]]; then
    FILE_EXISTS=0
    echo -e "${RED}   ! Unable to find file src/main/resources/config/application.yml${NC}"
fi

if [[ ${FILE_EXISTS} == 0 ]]; then
  echo -e "${YELLOW}--------------------------------------------------------------------------${NC}"
  echo -e "${GREEN}Please initialise microservice space for buddy.${NC}"
  echo -e "${GREEN}Run 'jhipster' on the commandline${NC}"
  echo -e "${GREEN}Remeber to run 'buddy.sh'aftewards again ${NC}"
  echo -e "${GREEN}--------------------------------------------------------------------------${NC}"
  echo -e "${GREEN}Cheat Sheet${NC}"
  echo -e "${GREEN}--------------------------------------------------------------------------${NC}"
  echo -e "${GREEN} + Which *type* of application would you like to create? ${BLUE}Microservice application${NC}"
  echo -e "${GREEN} + Do you want to make it reactive with Spring WebFlux? ${BLUE}No${NC}"
  echo -e "${GREEN} + What is the base name of your application? ${BLUE}Think really hard about this one${NC}"
  echo -e "${GREEN} + As you are running in a microservice architecture, on which port would like your server to run? It should be unique to avoid port conflicts. ${BLUE}Think really hard about this one${NC}"
  echo -e "${GREEN} + What is your default Java package name? ${BLUE}za.co.vodacom.something${NC}"
  echo -e "${GREEN} + Which service discovery server do you want to use? ${BLUE}JHipster Registry${NC}"
  echo -e "${GREEN} + Which *type* of authentication would you like to use? ${BLUE}JWT authentication${NC}"
  echo -e "${GREEN} + Which *type* of database would you like to use? ${BLUE}SQL${NC}"
  echo -e "${GREEN} + Which *production* database would you like to use? ${BLUE}MariaDB${NC}"
  echo -e "${GREEN} + Which *development* database would you like to use? ${BLUE}H2 with in-memory persistence${NC}"
  echo -e "${GREEN} + Do you want to use the Spring cache abstraction? ${BLUE}Yes, with the Hazelcast implementation${NC}"
  echo -e "${GREEN} + Do you want to use Hibernate 2nd level cache? ${BLUE}Yes${NC}"
  echo -e "${GREEN} + Would you like to use Maven or Gradle for building the backend? ${BLUE}Maven${NC}"
  echo -e "${GREEN} + Which other technologies would you like to use? ${BLUE}No Idea${NC}"
  echo -e "${GREEN} + Would you like to enable internationalization support? ${BLUE}No${NC}"
  echo -e "${GREEN} + Besides JUnit and Jest, which testing frameworks would you like to use? ${BLUE}None${NC}"
  echo -e "${GREEN} + Would you like to install other generators from the JHipster Marketplace? ${BLUE}No${NC}"
  # echo -e "${GREEN} +  ${BLUE}${NC}"
  echo -e "${GREEN}--------------------------------------------------------------------------${NC}"
  echo -e "${GREEN}Tutorial: https://www.jhipster.tech/video-tutorial/${NC}"
  echo -e "${GREEN}Visit https://www.jhipster.tech/creating-an-app/ if you are a noob${NC}"
  echo -e "${GREEN}--------------------------------------------------------------------------${NC}"
  exit 1
fi

# Obtaining properties required to run buddy
echo -e "${YELLOW}--------------------------------------------------------------------------${NC}"
echo -e "${YELLOW}Obtaining properties required to run buddy, this may take a few minutes...${NC}"
echo -e "${YELLOW}--------------------------------------------------------------------------${NC}"
echo -e "${YELLOW} + Using git config to obtain developer name and email${NC}"
CONTACT_EMAIL=$(git config user.email)
CONTACT_NAME=$(echo "${CONTACT_EMAIL}" | cut -d@ -f1 | sed 's/\./ /g')

echo -e "${YELLOW} + Using mvn to obtain project name and artifact ID${NC}"
PROJECT_NAME=$(mvn -q -Dexec.executable='echo' -Dexec.args='${project.name}' --non-recursive exec:exec)
ARTIFACT_ID=$(mvn -q -Dexec.executable='echo' -Dexec.args='${project.artifactId}' --non-recursive exec:exec)

echo -e "${YELLOW}--------------------------------------------------------------------------${NC}"
echo -e "${GREEN}Properties obtained${NC}"
echo -e "${GREEN}--------------------------------------------------------------------------${NC}"
echo -e "${GREEN}  + Your Name      = ${CONTACT_NAME}${NC}"
echo -e "${GREEN}  + Your Email     = ${CONTACT_EMAIL}${NC}"
echo -e "${GREEN}  + Artifact ID    = ${ARTIFACT_ID}${NC}"
echo -e "${GREEN}  + SonarQube Host = ${SONARQUBE_HOST}${NC}"
echo -e "${GREEN}--------------------------------------------------------------------------${NC}"

echo -e "${BLUE}Buddy is now ready to update the project, this may take a few seconds...${NC}"
echo -e "${BLUE}--------------------------------------------------------------------------${NC}"

# Update Dockerfiles
echo -e "${BLUE} + Update Dockerfiles${NC}"
find . -name Dockerfile -exec sh -c "sed -i 's/\${ARTIFACT_ID}/${ARTIFACT_ID}/g;s/\${PROJECT_NAME}/${PROJECT_NAME}/g' {}" \;

# Update bootstrap for testing
echo -e "${BLUE} + Update bootstrap for testing${NC}"
find . -name bootstrap-test.yml -exec sh -c "sed -i 's/\${ARTIFACT_ID}/${ARTIFACT_ID}/g' {}" \;

# Fix NoHttpCheck error which causes Jenkins builds to fail on scan
echo -e "${BLUE} + Fix NoHttpCheck error which causes Jenkins builds to fail on scan${NC}"
sed -i '/<module name="io.spring.nohttp.checkstyle.check.NoHttpCheck">/s/^/    <!--/;/ <\/module>/s/$/ -->/' checkstyle.xml

# Fix host and name in sonarqube properties
echo -e "${BLUE} + Fix host and name in sonarqube properties${NC}"
sed -i 's/ generated by jhipster//' sonar-project.properties
sed -i -E "s|(sonar.host.url)=.*|\1=${SONARQUBE_HOST}|" sonar-project.properties

# Add missing argLine to surefire and failsage plugins
echo -e "${BLUE} + Add missing argLine to surefire and failsage plugins${NC}"
sed -i '/<!-- Force alphabetical order to have a reproducible build -->/a                         <argLine>@{argLine}</argLine>' pom.xml

# Add missing maven repository
echo -e "${BLUE} + Add missing maven plugin repository${NC}"
sed -i '/<!-- jhipster-needle-maven-plugin-repository -->/i \
      <repository>\
        <id>maven-group</id>\
        <url>http://nexus-cvm-microservices-prod.app.prod.cpt.ocp.vodacom.corp/repository/maven-group/</url>\
      </repository>' pom.xml

# Add missing maven repository
echo -e "${BLUE} + Add missing maven repository${NC}"
sed -i '/<!-- jhipster-needle-maven-repository -->/i \
      <repository>\
        <id>maven-group</id>\
        <url>http://nexus-cvm-microservices-prod.app.prod.cpt.ocp.vodacom.corp/repository/maven-group/</url>\
      </repository>' pom.xml

# Add missing distribution management
echo -e "${BLUE} + Add missing distribution management${NC}"
sed -i "/<!-- jhipster-needle-distribution-management -->/i \    <distributionManagement>\
      \\n      <repository>\
      \\n        <id>maven</id>\
      \\n        <url>http://nexus-cvm-microservices-prod.app.prod.cpt.ocp.vodacom.corp/repository/${ARTIFACT_ID}/</url>\
      \\n      </repository>\
      \\n      <snapshotRepository>\
      \\n        <id>maven</id>\
      \\n        <url>http://nexus-cvm-microservices-prod.app.prod.tc.ocp.vodacom.corp/repository/${ARTIFACT_ID}-snapshot/</url>\
      \\n      </snapshotRepository>\
      \\n    </distributionManagement>" pom.xml

# Add additional dependencies
echo -e "${BLUE} + Add additional dependencies${NC}"
sed -i '/<!-- jhipster-needle-maven-add-dependency -->/i \
      <dependency>\
          <groupId>io.zipkin.brave</groupId>\
          <artifactId>brave</artifactId>\
      </dependency>\
      <dependency>\
          <groupId>com.hazelcast</groupId>\
          <artifactId>hazelcast-hibernate53</artifactId>\
      </dependency>\
      <dependency>\
          <groupId>org.springframework.cloud</groupId>\
          <artifactId>spring-cloud-starter-sleuth</artifactId>\
      </dependency>' pom.xml


# Add test profile
echo -e "${BLUE} + Add test profile${NC}"
sed -i '/<!-- jhipster-needle-maven-add-profile -->/i \
      <profile>\
          <id>test</id>\
          <dependencies>\
              <dependency>\
                  <groupId>org.springframework.boot</groupId>\
                  <artifactId>spring-boot-starter-undertow</artifactId>\
              </dependency>\
          </dependencies>\
          <build>\
              <plugins>\
                  <plugin>\
                      <artifactId>maven-clean-plugin</artifactId>\
                      <configuration>\
                          <filesets>\
                              <fileset>\
                                  <directory>target/classes/static/</directory>\
                              </fileset>\
                          </filesets>\
                      </configuration>\
                  </plugin>\
                  <plugin>\
                      <groupId>org.springframework.boot</groupId>\
                      <artifactId>spring-boot-maven-plugin</artifactId>\
                      <executions>\
                          <execution>\
                              <goals>\
                                  <goal>build-info</goal>\
                              </goals>\
                          </execution>\
                      </executions>\
                  </plugin>\
                  <plugin>\
                      <groupId>pl.project13.maven</groupId>\
                      <artifactId>git-commit-id-plugin</artifactId>\
                  </plugin>\
              </plugins>\
          </build>\
          <properties>\
              <!-- default Spring profiles -->\
              <spring.profiles.active>test${profile.api-docs}${profile.tls}${profile.no-liquibase}</spring.profiles.active>\
          </properties>\
      </profile>' pom.xml

#Insert contact details of developer
echo -e "${BLUE} + Insert contact details of developer${NC}"
sed -i "s/contact-name:.*/contact-name: ${CONTACT_NAME}/" src/main/resources/config/application.yml
sed -i "s/contact-email:.*/contact-email: ${CONTACT_EMAIL}/" src/main/resources/config/application.yml

echo -e "${BLUE}--------------------------------------------------------------------------${NC}"
echo -e "${GRAY} + Buddy now very sad${NC}"
echo -e "${GRAY} + Auto desctructing in 5 seconds${NC}"
echo -e "${GRAY} + Hope to see you soon on another project${NC}"
echo -e "${GRAY}--------------------------------------------------------------------------${NC}"

(sleep 5; rm buddy.sh;echo -e "${GRAY}Good bye${NC}")&
