# ELEC 377 Lab 2
## Curtis Shewchuk & Viraj Bangari

### Pseudocode

```
#define nr_threads_LOC 0xc038b3a8

def my_read_proc(page, eof, filepos):
	if filepos == 0:
		add number of running processes to page
		add number of running threads to page (using nr_threads_LOC)
		add headers for running processes
		*eof = 1
		firstTask, lastLast = NULL
	else:
		if firstTask is NULL:
			firstTask = &initTask;
			lastTask = firstTask;

			while (pid of lastTask is 0)
				lastTask = lastTask->next;

			add pid, uid and nice of lastTask to page
			*eof = 1
		else:
			while (pid of lastTask is 0):
				if lastTask == firstTask:
					# We have gone around the processes loop once
					*eof = 0
					firstTask = NULL
					return 0

				lasTask = lasTask-next;

			add pid, uid and nice of lastTask to page
			*eof = 1
	
	return number of characters added to page


def init_module():
	create_proc_entry("lab2", NULL);

def cleanup_module():
	remove_proc_entry("lab2", NULL);
```

### Algorithm Explanation
#### Part 1: Listing the headers
The first time a process creates a syscall for our proc entry program (fpos = 0), we add the number of threads and processes running to the page pointer. The address where the number of threads was stored was found by grepping the system.map file in the boot directory.

#### Part 2: Listing process information
Since we do not have an actual file to read from, we create a virtual file. We keep track of where the "file pointer" of the virtual file is by returning only one proc entry at a time starting from init task.
In the case where a proc entry is invalid (pid == 0 since the kernel does not free proc entries), we keep skipping entries until a valid one is found. If we know that the entry we visited has the same address as init_task, then we know thathave visited every process in memory. At that point, we set the eof to 0 (indicating that we are the end of the virtual file) and reset the values of the proc entry pointers.
