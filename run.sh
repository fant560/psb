#!/bin/bash
pushd backend_java && ./mvnw clean install -DkipTests && popd && docker-compose up --build