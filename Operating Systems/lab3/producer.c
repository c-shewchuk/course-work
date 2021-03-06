/* 	ELEC 377,  Lab 3
 *
 *  producer.c
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

#define MYPID 1

int main (int argc, char *argv[]){

	// initialize the shared memory, load in the initial array's, spawn the worker
	// processes.

	key_t   key;
	struct shared    *memptr;
	int shmid;
	int c,stored;

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

	// Set the mutex's shared memory to memptr.
	mutexInit(memptr);

	// Increment the number of producers. This is a CS.
	getMutex(pid);
	memptr->numProcs++;
	releaseMutex(pid);

	char charRead = getchar();
	while (charRead != EOF)
	{
		bool wasAddedToBuffer = false;

		// Get the mutex for modifying the shared memory.
		getMutex(pid);

		// Add to buffer if its not full.
		if (memptr->count != BUFSIZE)
		{
			insert(memptr, charRead);
			wasAddedToBuffer = true;
		}
		releaseMutex(pid);

		// Check if the buffer was written. If not, try writing the
		// same character again.
		if (wasAddedToBuffer)
		{
			charRead = getchar();
		}
	}

	// Decrement the number of producers. This is a CS.
	getMutex(pid);
	memptr->numProcs--;
	releaseMutex(pid);

	return 0;
}


