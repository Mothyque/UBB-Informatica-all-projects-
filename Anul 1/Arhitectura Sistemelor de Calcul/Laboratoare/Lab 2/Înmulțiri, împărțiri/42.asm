bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 3
    b db 1
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
    
    mov al, [a]
    mov dh, [d]
    mul dh
    add ax, [e]
    
    xor edx, edx
    
    mov bl, [c]
    sub bl, [b] ; bl = c - b
    
    mov ecx, eax ;ecx  = a * d + e
    xor eax, eax
    mov al, [h]
    div ebx
    xor ebx, ebx
    add bl, [c]
    add eax, ebx ; eax = c + h / (c - b)
    
    xor ebx, ebx
    mov ebx, ecx
    
    xor ecx, ecx
    mov ecx, eax
    
    xor eax, eax
    mov eax, ebx
    
    xor ebx, ebx
    mov ebx, ecx
    
    div ebx
    
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   