# Lab 4 Documentation
### Curtis Shewchuk SN: 10189026
### Viraj Bangari   SN: 10186046

## Part 1 - PSCLONE

### Pseudo Code
```
print 'PID USER RSS COMMAND' #padding with appropriate spaces to look like ps'
files = find('/proc/', regex, "\/proc\/[0-9][0-9]\*"])
for (proc in files):
        if(proc exists):
                pid = trim(proc, /proc/)
                uid = searchInProcForUID(proc)
                RSS = searchInProcForRSS(proc)
                cmdline = searchInProcForCMDLINE(proc)
        if(isEmpty(RSS)):
                RSS = 0 
        if(cmdline == 0):
                cmdline = searchInProcForStatusName(proc)
        print((pid, uid, RSS, cmdline).format(%4s\t%4s\t%3s\t%s\n))
```
### Algorithm Explanation
Psclone is supposed to print information in the same format that ps -eo pid,user,rss,args' would. First we print the top line, 'PID USER RSS COMMAND' and pad it with the appropriate spaces to keep the columns straight (see the script for the spacing used).Next, we call find on the /proc/ directory, and use the regular expression shown to match the running processes. The regex used is \/proc\/[0-9][0-9]\* which matches all files starting in the /proc/ directory, and contain at least 1 numbers between 0-9, and anything after. Then, the script loops through all the files. First, we trim off the /proc/ from the PID and set that equal to the 'pid variable. Next we call 'stat' on the current process, and pipe the output to 'grep' which matches the 'Uid:" and anything after that. This is then piped into 'sed' which strips off the 'Uid:' and replaces it with an empty space, leaving just the uid with no prefix. Next, the RSS is done in a similar fashion, except we use 'cat' on the '/proc/'currentprocess'/status directory, and pipe that to grep. For the cmdline, the 'cmdline' files are prefixed with a '0x'. The cat output of cat 'currentproc' is piped to 'sed' and the '0x' is stripped. Next we check if the RSS variable is empty (meaning that the RSS directory was not visible), and if it is empty, we set it to zero. For the commandline, we check if the value is zero, and if it is zero, we replace the cmdline variable with the process name. Finally, we print out the pid, uid, RSS and cmdline variables with the proper padding.  
## Part 2 - SOURCECODE

### Pseudo Code
```
if(input\_args ==0):
        exit 

dirname = input\_args

main\_pattern = "int \*main \*(.\*)"
module\_pattern = "int \*init\_module \*(.\*)"

main\_matches = ""
module\_matches = ""
other\_matches = ""

for f in (all .c files):
        if((# of main\_patterns in f ) > 0):
               num_printf = (find ('printf', f))
               num_fprintf = (find ('fprintf', f))
               main_matches = main_matches + f.name +:(num_printf,num_fprintf)
    elif ((# of module_patterns in f) > 0):
        num_printf =  (find ('printf', f)
        num_fprintf = (find ('fprintf, f)
        num_printk = (find ('printk',f )
        module_matches = module_matches + f.name: + (num_printf,num_fprintf, num_printk) 
    else:
        other_matches = other_matches + f.name

print ' Main Files:'
print ( main\_matches).format(" " "\n")

print 'Module Matches: '
print (module\_matches).format(" " "\n")

print 'Other Matches: '
print (other\_matches).format(" " "\n")
```
### Alogorithm Explantion
'source\_code' is a script that searches through a given directory and counts the number of 'printf', 'fprintf' and 'printk' statements in '.c' files. The script categorizes the statements by '.c' files that contain a main as a 'main file' and those contain the line 'init\_module' as a kernel module. First, the script checks the number of inputs given to it, and if that is zero, the script exits. If it gets past this, the input is given to the script as 'dirname'. We then define the regular expressions to match a main file, and a kernel module. Once done, we loop through every file that ends in '.c'  in the directory that is supplied. If main\_pattern gets a match, we count to number of printf statements using 'grep -c "\[^f\]printf", which ensures that it only matches 'printf' and not 'fprintf', We then count the number of 'fprintf' matches using 'grep -c "fprintf". We then concatenate these together into the main\_matches variable. If there are no main pattern matches, but there are 'module\_matches' we repeat the same matches for 'printf' and 'fprintf' statements. However we check for one more, 'printk' statements, using the same matching technique. Then we concatenate all the matches together in module\_matches. If no main or module matches happen, we add that file to the 'other\_matches' variable. Finally, we echo out all the results. When we echo the 'main\_matches', 'module\_matches' and 'other'\_matches' we pipe the variable to 'tr' to add spaces between the file name, # of printf matches, # of fprintf matches, and number of printk matches, adding a new line at the end, so the next files matches are on the next line.

