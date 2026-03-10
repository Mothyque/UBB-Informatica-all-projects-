bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 6
    b db 2
    c db 3
    d dw 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [a]
    sub al, [b]
    
    mov dh, 3
    mul dh
    
    mov ebx, eax
    xor eax, eax
    xor edx, edx
    
    mov al, [c]
    mov dh, 2
    mul dh
    
    add ebx, eax
    
    sub ebx, [d]
    
    push ebx
    push print_frmt 
    call [printf]
    
    
     
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   