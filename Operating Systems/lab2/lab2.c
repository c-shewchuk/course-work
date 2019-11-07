/*+
 * Module:  lab2.c
 *
 * Purpose: Skeleton solution to ELEC 377 Lab2.
 *
 -*/

#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/proc_fs.h>
#include <linux/sched.h>

static struct task_struct * firstTask, *lastTask;
#define nr_threads_LOC 0xc038b3a8

int my_read_proc(char * page, char **start, off_t fpos, int blen, int * eof, void * data){
    int numChars = 0;

    if (fpos == 0){
        int * nr_threads = (int*) nr_threads_LOC;
        numChars += sprintf(page,"Number of running processes: %d\n", nr_running);
        numChars += sprintf(page + numChars,"Number of running threads: %d\n", *nr_threads);
        numChars += sprintf(page + numChars, "PID  UID  NICE\n");
        *eof = 1;
        firstTask = NULL;
        lastTask = NULL;
    } else {
        if(firstTask == NULL) {
            firstTask = &init_task;
            lastTask = firstTask;

            while(lastTask->pid == 0){
                lastTask = lastTask->next_task;
            }

            numChars += sprintf(page, "%d\t%d\t%d\n", lastTask->pid, lastTask->uid, lastTask->nice);
            lastTask = lastTask->next_task;
            *eof = 1;
        }
        else
        {
            while(lastTask->pid == 0){
                if (lastTask == firstTask){
                    *eof = 0;
                    firstTask = NULL;
                    return 0;
                }
                lastTask = lastTask->next_task;
            }

            numChars += sprintf(page, "%d\t%d\t%d\n", lastTask->pid, lastTask->uid, lastTask->nice);
            lastTask = lastTask->next_task;
            *eof = 1;
        }
    }
    *start = page;
    return numChars;
}

int init_module(){
    struct proc_dir_entry * proc_entry;
    proc_entry = create_proc_entry("lab2",0444,NULL);
    if(proc_entry == NULL)
        return 1;
    proc_entry->read_proc = &my_read_proc;
    return 0;

}

void cleanup_module(){
    remove_proc_entry("lab2", NULL);
}
