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
    
    mov AL, [a] ; AL = a
    mov AH, 2 ; AH = 2
    mul AH ; AX = 2 * a`
    add AX, [d] ; AX = d + 2 * a
    
    mov BX, [d] ; BX = d
    mul BX ; EAX = d * (d + 2 * a)
    
    xor EBX, EBX
    
    mov EBX, EAX
    
    xor EAX, EAX
    mov AL, [b]
    mov DH, [c]
    mul DH ; AX = b * c
    
    xor EDX, EDX
    mov EDX, EBX
    
    mov EBX, EAX
    
    mov EAX, EDX 
    
    xor EDX, EDX
    
    div EBX
    
    push EAX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   