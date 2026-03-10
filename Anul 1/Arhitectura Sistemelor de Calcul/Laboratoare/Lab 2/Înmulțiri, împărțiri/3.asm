bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 2
    b db 2
    c db 6
    d db 9
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov AL, [b]
    add AL, 1
    mov DH, 2
    mul DH ; AX = 2 * (b + 1)
    
    
    mov EBX, [d]
    sub EBX, 1 ; EBX = -1 + d
        
    sub EBX, EAX ; EBX = -1 + d - 2 * (b + 1)
    
    mov EAX, EBX ; ; EBX = -1 + d - 2 * (b + 1)
    
    xor EBX, EBX
    xor EDX, EDX
    
    mov BL, [a]
    div EBX
    
    push EAX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   