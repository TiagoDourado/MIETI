$NOMOD51
NAME H2A
PUBLIC INICIOH2A

H2ACODE SEGMENT CODE
RSEG H2ACODE ; Rotina que converte d�gito hexadeciimal armazenado
;no endere�o de mem�ria apontado por R0 para ASCII

INICIOH2A: 
;BA1: Bloco de atribui��o 1
MOV A,R0
CLR C
SUBB A,#10

;BD1 : Bloco de decis�o 1
JC BA3_NAO
;Carry � 0, logo � letra porque A>=10
;BA2_SIM:Bloco de atribui��o 2 
ADD A,#'A'
MOV B,A
JMP FIMH2A

;BA3_NAO:BLOCO DE ATRIBUI��O 3

BA3_NAO:
		ADD A,#10
		ADD A ,#'0'
		MOV B,A

FIMH2A:
		RET
END