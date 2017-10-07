#include <stdio.h>
#include <stdlib.h>

#include "../rpccats.h"

CLIENT *clnt;

int do_connect(char *host) {
#ifndef	DEBUG
	clnt = clnt_create(host, RPCCATSPROG, RPCCATSVERS2, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror(host);
		return 1;
	}
#endif	/* DEBUG */

	return 0;
}

void disconnect() {
#ifndef	DEBUG
	clnt_destroy(clnt);
#endif	 /* DEBUG */
}

void do_add() {
	bool_t *result;
	Cat c;
	char sex[2];

	c.name = (char *) malloc(sizeof(char) * 33);

	printf("name: ");
	scanf("%32s", c.name);
	printf("age: ");
	scanf("%d", &(c.age));
	printf("sex: ");
	scanf("%1s", sex);
	c.sex = sex[0];

	result = add_2(&c, clnt);
	if (result == (bool_t *) NULL) {
		clnt_perror(clnt, "call failed");
	} else {
		printf("Cat added");
	}

	free(c.name);
}

void do_list() {
	int i;
	ListOfCats * result;
	char *list_arg;
	result = list_2((void*) &list_arg, clnt);
	if (result == (ListOfCats *) NULL) {
		clnt_perror(clnt, "call failed");
	} else {
		printf("Number of cats: %d\n", result->cats.cats_len);
		for (i = 0; i < result->cats.cats_len; ++i) {
			printf("%d] %s %d %c\n", i, result->cats.cats_val[i].name,
					result->cats.cats_val[i].age, result->cats.cats_val[i].sex);
		}
	}
}

void do_count() {
	int i;
	int * result;
	char *list_arg;
	result = count_2((void*) &list_arg, clnt);
	if (result == (int *) NULL) {
		clnt_perror(clnt, "call failed");
	} else {
		printf("Number of cats: %d\n", *result);
	}
}

int main(int argc, char *argv[]) {
	char *host;
	int done = 0;

	if (argc < 2) {
		printf("usage: %s server_host\n", argv[0]);
		exit(1);
	}
	host = argv[1];

	if (do_connect(host) != 0) {
		fprintf(stderr, "Error connecting to server. Exiting\n");
		return 1;
	}

	while (!done) {

		char cmd[32];

		printf("Menu:\n");
		printf("1] Add cat\n");
		printf("2] List cats\n");
		printf("3] Count\n");
		printf("5] Exit\n");

		scanf("%30s", cmd);

		switch (cmd[0]) {
		case '1':
			do_add();
			break;
		case '2':
			do_list();
			break;
		case '3':
			do_count();
			break;
		case '5':
			done = 1;
			break;
		default:
			printf("Unknown command '%c'\n", cmd[0]);
			break;
		}
	}

	return 0;
}
