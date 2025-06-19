bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %u", 0
    a dw 1110101100001011b
    b dw 1111101110101101b
    c dd 0
    ; c =  1110101100001011 1100001 101101 110
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov eax, [a]
    and eax, 0111000000000000b
    mov cl, 12
    ror eax, cl
    or ebx, eax
    
    xor eax, eax
    mov eax, [b]
    and eax, 0000000000111111b
    mov cl, 3
    rol eax, cl
    or ebx, eax
    
    xor eax, eax
    mov eax, [a]
    and eax, 0000001111111000b
    mov cl, 6
    rol eax, cl
    or ebx, eax
    
    xor eax, eax
    mov eax, [a]
    push ebx
    and eax, 00000000000000001111111111111111b
    mov cl, 16
    rol eax, cl
    or ebx, eax
    mov [c], ebx
    push ebx
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   