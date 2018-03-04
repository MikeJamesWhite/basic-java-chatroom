JAVAC=javac
BIN=./bin
SRC=./src
OPTIONS = -cp $(BIN) -d $(BIN) -sourcepath $(SRC)
RUN_OPTIONS= -cp $(BIN)

.PHONY: all clean

all:
	$(JAVAC) $(OPTIONS) $(SRC)/*.java

%.class : %.java
	$(JAVAC) $(OPTIONS) $(SRC)/ $<

clean:
	rm -f $(BIN)/*.class

runServer: all
	java $(RUN_OPTIONS) ChatServer

runClient: all
	java $(RUN_OPTIONS) ChatClient