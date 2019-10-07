/*	NAME WAIT2
	PUBLIC WAIT2
	WAIT2CODE SEGMENT CODE 
	RSEG WAIT2CODE
	JMP WAIT2


WAIT2:
	MOV TMOD,#10H //ATIVA TIMER1 NO MODO 1
	MOV R3,#3CH //P�E 60 EM R3, PARA FAZER 3 SEGUNDOS DE TIMER
	MOV IE,#88H  // ATIVA OS BITS EA(GLOBAL) E ET1(TIMER1) DE INTERRUP��ES
TIMER:
	MOV TH1,#HIGH(-50000)
	MOV TL1,#LOW(-50000)
	SETB TR1
LOOP:
	JNB TF1,LOOP
	CLR TR1
	CLR TF1
	DJNZ R3,TIMER
FIM:
	RET
	
	
	EXTRN CODE (INICIOSERIAL)
	
	CSEG AT 001BH
	JMP ROT
ROT:
	LCALL INICIOSERIAL
	RETI
*/
END