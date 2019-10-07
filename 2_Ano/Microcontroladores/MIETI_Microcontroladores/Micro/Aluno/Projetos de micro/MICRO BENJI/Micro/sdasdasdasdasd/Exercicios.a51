NAME MAIN


EXTRN	CODE	(CONTAR1_0)

CSEG	AT	0H

LJMP MAIN

CSEG AT 30H
	
	MAIN:
		MOV		R0,#20H
		MOV		R2,#0H
		MOV		R3,#0H
		MOV		R4,#0H
		MOV		R5,#0H
	LOOP:
		CJNE	@R0,#0H,TESTE
		JMP		FIM
		
	TESTE:
		LCALL	CONTAR1_0
		MOV		A,R6
		SUBB	A,R4       ;se R6 > R4, o cy fica a 0
		JC		TESTE2
		CLR		A
		MOV		A,R6
		MOV		R4,A
		CLR		A
		MOV		A,@R0
		MOV		R3,A
		CLR 	A
	
	TESTE2:
		MOV		A,R7
		SUBB	A,R5
		JC		TESTE3
		CLR		A
		MOV		A,R7
		MOV		R5,A
		CLR		A
		MOV		A,@R0
		MOV		R2,A
		INC 	R0
		
	TESTE3:
		INC		R0
		JMP		LOOP
		
	FIM:
		JMP		$
		END