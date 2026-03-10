bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 1
    b db 2
    c db 7
    d db 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    
    mov AL, [c]
    mov BL, [a]
    sub AL, BL
    sub AL, [d]
    
    mov EDX, EAX
    
    xor EAX, EAX
    
    mov AL, [c]
    sub AL, [b]
    sub AL, [a]
   
    add EDX, EAX
    
    push EDX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   