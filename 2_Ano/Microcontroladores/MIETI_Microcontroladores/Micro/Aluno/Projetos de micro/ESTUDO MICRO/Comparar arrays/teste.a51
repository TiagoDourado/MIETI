$NOMOD51
#include <89c51ic2.inc>

	CSEG AT 0H
	

MAIN:	
		MOV R0,#30H
		MOV R1,#60H
		MOV R3,#4

START:	
		
		MOV A,@R0
		CJNE A,60h, FALSE
		DJNZ R3, TRUE
		INC R0
		INC R1
		JMP START
		
FALSE:
		MOV R0,#0
		JMP FIM
		
TRUE:	
		MOV R0,#1

FIM:
		END