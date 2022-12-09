#!/bin/sh

mvn archetype:generate \
  -DarchetypeGroupId=org.apache.camel.archetypes \
  -DarchetypeArtifactId=camel-archetype-spring-boot \
  -DarchetypeVersion=3.20.0-SNAPSHOT \
  -DgroupId=com.scoutingthestatline \
  -DartifactId=day$1
