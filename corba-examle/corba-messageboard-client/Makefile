all: corba-messageboard-client

corba-messageboard-client: main.o message_boardSK.o
	g++ -o $@ $^ -lomniORB4 -lomnithread

%.o: %.cpp
	g++ -c $<

%.o: %.cc
	g++ -c $<

clean:
	rm -rf *.o corba-messageboard-client
