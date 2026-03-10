;(c+b+a)-(d+d)

bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 1
    b dw 2
    c dd 3
    d dq 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [a]
    cbw
    add ax, [b]
    cwde
    add eax, [c]
    
    mov edx, [d]
    add edx, [d]
    
    sub eax, edx
    
    push eax
    push print_frmt
    call [printf]
    add esp, 2 * 4

    push    dword 0      
    call    [exit]   