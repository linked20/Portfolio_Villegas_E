#include <string>
#include "codearray.h"
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <vector>
#include <sys/stat.h>
#include <sys/types.h>

using namespace std;

int main()
{
	/* The child process id */
	pid_t childProcId = -1;
		
	/* Go through the binaries */
	for(int progCount = 0; 	progCount < NUM_BINARIES; ++progCount)
	{
			
		//TODO: Create a temporary file you can use the tmpnam() function for this.
		// E.g. fileName = tmpnam(NULL)
		//make
		char fileName[L_tmpnam+1];
		
		//TODO: Open the file and write the bytes of the first program to the file.
		//These bytes are found in codeArray[progCount]
		FILE* pFile ; 
		tmpnam(fileName);
		pFile = fopen(fileName, "wb");
		

		if ( ! pFile) 
		{
			perror("fopen");
			exit(-1);
		}

		if (fwrite(codeArray[progCount], sizeof(char), programLengths[progCount], pFile) != programLengths[progCount]) 
		{
			perror("fwrite");
			exit(-1);
		}

		fclose(pFile);	
		
		//TODO: Make the file executable: this can be done using chmod(fileName, 0777)
		chmod(fileName, 0777);
		
		//TODO: Create a child process using fork
		childProcId = fork();
		/* I am a child process; I will turn into an executable */
		if(childProcId == 0)
		{
			
			//TODO: use execlp() in order to turn the child process into the process
			//running the program in the above file.
			printf("Filename: %s", fileName);
			if (execlp(fileName, fileName, NULL) < 0) 
			{
				perror("execlp");
				exit(-1);
			} 
			else 
			{
				fprintf(stderr, "Fork failed\n");
				exit(-1);
			}
		
		}
	}
	
	/* Wait for all programs to finish */
	for(int progCount = 0; progCount < NUM_BINARIES; ++progCount)
	{
		/* Wait for one of the programs to finish */
		if(wait(NULL) < 0)
		{
			perror("wait");
			exit(-1);
		}	
	}
}
