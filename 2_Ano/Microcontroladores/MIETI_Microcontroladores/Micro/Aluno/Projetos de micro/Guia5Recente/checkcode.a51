	NAME CHECK
	PUBLIC INICIOCHECK
	CHECKCODE SEGMENT CODE 
	RSEG CHECKCODE
	JMP INICIOCHECK
	
	
INICIOCHECK:
	MOV R2,#4
	MOV DPTR,#40H
START:
	MOV B,@R0
	CLR A
	MOVC A,@A+DPTR
	CJNE A,B,FALSO
	INC DPTR
	INC R0
	DJNZ R2,START
	MOV R0,#1
	JMP FIM

FALSO:
	MOV R0,#0
FIM:
	 RET 
END	