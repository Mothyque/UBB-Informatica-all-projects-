bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %u", 0
    a dw 1110010110001011b
    b db 10101011b    
    ;b devine 10100101
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov ax, [a]
    and ax, 0000111100000000b
    mov cl, 8
    ror ax, cl
    or bx, ax
    
    xor eax, eax
    mov ax, [b]
    and ax, 11110000b
    or bx, ax
    push ebx
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   