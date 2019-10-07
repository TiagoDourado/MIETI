#include<89c51ic2.inc>
PERIODO EQU 30
CSEG AT 0H
JMP MAIN
CSEG AT 002BH
CPL P2.0
RETI
MAIN:
	MOV R5,#PERIODO
	MOV A,#255
	SUBB A,R5
	MOV R5,A

CONFIG_TMR:
			MOV TMOD,#1H
			MOV TH0,R5
			MOV TL0,R5
			MOV RCAP2H,TH2
			MOV RCAP2L,TL2
			SETB TR2
			SETB EA
			SETB EXF2
			JMP $
			END
	