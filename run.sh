#!/bin/bash
pushd backend_java && ./mvnw clean install -DkipTests && popd \
&& pushd frontend && yarn && docker-compose up --build