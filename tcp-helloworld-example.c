#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <sys/sysinfo.h>

#define MY_PORT 1901
#define BUFF_SIZE 1024

void do_the_work(int client_sock) {
	int len;
	char buff[BUFF_SIZE];
	char html[] = "<html>"
			" <head>"
			"  <title>Hello World !!!</title>"
			" </head>"
			" <body>"
			"  Hello World !!!"
			" </body>"
			"</html>";
	read(client_sock, buff, BUFF_SIZE);

	len = sprintf(buff, "HTTP/1.1 200 OK\r\n"
			"Content-Type: text/html\r\n"
			"Content-Length: %d\r\n"
			"\r\n"
			"%s", strlen(html), html);
	write(client_sock, buff, len);
	sleep(100);
	close(client_sock);
}

int main(int argc, char **argv) {
	int sock_fd;
	int status;
	struct sockaddr_in my_addr;

	sock_fd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

	if (sock_fd == -1) {
		perror("socket()");
		return 1;
	}

	int enable = 1;
	setsockopt(sock_fd, SOL_SOCKET, SO_REUSEADDR, &enable, sizeof(int));

	my_addr.sin_family = AF_INET;
	my_addr.sin_port = htons(MY_PORT);
	my_addr.sin_addr.s_addr = INADDR_ANY;

	status = bind(sock_fd, (struct sockaddr *) &my_addr, sizeof(my_addr));

	if (status == -1) {
		perror("bind()");
		return 1;
	}

	status = listen(sock_fd, 10);

	if (status == -1) {
		perror("listen()");
		return 1;
	}

	while (1) {
		int status;
		int client_sock;
		struct sockaddr_in client_addr;
		socklen_t client_addr_len = sizeof(client_addr);

		printf("[%d] Waiting for clients\n", getpid());
		client_sock = accept(sock_fd, (struct sockaddr *) &client_addr,
				&client_addr_len);

		if (client_sock == -1) {
			perror("accept()");
			continue;
		}

		printf("Request from: %d.%d.%d.%d:%d\n",
				((unsigned char *) &client_addr.sin_addr.s_addr)[0],
				((unsigned char *) &client_addr.sin_addr.s_addr)[1],
				((unsigned char *) &client_addr.sin_addr.s_addr)[2],
				((unsigned char *) &client_addr.sin_addr.s_addr)[3],
				ntohs(client_addr.sin_port));

		printf("[%d] Starting child process\n", getpid());
		status = fork();
		if(status == 0) {
			printf("    [%d] fork()=%d\n", getpid(), status);
			printf("    [%d] Child is started\n", getpid());
			close(sock_fd);
			do_the_work(client_sock);
			printf("    [%d] Child id DONE\n", getpid());
			break;
		}
		else {
			printf("[%d] fork()=%d\n", getpid(), status);
		}
		printf("[%d] Done\n", getpid());
		close(client_sock);
	}

	return 0;
}
