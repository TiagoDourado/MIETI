$NOMOD51
	NAME	H2A
	
	PUBLIC	INICIOH2A
		
	H2ACODE SEGMENT CODE
	RSEG H2ACODE
	;rotina que converte digito hexadecimal armazenado
	;no endere�o de mem�ria apontado por R0 para ASCII

INICIOH2A:
;BA1:BLOCO DE ATRIBUI��O 1
	MOV	A,@R0
	CLR C
	SUBB A,#10
	
	;BD1:BLOCO DE DECIS�O 1
	JC	BA3_NAO
	;CARRY � 0, LOGO � LETRA PORQUE A>=10
	;BA2_SIM:BLOCO DE ATRIBUI��O 2
	ADD A,#"A"
	MOV B,A
	JMP FIMH2A
	
	;BA3_NAO:BLOCO DE ATRIBUI��O 3
	
BA3_NAO:
	ADD A,#10
	ADD A,#'0'
	MOV B,A
FIMH2A:
	RET
	
END