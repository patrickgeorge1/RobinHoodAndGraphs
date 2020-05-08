JFLAGS = -g
JC = javac

.SUFFIXES: .java .class


build:
	javac -g src/P1.java
	javac -g src/P2.java
	javac -g src/P3.java

run-p1:
	java src.P1

run-p2:
	java src.P2

run-p3:
	java src.P3

default: classes

classes: $(CLASSES:.java=.class)


clean:
	$(RM) src/*.class
