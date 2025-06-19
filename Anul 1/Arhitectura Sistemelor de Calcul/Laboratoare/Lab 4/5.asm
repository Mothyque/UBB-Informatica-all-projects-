bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %u", 0
    a dw 10101101b
    b dw 01110010b
    c dd 0
    ; c =  11111111111111110110111000001110
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [b]
    and al, 011111000b
    mov cl, 3
    ror al, cl
    or bl, al
    or bx, 11000000000b
    
    xor eax, eax
    mov ax, [a]
    and ax, 0000000000011111b
    mov cl, 11
    rol ax, cl
    or bx, ax
    
    or ebx, 11111111111111110000000000000000b
    push ebx
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   