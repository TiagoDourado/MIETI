$NOMOD51
#include	<89C51Rx2.inc>
#define		NLOOPS	4

	CSEG	AT	0H
	LJMP		MAIN
	
	CSEG	AT	50H
MAIN:
	MOV		20H,#1
	MOV		21H,#2
	MOV		22H,#3
	MOV		23H,#4
	
	MOV		R3,#NLOOPS
	MOV		R0,#20H
	MOV		R1,#30H
	
LOOP:
	MOV		A,@R0
	MOV		@R1,A
	INC		R0
	INC		R1
	DJNZ	R3,LOOP

AQUI:
	SJMP	AQUI
	
END
	