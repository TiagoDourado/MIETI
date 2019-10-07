;#include <89C51Rx2.inc>
		NAME MAIN

		EXTRN 	CODE 	(ENVIAR_RS232, RECEBER_RS232, CONFIG_RS232)

		CSEG 	AT	0H
		JMP		MAIN

		CSEG 	AT 	30H
MAIN:
		CALL	CONFIG_RS232
		MOV 	R4,#0
		MOV 	DPTR,#MENSAGEM
MAINLOOP:
        MOV		A,R4
		MOVC	A,@A+DPTR
		CALL	ENVIAR_RS232
		INC		R4
		CJNE	R4,#MSGLEN,MAINLOOP
MAINLOOP2:
		CALL	RECEBER_RS232
		CALL	ENVIAR_RS232
		JMP		MAINLOOP2

MENSAGEM:
		DB	"MICROPROCESSADORES E CIRCUITOS ELECTRONICOS-"
MSGLEN  EQU $-MENSAGEM
END