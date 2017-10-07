#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#include "../rpccats.h"

#define MAX_CATS 128

struct {
	Cat cats[MAX_CATS];
	int count;
} DATABASE;

ListOfCats *
list_1_svc(void *argp, struct svc_req *rqstp)
{
	static ListOfCats result;
	int i;

	result.cats.cats_len = DATABASE.count;
	result.cats.cats_val = DATABASE.cats;

	return &result;
}

bool_t *
add_1_svc(Cat *argp, struct svc_req *rqstp)
{
	static bool_t  result;

	if(DATABASE.count < MAX_CATS) {
		DATABASE.cats[DATABASE.count].name = (char *) malloc(sizeof(char) * 32);
		strcpy(DATABASE.cats[DATABASE.count].name, argp->name);
		DATABASE.cats[DATABASE.count].age = argp->age;
		DATABASE.cats[DATABASE.count].sex = argp->sex;
		++DATABASE.count;
		result = 1;
	}
	else {
		result = 0;
	}

	return &result;
}

ListOfCats *
list_2_svc(void *argp, struct svc_req *rqstp)
{
	return list_1_svc(argp, rqstp);
}

bool_t *
add_2_svc(Cat *argp, struct svc_req *rqstp)
{
	return add_1_svc(argp, rqstp);
}

int *
count_2_svc(void *argp, struct svc_req *rqstp)
{
	return &(DATABASE.count);
}

ListOfCats *
list_3_svc(void *argp, struct svc_req *rqstp)
{
	return list_2_svc(argp, rqstp);
}

bool_t *
add_3_svc(Cat *argp, struct svc_req *rqstp)
{
	return add_2_svc(argp, rqstp);
}

int *
count_3_svc(void *argp, struct svc_req *rqstp)
{
	return count_2_svc(argp, rqstp);
}

void *
register_3_svc(void *argp, struct svc_req *rqstp)
{
	static void * result = (void *) "";

	printf("register_3_svc() called\n");

	printf("client: %s\n", inet_ntoa(rqstp->rq_xprt->xp_raddr.sin_addr));

	return result;
}
