maven_settings ?= ${HOME}/.m2/settings.xml
snapshotId=snapshots
releaseId=releases
HOME_DIR=${HOME}/.hot-reload-agent

default: package

install: package dist-to-home

dist-to-home:
	@echo 'HOME_DIR: ' ${HOME_DIR}
	@rm -rf ${HOME_DIR}/*.jar ${HOME_DIR}/*.sh ${HOME_DIR}/doc ${HOME_DIR}/*.md
	@mkdir -p ${HOME_DIR}
	unzip packaging/target/hot-reload-agent-bin.zip -d ${HOME_DIR}

package:
	@echo 'JAVA_HOME: ' ${JAVA_HOME}
	mvn --settings=${maven_settings} clean package -Dmaven.test.skip=true -P full
