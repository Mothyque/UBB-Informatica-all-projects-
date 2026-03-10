bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 5
    b db 3
    c db 1
    d dw 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov AL, [a]
    mov BL, [b]
    add AL, BL
    sub AL, [c]
    
    mov AH, 2
    mul AH
    
    add AX, [d]
    sub AX, 5
    
    mov BX, [d]
    mul BX
    
    push EAX
    
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   