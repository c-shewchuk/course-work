# Lab 3 Report 
## Curtis Shewchuk SN: 10189026
## Viraj Bangari   SN: 10186046

## Pseudo Code

### Producer.c
#### Producer.c is the portion of the program that enter characters into the shared memory. All of this code occurs in main.
        while (not at end of file):
                wasAddedToBuffer = false
                getTheKey()
                if (memorypointer->count != BUFFERSIZE):
                        insertCharactersToBuffer()
                        wasAddedToBuffer = true
                releaseKey()

                if(wasAddedToBuffer):
                        charRead = getChar()
        getTheKey()
        memptr->numProcs--
        releaseKey()
### Consumer.c
#### Consumer.c is the code that reads characters that have been placed in the shared memory by the producer. All of this code occurs in main.
        bool shouldLoop = true
        while(shouldLoop):
                getTheKey()
                if(memptr->count > 0):
                        read = get(memptr)
                        printf(read)
                else if (memptr->count == 0 and memptr->numProcs == 0):
                        shouldLoop = false
                releaseKey()
### Common.c
#### Common.c is the code that both the producer and consumer share. It contains the code that implements the "pass the key" algorithm.        `
        //test_and_set is an atomic instruction
        void getKeyx(processID):
                sharedptr->waiting[processID] = true
                key = true
                while(sharedptr->isWaiting[processID] and key):
                        key = test_and_set(&sharedptr->lock)
                sharedptr->isWaiting[processID] = false
        
        void releaseKey(processID):
                j = (processID + 1) modulus NUMPROCS
                while( j doesNotEqual processID and sharedptr->isWaiting[j] != false):
                        j = (j+1) modulus NUMPROCS
                if (processID == j):
                        sharedptr->lock = false
                else:
                        sharedptr->waiting[j] = false
## Algorithm
        
        The assignment is based on the "reader-writer" or "producer-consumer" problem. The algorithm relies on the producer to add some data to the shared memory, which the consumer can read from. 
    The producer is run, and is given data from stdin. This data could be a set of characters or a file. The producer needs to put this data in a shared variable so that the consumer can access it. Shared memory is involved, so we treat this as a critical section. We block the shared memory using the "pass the key" algorithm. To add to the shared memory, the producer asks for the key, increments the number of producers, and releases the key. The producer then reads in a character from stdin. The producer then asks for the key a second time, and when it gets the key, checks that the buffer is not full, and if not full, adds the character to the shared memory, setting the *wasAddedToBuffer* flag to true, and releases the key. Then, the character gets reads into memory, if the wasAddedToBuffer flag is true. The producer asks for the key a third time, and decrements the number of processes, and releases the key.

    The consumer works on an infinite loop, attempting to read characters from the shared memory, until the shared memory is empty. The consumer asks for the key, and when it gets the key, it checks to ensure their is data in the shared buffer. If so, read a character from the buffer and print that character out. If the buffer was empty and their are no producers running, set the loop flag to false. At the end of loop iteration, release the key.

The shared memory is guarded by the "pass the key" algorithm. It is implemented using an atomic test_and_set instruction. When either the producer or consumer wants the key, they call getMutex(short pid), giving in their process ID. The process ID is added to the queue, and the sets a waiting flag to true. The process sets a local "key" variable to true, and begins looping over a test and set instruction. The process loops until test and set, sets the key variable to false, and then the process that is waiting has it's "waiting" flag set to false. At this point, the process has the key to the shared memory. When the process wants to release the key, it calls releaseMutex(short pid), giving it's process ID again. The current process loops through all processes that are currently running, breaking out of the loop if one of them is waiting, or once it has looped through all processes. If it loops all the way through, (back to it's own process ID), the process sets the shared lock to false, indicating no one is in the critical section, and then releases the key. If it breaks the loop because another process is waiting, that process's waiting flag is set to false, and it now gets the key. This would break the loop for a waiting process that is currently executing getMutex().
