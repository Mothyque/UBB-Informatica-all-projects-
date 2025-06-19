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
    c db 3
    d db 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov AL, [b]
    add AL, [c]
    add DH, 4
    mul DH
    
    mov EBX, EAX
    
    xor EAX, EAX
    xor EDX, EDX
    mov AL, [a]
    mov DH, 10
    mul DH
    xor EDX, EDX
    mov DX, 100
    sub DX, AX
    mov EAX, EDX
    add EAX, EBX
    xor EBX, EBX
    mov EBX, [d]
    sub EAX, EBX
    
    push EAX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   