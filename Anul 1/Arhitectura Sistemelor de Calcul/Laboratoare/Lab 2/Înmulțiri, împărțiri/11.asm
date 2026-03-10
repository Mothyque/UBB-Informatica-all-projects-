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
    d dw 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov eax, [d]
    mov ebx, 2
    div ebx
    
    xor edx, edx
    xor ebx, ebx
    
    mov bl, [c]
    add bl, [b]
    mul ebx
    
    xor ebx, ebx
    mov ebx, eax
    
    xor eax, eax
    xor edx, edx
    
    mov al, [a]
    mov dh, [a]
    mul dh
    
    sub ebx, eax
    
    mov eax, ebx ;EAX = d / 2 * c + b - a * a
    xor ebx, ebx
    xor edx, edx
    mov bl, [b]
    div ebx
    
    push eax
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   