$NOMOD51


	CSEG AT 0H
	
MAIN:
	MOV R4,#20
	MOV R1,#30H
	MOV R2,#80H
	MOV R3,#0A0H

CICLO:
	MOV A,@R1
	MOV B,#2
	DIV AB
	MOV A,B
	JNZ IMPAR

PAR:
	MOV A,R2
	MOV R0,A
	MOV A,@R1
	MOV @R0,A
	INC R2
	INC R1
	DJNZ R4,CICLO
	JMP FIM
	
IMPAR:
	MOV A,R3
	MOV R0,A
	MOV A,@R1
	MOV @R0,A
	INC R3
	INC R1
	DJNZ R4,CICLO	
	
FIM:
	END
		