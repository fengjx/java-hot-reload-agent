maven_settings ?= ${HOME}/.m2/settings.xml
snapshotId=snapshots
releaseId=releases

### help:               Makefile帮助
.PHONY: help
help:
	@echo Makefile cmd:
	@echo
	@grep -E '^### [-A-Za-z0-9_]+:' Makefile | sed 's/###/   /'

### snapshot:           发布 snapshot 版本
.PHONY: deploy
snapshot:
	mvn --settings=${maven_settings} clean deploy -DskipTests -DrepositoryId=${snapshotId}

### release:            发布到 release 版本
release:
	mvn --settings=${maven_settings} clean deploy -DskipTests -DrepositoryId=${releaseId}



