bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 6
    b db 12
    c db 3
    d db 7
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov AL, [a]
    add AL, [b]
    mov DH, 2
    mul DH
    
    mov EBX, EAX
    xor EAX, EAX
    
    mov AL, [c]
    mov DH, 5
    mul DH
    
    sub EBX, EAX
    
    xor EAX, EAX
    
    mov AX, [d]
    sub AX, 3
    
    mul EBX
    
    push EAX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   