bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %u", 0
    a db 00101000b
    b db 10101011b    
    c db 10100001b
    d db 01100001b
    ; ax devine 010 + 010 + 010 + 110
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov bx, [a]
    and bx, 0000000001110000b
    mov cl, 4
    ror bx, cl
    or dx, bx 
    add ax, dx
    xor edx, edx
    xor ebx, ebx
    
    mov bx, [b]
    and bx, 0000000001110000b
    mov cl, 4
    ror bx, cl
    or dx, bx 
    add ax, dx
    xor edx, edx
    xor ebx, ebx

    mov bx, [c]
    and bx, 0000000001110000b
    mov cl, 4
    ror bx, cl
    or dx, bx
    add ax, dx
    xor edx, edx
    xor ebx, ebx

    
    mov bx, [d]
    and bx, 0000000001110000b
    mov cl, 4
    ror bx, cl
    add ax, dx
    xor edx, edx
    or dx, bx
    add ax, dx
    
    push eax
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   