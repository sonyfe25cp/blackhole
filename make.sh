#!/bin/sh
#HOMEPATH=/usr/local/blackhole
HOMEPATH=/tmp/blackhole

mvn clean package -Dmaven.test.skip=true

mkdir -p $HOMEPATH
mkdir -p $HOMEPATH/lib
if [ ! -d $HOMEPATH/config ]
then
mkdir -p $HOMEPATH/config
cp ./server/config/* $HOMEPATH/config/
fi
cp ./server/blackhole.sh $HOMEPATH
cp ./server/target/blackhole*.jar $HOMEPATH/blackhole.jar
rsync -avz --delete ./server/target/lib/ $HOMEPATH/lib/

cd $HOMEPATH
