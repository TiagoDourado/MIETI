	NAME WAIT
	PUBLIC INICIOWAIT
	WAITCODE SEGMENT CODE 
	RSEG WAITCODE
		
INICIOWAIT:
		DEC R0
CARGA:
		MOV TMOD,#01H
		MOV R1,#6
LOOP:
		MOV TH0,#HIGH(-50000)
		MOV TL0,#LOW(-50000)
		SETB TR0
WAIT:
		JNB TF0,WAIT
		CLR TR0
		CLR TF0
		DJNZ R1,LOOP
		CJNE R0,#0,INICIOWAIT
FIM:
		RET
END		