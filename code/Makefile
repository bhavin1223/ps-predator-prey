# A simple Makefile for java. The default action is to compile every file in which the 
# current .class file is older than the current .java file. 'make clean' removes all
# .class files, and 'make all' recompiles the whole project.

#
# define compiler and flags

JC = javac
JFLAGS = -g

#
# make needs to be informed about java suffixes

.SUFFIXES: .java .class 

#
# make also needs a rule for generating .class file from .java

.java.class:
	$(JC) $(JFLAGS) $<

#
# by default, all of these .class files will be created from their .java source files

CLASSES = Animal.class GridAlg.class Hair.class InOut.class PredPrey.class Puma.class 

#
# default action is to only compile new source code

default: $(CLASSES)

#
# 'make all' will remove .class files then recompile everything

all: clean default

#
# 'make clean' will remove .class files

clean:
	$(RM) $(CLASSES)
