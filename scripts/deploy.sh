#!/bin/bash

if [ ! -z "$TAG" ]
then
    echo "Tag found"
    if ( echo $TAG | egrep -i '^v[0-9]+\.[0-9]+\.[0-9]+')
    then
        VERSION_NUMBER=${TAG:1}
        echo "on a tag -> set pom.xml <version> to $VERSION_NUMBER"
        mvn --settings scripts/settings.xml org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=$VERSION_NUMBER 1>/dev/null 2>/dev/null -e -X
    else
        echo "Tag does not start with v: ${TAG} keep snapshot version in pom.xml"
    fi
    mvn clean deploy --settings scripts/settings.xml -B -U
else
    echo "not on a tag no deploy trigered"
fi
