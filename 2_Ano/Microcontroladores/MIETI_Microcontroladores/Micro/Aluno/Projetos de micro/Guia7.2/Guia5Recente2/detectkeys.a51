	NAME DETECT
	PUBLIC INICIODETECT
	DETECTCODE SEGMENT CODE 
	RSEG DETECTCODE
	JMP INICIODETECT
	
	EXTRN CODE (INICIOWAIT)
		
// SABER BOTOES EM QUE CARREGOU
	
INICIODETECT:
	MOV R0,#0
	MOV R1,#0
BOTOES:
	JNB P3.5,ENTER
	JNB P3.3,SOMA
	JMP BOTOES

BOTOES2:
	JNB P3.3,SOMA1
	JMP TESTE
	
BOTOES3:
	
	JNB P3.5,NORMAL
	JMP TESTE

ENTER:
	MOV R0,#1
	INC R1
	
	JMP BOTOES2
	
NORMAL:
	USING 0
	PUSH AR1
	USING 0
	PUSH AR0
	MOV R0,#1
	LCALL INICIOWAIT
	POP AR0
	POP AR1
	MOV R0,#2
	INC R1
	
	JMP TESTE
SOMA:
	MOV R0,#2
	INC R1
	
	JMP BOTOES3
SOMA1:
	USING 0
	PUSH AR1
	USING 0
	PUSH AR0
	MOV R0,#1
	LCALL INICIOWAIT
	POP AR0
	POP AR1
	MOV R0,#1
	INC R1
	
TESTE:
	CJNE R1,#0,TESTE2
	JMP BOTOES
TESTE2:
	CJNE R1,#2,FIM
	MOV R0,#0
FIM:	
	RET
	END
	
	