# Parameters

CLIENT = client
CLIENT2 = client2
SERVER = server

SOURCES_CLNT.c = src/client.c
SOURCES_CLNT.h = 
SOURCES_CLNT2.c = src/client2.c
SOURCES_CLNT2.h = 
SOURCES_SVC.c = src/server.c
SOURCES_SVC.h = 
SOURCES.x = rpccats.x

TARGETS_SVC.c = rpccats_svc.c rpccats_xdr.c 
TARGETS_CLNT.c = rpccats_clnt.c rpccats_xdr.c 
TARGETS = rpccats.h rpccats_xdr.c rpccats_clnt.c rpccats_svc.c

OBJECTS_CLNT = $(SOURCES_CLNT.c:%.c=%.o) $(TARGETS_CLNT.c:%.c=%.o)
OBJECTS_CLNT2 = $(SOURCES_CLNT2.c:%.c=%.o) $(TARGETS_CLNT.c:%.c=%.o)
OBJECTS_SVC = $(SOURCES_SVC.c:%.c=%.o) $(TARGETS_SVC.c:%.c=%.o)
# Compiler flags 

CFLAGS += -g 
LDLIBS += -lnsl
RPCGENFLAGS = 

# Targets 

all : $(CLIENT) $(CLIENT2) $(SERVER)

$(TARGETS) : $(SOURCES.x)
	rpcgen $(RPCGENFLAGS) $(SOURCES.x)
	
$(OBJECTS_CLNT) : $(SOURCES_CLNT.c) $(SOURCES_CLNT.h) $(TARGETS_CLNT.c) 

$(OBJECTS_CLNT2) : $(SOURCES_CLNT2.c) $(SOURCES_CLNT2.h) $(TARGETS_CLNT.c) 

$(OBJECTS_SVC) : $(SOURCES_SVC.c) $(SOURCES_SVC.h) $(TARGETS_SVC.c) 

$(CLIENT) : $(OBJECTS_CLNT) 
	$(LINK.c) -o $(CLIENT) $(OBJECTS_CLNT) $(LDLIBS) 

$(CLIENT2) : $(OBJECTS_CLNT2) 
	$(LINK.c) -o $(CLIENT2) $(OBJECTS_CLNT2) $(LDLIBS) 
	
$(SERVER) : $(OBJECTS_SVC) 
	$(LINK.c) -o $(SERVER) $(OBJECTS_SVC) $(LDLIBS)

 clean:
	 $(RM) core $(TARGETS) $(OBJECTS_CLNT) $(OBJECTS_CLNT2) $(OBJECTS_SVC) $(CLIENT) $(CLIENT2) $(SERVER)

