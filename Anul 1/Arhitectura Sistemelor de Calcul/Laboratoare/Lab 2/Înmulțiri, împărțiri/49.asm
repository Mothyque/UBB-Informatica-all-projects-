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
    e dw 5
    f dw 6
    g dw 7
    h dw 8
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [e]
    add al, [g]
    mov dh, 2
    mul dh
    
    mov ecx, eax
    
    xor eax, eax 
    xor edx, edx
    
    mov al, [a]
    mov dh, [c]
    mul dh
    
    mov ebx, eax
    
    xor edx, edx
    xor eax, eax
    
    mov eax, ecx
    div ebx
    
    mov dl, [h]
    sub dl, [f]
    push edx
    
    add eax, edx
    
    mov ecx, eax
    
    xor eax, eax
    xor edx, edx
    
    mov al, [b]
    mov dh, 3
    mul dh
    
    add eax, ecx
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   