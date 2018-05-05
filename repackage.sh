#!/usr/bin/env bash
sed -i'' 's/eduardobachiega.baseapp/'"$1"'/g' app/build.gradle
appPath=$(echo -n $1 | sed 's/\./\//g')
mkdir -p app/src/main/java/$appPath
mv app/src/main/java/eduardobachiega/baseapp/* app/src/main/java/$appPath
rm -rf app/src/main/java/eduardobachiega
find . -name "*.java" -exec sed -i'' 's/eduardobachiega.baseapp/'"$1"'/g' {} +
find . -name "*.xml" -exec sed -i'' 's/eduardobachiega.baseapp/'"$1"'/g' {} +

