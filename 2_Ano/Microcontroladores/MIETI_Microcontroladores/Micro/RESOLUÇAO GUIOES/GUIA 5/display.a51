NAME DISPLAY
	PUBLIC INICIODISPLAY
	DISPLAYCODE SEGMENT CODE 
	RSEG DISPLAYCODE
	JMP INICIODISPLAY
	CSEG AT 5DH
TABLE: DB 0CH,0CFH,54H,46H,87H,26H,24H,4FH,4H,6H

INICIODISPLAY:
	MOV DPTR,#TABLE
	MOVC A,@A+DPTR
	MOV P1,A

	
	FIM: RET
END					