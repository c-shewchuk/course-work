# ELEC 377 Lab 1
## Group Members: Curtis Schewchuck (14cms13) and Viraj Bangari (14vb16)


### Pseudocode
```
def isProcessDir(const struct dirent*d): bool
    for letter in d->name:
        if !is_digit(letter):
            return false
        else:
            return true

def stripString(char* str, int n) : char*
        str += n;
        while (isspace(*str)):
                str++;
        char *rval = str;
        while (!isspace(*str)):
                str++;

        *str = '\0';
        return rval;

def main:
   namelist = [] : array of *dirent

   int n = scandir("/proc", namelist, isProcessDir, NULL);
   print("The currently running processes are:\n");
   print("PID Name Status UID Cmdline\n");

   for dirent in namelist:
       pid = dirent->d_name : string
       file = open("/proc/" + pid + "/cmdline') : FILE
       cmdline = file.readline() : string

       file = open("/proc/" + pid + "/status')
       // The first line in /proc/{PID}/status is the name.
       // The second line is the status
       name = stripString(file.readline()) : string
       status = stripString(file.readline())

       printf("%s   %s   %s   %s   %s\n", pid, name, status, uid, cmdline);
```

### Algorithm explanation
The program first uses scandir in the "/proc" directory to get a list of directory entries.
The function isProcessDir is ran by scandir to filter the directory. Our function only returns
true if the directory's name only contains digits (it is a process).

Then, we get a list of dirent pointers. For each dirent, we get the PID (from the name) and open
the file "/proc/{PID}/cmdline". The file is read till the EOF using fgets and put in a buffer that
stores the name of the command line.

Then, the file "/proc/{PID}/status" is opened. Once the file is opened, the first line is read using
fgets to get the name. Since the status file contains information in the form of:

```
{KEY}:   {DATA} (newline or EOF terminated)
```

we strip the string by incrementing the pointer to the buffer n digits (where n is the number of characters
in the key) and to the first non-whitespace character. Then, the first non whitespace character in the
file is replace by a NULL terminating character. This process does not actually reduce the size
of the buffer, but it just changes where in the buffer the char pointer points to. This process is repeated
form the second line to get the status. fgets is run five more times to get the UID (since UIDs are the 7th
element in the file). Now that the PID, name, status, UID, status are stored in their respective buffer, the
data is printed to stdout.

### Testing

For testing, we created a test directory (proctest) and copied several processes that had been run on the machine, to our new directory. This way, we could control the input the program, and check for the expected output. To control which directory to look in, a boolean flag (isTesting) was used to determine which directory to search in. If the flag was set to 0 (false), the program will look in the "proc" directory, to check for real running processes. If the flag was set to 1 (true), the program will look in the "proctest" directory, and output the process data we put there. We were able to test the program with different process sets in the directory, adding new process data to the directory, to check if the program behaved as expected. 

### Sample Output
```
```
