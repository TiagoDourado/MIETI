# UNTITLED PROGRAM

	.data		# Data declaration section

porto:	.word 70,602,102,18,15,-4,89,8078,-475,289
nl:		.asciiz "\n"

	.text

main:			# Start of code section
	
	la $t0, porto
	lw $s1, ($t0)
	move $a0, $s1
	li $v0, 1
	syscall
	la $a0,nl	
	li $v0,4	
	syscall
	add $t0,$t0,4
	lw $s1, ($t0)
	move $a0, $s1
	li $v0, 1
	syscall
	la $a0,nl	
	li $v0,4	
	syscall
	add $t0,$t0,4
	lw $s1, ($t0)
	move $a0, $s1
	li $v0, 1
	syscall
	la $a0,nl	
	li $v0,4	
	syscall
	add $t0,$t0,4
	lw $s1, ($t0)
	move $a0, $s1
	li $v0, 1
	syscall
	la $a0,nl	
	li $v0,4	
	syscall





# END OF PROGRAM
