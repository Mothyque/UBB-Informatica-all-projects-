bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %u", 0
    a db 1
    b dw 2
    c dd 3
    d dq 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov bx, [b]
    add bx, [b]
    
    add al, [a]
    add eax, dword [d]
    adc eax, dword [d + 4]
    add eax, dword [d]
    adc eax, dword [d + 4]
    
    sub eax, [c]
    add eax, ebx
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   