/*+
 * lab1.c - Print process information
-*/

#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <strings.h>
#include <ctype.h>

/*
 * Returns whether a dirent's name only contains digits
 *
 * d - dirent entry
 */
int isProcessDir(const struct dirent*d){
	int i = 0;

	while (d->d_name[i] != '\0')
	{
		char currentLetter = d->d_name[i];
		if (currentLetter >= '1' && currentLetter <= '9'){
			i++;
		}
		else{
			return 0;
		}
	}
	return 1;
}

/*
 * Increments a pointer n characters from the start of str.
 * Then, keeps incrementing until a non-whitespace character is
 * encountered. After that, the first whitespace character that
 * it meets is set to the null terminating character
 *
 * Returns a pointer to the first non-whitespace character.

 * str - string to strip
 * n - number of characters to strip initially
 */
char* stripString(char* str, int n)
{
	str += n;
	while (isspace(*str))
	{
		str++;
	}
	char *rval = str;
	while (!isspace(*str))
	{
		str++;
	}
	*str = '\0';
	return rval;
}

/*
 * Prints the running processes, similar to ps -aux
 */
int main(){
   struct dirent ** namelist;
   char * testProc = "proctest/";
   char * realProc = "/proc/";

   char* dirname;
   int isTesting = 0;
   if(isTesting){
   	dirname = testProc;
   }
   else{
   	dirname = realProc;
   }
   printf("%s", dirname);

   int n = scandir(dirname, &namelist, &isProcessDir, NULL);

   printf("The currently running processes are:\n");
   printf("PID Name Status UID Cmdline\n");
   int i = 0;
   for (i = 0; i < n; i++)
   {
	   char filename_buffer[256];

	   char* pid = namelist[i]->d_name;
	   char cmdline[128];

	   char name_buffer[128];
	   char* name;

	   char status_buffer[128];
	   char* status;

	   char uid_buffer[128];
	   char* uid;

	   // Read the cmdline from /proc/{PID}/cmdline
	   sprintf(filename_buffer, "%s%s%s", dirname, pid, "/cmdline");

	   FILE *file = fopen(filename_buffer, "r");
	   if (file != NULL)
	   {
		   fgets(cmdline, 128, file);
		   fclose(file);
	   }

	   // Read the process name and status form /proc/{PID}/status
	   sprintf(filename_buffer, "%s%s%s", dirname, pid, "/status");
	   file = fopen(filename_buffer, "r");
	   if (file != NULL)
	   {
		   // The first line in /proc/{PID}/status is the name.
		   fgets(name_buffer, 128, file);
		   name = stripString(name_buffer, sizeof("Name:"));

		   // The second line is the status
		   fgets(status_buffer, 128, file);
		   status = stripString(status_buffer, sizeof("Status:"));

		   int j = 0;
		   for (j = 0; j < 5; j++)
		   {
			   fgets(uid_buffer, 128, file);
		   }
		   uid = stripString(uid_buffer, sizeof("UID:"));
		   fclose(file);
	   }

	   printf("%s   %s   %s   %s   %s\n", pid, name, status, uid, cmdline);
   }

   return 0;
}
