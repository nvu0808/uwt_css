;TCSS 371 Spring HW6
;Zhou Lu
;Hopp Pham
;this program will encrypt or decrypt a string input from user

	.ORIG	x3000	;Starting address
	JSR	GETTYPE	;go to the get type subroutine
	JSR	GETKEY	;go to the get key subroutine	
	JSR	GETSTR	;go to the get key subroutine
	JSR	CRYPT	;go to the decrypt or encrypt subroutine
	JSR	PRINT	;go to print the result out	
	BRnzp	END	;go to halt

GETTYPE	ST	R7, SAVER7	;save R7 value to the a memory address
	LEA	R0, TYPESTR		
	PUTS
	TRAP	x20		;getc
	TRAP	x21		;echo back
	STI	R0, TYPE	;M[M[TYPE]] = R0
	LD	R7, SAVER7	;load the previous address back to R7
	RET

GETKEY	ST	R7, SAVER7	;save R7 value to the a memory address
	LEA	R0, KEYSTR		
	PUTS
	TRAP	x20		;getc
	TRAP	x21		;echo back
	STI	R0, KEY		;M[M[KEY]] = R0
	LD	R7, SAVER7	;load the previous address back to R7
	RET

GETSTR	LD	R1, MESS	;use the R1 as a pointer
	LD	R2, NEWLINE	;use to check the null terminator
	ST	R7, SAVER7	;save R7 value to the a memory address	
	LEA	R0, MESSSTR	
	PUTS
AGAIN	TRAP	x20		;getc
	TRAP	x21		;echo back
	STR	R0, R1, #0	;M[M[R1] + 0] = R0
	ADD 	R1, R1, #1	;increase R1 pointer
	ADD	R3, R2, R0	
	BRnp	AGAIN		;if R3 is negative or positive then go back again
	LD	R7, SAVER7	;load the previous address back to R7
	RET	

CRYPT	ST	R7, SAVER7	;save the R7 address
	LD	R1, MESS	;use R1 as a pointer to the message
	LD	R2, RES		;use R2 as a pointer to the result
LOOP	LDR	R6, R1, #0	;load the character into R6
	ADD 	R6, R6, #-10	;do the checking
	BRz	GOBACK		;RET
	LD	R5, E
	LDI	R4, TYPE	;R4 = M[M[TYPE]]		
	ADD	R5, R5, R4
	BRz	ENCRT
	BRn	DECRT
GOON1	ADD	R1, R1, #1	;increase R1 pointer by one
	ADD	R2, R2, #1	;increase R2 pointer by one
	BRnzp	LOOP
GOBACK	LD 	R7, SAVER7	;load the previous R7 value back to R7
	ADD 	R6, R6, #10
	STR	R6, R2, #0	;add the null terminator to the R2
	RET



ENCRT	LDR	R3, R1, #0
	JSR	TOGGLE		;go to the subtract key subroutine
	JSR	ADDKEY		;go to the toogle subroutine
	STR	R3, R2, #0	;store the result from R3 to the result memory location. R2 is the pointer to it.
	BRnzp	GOON1		;go back to the loop

DECRT	LDR	R3, R1, #0
	JSR	SUBKEY		;go to the toggle subroutine
	JSR	TOGGLE		;go to the add key subroutine
	STR	R3, R2, #0	;store the result from R3 to the result memory location. R2 is the pointer to it.
	BRnzp	GOON1		;go back to the loop


TOGGLE	AND	R4, R3, #1	
	BRz	addOne		;if R4 is 0 that means the last bit is 0
	ADD 	R3, R3, #-1
	RET
addOne	ADD	R3, R3, #1	
	RET

ADDKEY	LDI 	R4, KEY		;put the key into R4
	LD	R5, ZERO
	ADD	R4, R4, R5	;without this, will add the ascii value to the R3
	ADD 	R3, R3, R4	;add the key to the R3 which contain the result after the toggle
	RET

SUBKEY	LDI 	R4, KEY		;put the key into R4
	LD	R5, ZERO
	ADD	R4, R4, R5	;without this, will add the ascii value to the R3
	NOT 	R4, R4		;flip the bit of R4
	ADD	R4, R4, #1	;add one
	ADD 	R3, R3, R4	;add the key to the R3 which contain the result after the toggle
	RET

PRINT	ST	R7, SAVER7	
	LD	R1, RES		;load the memory address of the result
LOOP2	LDR	R2, R1, #0	;load the result character to R2
	ADD	R3, R2, #-10
	BRnp	GOON
	LD	R7, SAVER7
	RET
GOON	ADD 	R1, R1, #1	;increment R1
	ADD	R0, R2, #0	;laod the value in R2 to R0
	TRAP	x21		;print to the console
	BRnzp	LOOP2		
	
END	HALT	


;Propmt
TYPESTR	.STRINGZ "\nType (E)ncrypt/(D)ecrypt: "
KEYSTR	.STRINGZ "\nEnter encryption key(1-9): "
MESSSTR	.STRINGZ "\nEnter message (20 char limit): "
NEWLINE	.FILL	#-10

;Data
SAVER7	.BLKW	#1	;a place to hold R7 value
TYPE	.FILL	x3100	;a place to hold the type
KEY	.FILL	x3101	;a place to hold the key
MESS	.FILL	x3102	;a place to hold the input string
RES	.FILL	x3117	;a place to hold the result
E	.FILL	#-69
ZERO	.FILL	#-48	
	.END