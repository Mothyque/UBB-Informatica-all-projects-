bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 5
    b db 2
    c db 3
    d db 25
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov AL, [b]
    mov DH, [c]
    mul DH
    
    mov EBX, EAX
    
    
    
    mov AL, [a]
    mov DL, [b]
    sub AL, DL
    mov DH, 2
    mul DH
    add EBX, EAX ; EBX = 2 * (a - b) + b * c
    
    xor EDX, EDX
    
    mov EDX, [d]
    sub EDX, EBX
    
    xor EAX, EAX
    mov EAX, EDX
    
    xor EDX, EDX
    xor EBX, EBX
    mov EBX, 2
    div EBX
    
    push EAX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 4

    push    dword 0      
    call    [exit]   