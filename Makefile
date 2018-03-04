JAVAC=javac
BIN=./bin
SRC=./src
OPTIONS = -cp $(BIN) -d $(BIN) -sourcepath $(SRC)
RUN_OPTIONS= -cp $(BIN)

.PHONY: clean all

all: server client

server:
	$(JAVAC) $(OPTIONS) $(SRC)/chatserver/*.java

client:
	$(JAVAC) $(OPTIONS) $(SRC)/chatclient/*.java

clean:
	rm -f $(BIN)/*.class

runServer: all
	java $(RUN_OPTIONS) chatserver.ChatServer

runClient: all
	java $(RUN_OPTIONS) chatclient.ChatClient