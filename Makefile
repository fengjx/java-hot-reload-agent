maven_settings ?= ${HOME}/.m2/settings.xml
snapshotId=snapshots
releaseId=releases
HOME_DIR=${HOME}/.hot-reload-agent

default: package

install: package dist-to-home

dist-to-home:
	@rm -rf ${HOME_DIR}/*.jar
	@mkdir -p ${HOME_DIR}
	cp hot-reload-core/target/hot-reload-core-jar-with-dependencies.jar ${HOME_DIR}/hot-reload-core.jar
	cp hot-reload-agent/target/hot-reload-agent-jar-with-dependencies.jar ${HOME_DIR}/hot-reload-agent.jar
	cp hot-reload-server/target/hot-reload-server-jar-with-dependencies.jar ${HOME_DIR}/hot-reload-server.jar
	cp hot-reload-watcher/target/hot-reload-watcher.jar ${HOME_DIR}/hot-reload-watcher.jar

package:
	mvn --settings=${maven_settings} clean package -Dmaven.test.skip=true
