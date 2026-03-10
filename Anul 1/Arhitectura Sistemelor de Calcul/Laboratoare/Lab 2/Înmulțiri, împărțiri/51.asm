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
    
    mov al, [f]
    mov dh, [g]
    mul dh
    
    mov ecx, eax
    xor eax, eax
    xor edx, edx
    
    mov al, [a]
    mov dh, [b]
    mul dh
    
    xor edx, edx
    
    mov bl, [e]
    mul ebx
        
    sub ecx, eax ; ecx = f * g - a * b * e
    
    
    
    xor edx, edx
    xor ebx, ebx
    xor eax, eax
    
    mov al, [c]
    mov dh, [d]
    mul dh
    add eax, [h]
    
    
    mov ebx, eax
    xor eax, eax
    mov eax, ecx
    xor edx, edx
    div ebx
    
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   