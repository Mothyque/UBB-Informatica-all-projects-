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
    mov DH, [a]
    sub AL, DH
    add AL, 2
    
    xor EDX, EDX
    mov dh, 20
    
    mul dh
    mov EBX, EAX
    
    xor EAX, EAX
    xor EDX, EDX
    mov AL,  [c]
    mov DH, 10
    mul dh
    
    sub EBX, EAX
    mov EAX, EBX
    xor EBX, EBX
    mov EBX, 3
    mul EBX
    
    xor EBX, EBX
    mov EBX, EAX
    xor EAX, EAX
    xor EDX, EDX
    mov AL, [d]
    sub AL, 3
    mov DH, 2
    mul DH
    
    add EBX, EAX
    push EBX
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   