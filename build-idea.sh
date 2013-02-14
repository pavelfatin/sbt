#!/bin/sh

BASE=idea

COMPILER=$BASE/incremental-compiler.jar
SOURCES=$BASE/compiler-interface-sources.jar
INTERFACE=$BASE/sbt-interface.jar

if [ -d $BASE ]; then
	if ls $BASE/* &> /dev/null; then
		echo Cleaning the output directory...
		rm -r $BASE/*
	fi
else
	echo Creating an output directory...
	mkdir $BASE
fi

echo -e "Creating a JAR with SBT compiler implementation:\t$COMPILER ..."
jar -cf $COMPILER -C compile/target/classes .
jar -uf $COMPILER -C compile/api/target/classes .
jar -uf $COMPILER -C compile/inc/target/classes .
jar -uf $COMPILER -C compile/integration/target/classes .
jar -uf $COMPILER -C compile/persist/target/classes .

jar -uf $COMPILER -C util/classfile/target/classes .
jar -uf $COMPILER -C util/classpath/target/classes .
jar -uf $COMPILER -C util/collection/target/classes .
jar -uf $COMPILER -C util/control/target/classes .
jar -uf $COMPILER -C util/io/target/classes .
jar -uf $COMPILER -C util/log/target/classes .
jar -uf $COMPILER -C util/process/target/classes .
jar -uf $COMPILER -C util/relation/target/classes .

jar -uf $COMPILER -C ../sbinary/core/target/Scala-2.10/classes .

echo -e "Creating a JAR with SBT compiler interface sources:\t$SOURCES ..."
jar -cf $SOURCES -C compile/interface/src/main/scala/xsbt .

echo -e "Copying a JAR with SBT compiler interface API:\t\t$INTERFACE ..."
cp interface/target/interface-0.13.0-SNAPSHOT.jar $INTERFACE

echo "Done."
