/* 	ELEC 377,  Lab 3
 *
 *  consumer.c 
 */

#include <stdio.h>
#include <strings.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdlib.h>
#include <errno.h>

#include "common.h"

#define FALSE 0
#define TRUE 1

#define MYPID 0

int main (int argc, char *argv[]){

	// initialize the shared memory, load in the initial array's, spawn the worker
	// processes.

	key_t   key;
	struct shared    *memptr;
	int shmid;

	if (argc != 2) {
		fprintf(stderr,"Usage: %s processid\n",argv[0]);
		exit(1);
	}
	// no error checking...
	short pid = atoi(argv[1]);
	if (pid < 0 || pid >= NUMPROCS){
		fprintf(stderr,"pid between 0 and %d\n",NUMPROCS-1);
		exit(1);
	}


	/*	 Shared memory init 	*/
	key = ftok(".", 'S');
	if((shmid = shmget(key, MEMSIZE, IPC_CREAT|0666)) == -1 ){
		if( (shmid = shmget(key, MEMSIZE, 0)) == -1){
			printf("Error allocating shared memory. \n");
			exit(1);
		}
	}

	// now map the region..
	if((int)(memptr = (struct shared *) shmat(shmid, 0, 0)) == -1){
		printf("Couldn't map the memory into our process space.\n");
		exit(1);
	}

	// Initialize the mutex
	mutexInit(memptr);

	bool shouldLoop = true;
	while (shouldLoop)
	{
		// Get the mutex for the CS.
		getMutex(pid);

		// Read from the buffer if it's not empty.
		if (memptr->count > 0)
		{
			char read = get(memptr);
			printf("%c", read);
		}
		else if (memptr->count == 0 && memptr->numProcs == 0)
		{
			// Stop running if the buffer emptry and the number 
			// of producers is 0.
			shouldLoop = false;
		}
		releaseMutex(pid);
	}


	return 0;
}


