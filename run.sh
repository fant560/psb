#!/bin/bash
pushd backend_java && ./mvnw clean install -DkipTests && pushd nodes \
&& ./mvnw clean install -DkipTests && popd && popd \
&& pushd frontend && yarn && docker-compose up --build