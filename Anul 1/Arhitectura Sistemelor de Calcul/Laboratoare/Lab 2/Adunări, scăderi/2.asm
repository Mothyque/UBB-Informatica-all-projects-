bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 4
    b db 13
    c db 6
    d db 7
    
segment code use32 class=code
    start:
    xor EAX, EAX
    mov AL, [b]
    add AL, AL
    
    mov EDX, EAX
    
    xor EAX, EAX
    
    mov AL, [c]
    mov BL, [a]
    sub AL, BL
    add EDX, EAX
    add EDX, [d]
    
    push EDX
	push print_frmt 
        call [printf]
        
        add  esp, 4 * 2

        push    dword 0      
        call    [exit]       
