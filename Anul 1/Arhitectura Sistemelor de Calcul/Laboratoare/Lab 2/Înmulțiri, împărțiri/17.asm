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
    
    mov al, [a]
    mov dh, 2
    mul dh
    mov ebx, [d]
    sub ebx, eax
    
    xor ebx, ebx
    mov ebx, 5
    mul ebx
    
    sub eax, 1
    
    mov edx, 300
    sub edx, eax
    
    push edx
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   