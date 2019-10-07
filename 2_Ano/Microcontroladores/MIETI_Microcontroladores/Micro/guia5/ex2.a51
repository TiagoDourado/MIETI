EXTRN CODE (CONFIG_RS232)
	EXTRN CODE (ENVIAR_RS232)
	EXTRN CODE (RECEBER_RS232)
	
	CSEG AT 0H
	JMP MAIN
	CSEG AT 30H
		
MAIN:
	CALL CONFIG_RS232
	CALL RECEBER_RS232
	CLR C
	SUBB A,#30h
	MOV R0,A
	
DISPLAY:
		;MOV R4,#200
		MOV P1,#0FFH
		;DJNZ R4,$
		CLR P1
	ZERO:	
		CJNE R0,#0h,ONE 
		MOV P1,#8 
		
		JMP FIMDISPLAY 
	ONE:
		CJNE R0,#1h,TWO
		MOV P1,#0CBH
		
		JMP FIMDISPLAY 
	TWO:
		CJNE R0,#2h,THREE
		MOV P1,#50H
		
		JMP FIMDISPLAY	
	THREE:
		CJNE R0,#3h,FOUR	
		MOV P1,#42H	
		
		JMP FIMDISPLAY
	FOUR:
		CJNE R0,#4h,FIVE	
		MOV P1,#83H		
		
		JMP FIMDISPLAY  
	FIVE:
		CJNE R0,#5h,SIX	
		MOV P1,#22H	
		
		JMP FIMDISPLAY 
	SIX:
		CJNE R0,#6h,SEVEN	
		MOV P1,#20H		
		
		JMP FIMDISPLAY  
	SEVEN:
		CJNE R0,#7h,EIGHT	
		MOV P1,#4BH		
		
		JMP FIMDISPLAY  
	EIGHT:
		CJNE R0,#8h,NINE	
		MOV P1,#0	
		
		JMP FIMDISPLAY  
	NINE:
		MOV P1,#2H
		
		JMP FIMDISPLAY  
FIMDISPLAY:
			END