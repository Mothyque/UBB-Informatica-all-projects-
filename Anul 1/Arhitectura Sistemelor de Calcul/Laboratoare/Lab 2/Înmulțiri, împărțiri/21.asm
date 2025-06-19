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
    add al, [b]
    add al, 2
    
    mov dh, 3
    mul dh
    
    mov ebx, eax
    
    xor eax, eax
    
    mov al, [c]
    add al, 2
    mov dh, 5
    mul dh
    
    sub ebx, eax
    
    xor eax, eax
    mov eax, [d]
    
    sub eax, ebx
       
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   