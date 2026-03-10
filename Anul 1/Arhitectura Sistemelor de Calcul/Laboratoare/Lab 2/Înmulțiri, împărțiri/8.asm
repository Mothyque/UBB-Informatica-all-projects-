bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 3
    b db 2
    c db 8
    d dw 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov AL, [a]
    mov DH, 100
    mul DH ; AX = 100 * a
    
    add AX, [d]
    add AX, 5
    
    mov EBX, EAX
    
    xor EAX, EAX
    
    mov AL, [b]
    mov DH, 75
    mul DH
    
    sub EBX, EAX
    
    xor EAX, EAX
    mov EAX, EBX
    
    xor EBX, EBX
    xor EDX, EDX
    mov BL, [c]
    sub BL, 5
    
    div EBX
    
    push EAX     
    
    
    
    
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   