	NAME NEWPASS
	PUBLIC INICIOPASS1
	NEWPASS SEGMENT CODE 
	RSEG NEWPASS
	JMP INICIOPASS1
		
	EXTRN CODE (INICIOREAD)
	EXTRN CODE (INICIOWAIT)
	EXTRN CODE (INICIODISPLAY)
	EXTRN DATA (COD3)
		
// REDEFENIR NOVA PASS

INICIOPASS1:
	MOV R0,#COD3
	MOV R2,#4
	MOV A,R0
	MOV R1,A
	USING 0
	PUSH AR1
	MOV R0,#13
	LCALL INICIODISPLAY
	MOV R0,#3
	LCALL INICIOWAIT
	POP AR1
INICIO:
	USING 0
	PUSH AR1
	USING 0
	PUSH AR2
	LCALL INICIOREAD
	POP AR2
	POP AR1
	MOV A,R0
	MOV @R1,A
	INC R1
	DJNZ R2,INICIO
FIM:
	MOV R0,#14
	LCALL INICIODISPLAY
	MOV R0,#3
	LCALL INICIOWAIT
	RET
	END