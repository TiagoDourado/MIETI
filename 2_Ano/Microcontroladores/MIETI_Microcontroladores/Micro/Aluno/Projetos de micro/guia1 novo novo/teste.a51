#include <89c51rx2.inc>

CSEG AT 0H
	
	SETB	P3.3
	SETB	P3.5
CICLO:
	JNB		P3.3,ACENDE1
	JNB		P3.5,ACENDE2
	JMP		CICLO
ACENDE1:
	MOV		P1,#8
	JMP		CICLO
ACENDE2:
	MOV		P1,#0CBH
	JMP		CICLO
END