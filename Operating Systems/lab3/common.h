/*
 *  common.h - Common definiton of the shared
 *     memory segment and prototypes for semaphore functions
 * 
 *  Created by Thomas Dean
 *  Copyright 2005 Queen's University.
 *
 */

#define MEMSIZE 200
#define BUFSIZE 5
#define NUMPROCS 5
#include <stdbool.h>

struct shared {
	// Variables needed for pass the key.
	int waiting[NUMPROCS];
	int lock;

	// Number of running processes.
	int numProcs;

	// The shared buffer.
	char buffer[BUFSIZE];

	// Index of oldest element in buffer.
	int start;

	// Index of newest element in buffer.
	int end;

	// Number of elements in buffer.
	int count;
};


void mutexInit(struct shared *memptr);
void getMutex(short pid);
void releaseMutex(short pid);
void insert(struct shared *memptr, char c);
char get(struct shared *memptr);
