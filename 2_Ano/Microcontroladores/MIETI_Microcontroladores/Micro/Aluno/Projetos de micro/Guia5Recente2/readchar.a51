	NAME READ
	PUBLIC INICIOREAD
	READCODE SEGMENT CODE 
	RSEG READCODE
		
	EXTRN CODE (INICIODISPLAY)
	EXTRN CODE (INICIOWAIT)
	EXTRN CODE (INICIODETECT)
		
// LER CARATER 


INICIOREAD:	
			MOV R0,#0
			LCALL INICIODISPLAY
			MOV R1,#0
			MOV R2,#0
DETETA:			
			USING 0
			PUSH AR1
			LCALL INICIODETECT
			MOV A,R0
			LCALL INICIOWAIT
			POP AR1
			
			MOV R2,A
			CJNE R2,#0,DETETA1 // SALTA SE N�O CARREGAR NAS DUAS TECLAS
			MOV R1,#11
			MOV R2,#1
			JMP DETETAFINAL
DETETA1:
			CJNE R2,#2,DETETAFINAL //SALTA SE N�O CARREGOU NA SOMA
			INC R1
			CJNE R1,#10,DISWAIT
			MOV R1,#0
DISWAIT:
			MOV A,R1
			MOV R0,A
			LCALL INICIODISPLAY
			USING 0
			PUSH AR1
			LCALL INICIOWAIT
			POP AR1
			
DETETAFINAL:
			CJNE R2,#1,DETETA // SALTA SE N�O FOR ENTER
			USING 0
			PUSH AR1
			LCALL INICIOWAIT
			POP AR1
			MOV A,R1
			MOV R0,A
			RET
			
END
			