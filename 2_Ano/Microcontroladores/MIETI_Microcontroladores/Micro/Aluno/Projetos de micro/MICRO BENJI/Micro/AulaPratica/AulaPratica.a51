CSEG 	AT 	0H
	
	LJMP BA1
	
CSEG 	AT 30H

BA1:
	MOV 	R0,#40H
	MOV 	R1,#42H
	MOV 	R2,#128
	MOV 	R3,#0
	MOV 	A,@R0
	
BD1:
	JB 		ACC.7,BA2
	JMP 	BA3

BA2:
	PUSH 	ACC
	MOV 	A,R2
	MOV 	@R1,A
	POP 	ACC
	INC 	R1
	INC 	R3

BA3:
	RL 		A
	PUSH 	ACC
	MOV 	A,R2
	CLR 	c
	RRC 	A
	MOV 	R2,A
	POP 	ACC

BD2:
	CJNE 	R2,#0H,BD1

BA4:
	MOV 	R1,#41H
	MOV 	A,R3
	MOV 	@R1,A
	JMP 	FIM

FIM:
	JMP 	$

END