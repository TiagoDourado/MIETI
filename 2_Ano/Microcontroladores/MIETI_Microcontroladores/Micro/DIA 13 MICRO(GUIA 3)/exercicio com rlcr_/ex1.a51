$NOMOD51
#include <89C51IC2.inc>

	CSEG AT 0H

MAIN: 
MOV R1,#30H
MOV R2,#80H;PAR
MOV R3,#0A0H;IMPAR
MOV R4,#0;CONTADOR
	  
	   NICIO: MOV A, @R0
	          INC R0
			  INC R4
			  
			  