/* 	ELEC 377, Lab 3
 *
 * common.c contains routines to be used from both the  producer, and the  consumer
 *   Mutual Exclusion routines will be here
 */

#include <stdio.h>
#include <strings.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdlib.h>
#include <asm/system.h>


#include "common.h"

#define FALSE 0
#define TRUE 1

static struct shared *sharedptr = NULL;

int test_and_set(int * lock){
    return __cmpxchg(lock,0,1,4);
}


void mutexInit(struct shared *memptr){
        // initialize the only mutex once, from the producer... 
	sharedptr = memptr;
}

/*
 * Inserts a byte into the shared memory's buffer. This buffer is
 * treated as a circular array. This function is not threadsafe.
 */
void insert(struct shared *memptr, char c)
{
	memptr->buffer[memptr->end] = c;
	memptr->end = (memptr->end + 1) % BUFSIZE;
	memptr->count++;
}

/*
 * Removes a byte from the shared memory's buffer. This buffer is
 * treated as a circular array. This function is not threadsafe.
 */
char get(struct shared *memptr)
{
	char val = memptr->buffer[memptr->start];
	memptr->start = (memptr->start + 1) % BUFSIZE;
	memptr->count--;
	return val;
}


/*
 * Gets the mutex for the shared memory using pass the key.
 */
void getMutex(short pid){
	sharedptr->waiting[pid] = true;
	bool key = true;
	while(sharedptr->waiting[pid] && key){
		key = test_and_set(&sharedptr->lock);
	}
	sharedptr->waiting[pid] = false;
}

/*
 * Releases the mutex for the shared memory, giving the key to the process
 * that has a higher PID, looping over all active processes.
 */
void releaseMutex(short pid){
	int j = (pid +1) % NUMPROCS;
	while(j != pid && !sharedptr->waiting[j]){
		j = (j+1) % NUMPROCS;
	}
	if(pid == j) {
		sharedptr->lock = false;
	}
	else {
		sharedptr->waiting[j] = false;
	}
		
}

