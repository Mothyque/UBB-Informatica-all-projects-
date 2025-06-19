bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 3
    b db 12
    c db 3
    d db 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov AL, [b]
    sub AL, 1
    mov AH, 2
    mul AH
    
    mov DX, [d]
    sub AX, DX
    
    mov EBX, EAX
    ; EBX = 2 * (b - 1) - data
    xor EAX, EAX
    
    mov AL, [a]
    mov DH, [a]
    mul DH
    
    sub EBX, EAX
    push EBX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   