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
    ; c =  11101011000010111011111111111101
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov eax, [a]
    and eax, 00000000000000001111100000000000b
    mov cl, 11
    ror eax, cl
    or ebx, eax
    
    or ebx, 00000000000000000000111111100000b
    
    xor eax, eax
    mov eax, [b]
    and eax, 0000000000000000000111100000000b
    mov cl, 4
    rol eax, cl
    or ebx, eax
    
    xor eax, eax
    mov eax, [a]
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