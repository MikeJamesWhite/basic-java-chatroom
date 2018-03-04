# Makefile for basic-java-chatroom
# by Michael White
# 04/03/2018

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
	rm -r -f $(BIN)/*

runServer: all
	java $(RUN_OPTIONS) chatserver.ChatServer

runClient: all
	java $(RUN_OPTIONS) chatclient.ChatClient