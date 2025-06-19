bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll


segment data use32 class=data
    print_frmt db "Rezultatul este %u", 0
    a dw 0000001010100101b
    b dw 0111010101010001b
    c dd 0 ; c =  01110101010100010001010001011010
   
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov eax, [b]
    and eax, 0000000111100000b
    mov cl, 5
    ror eax, cl
    or ebx, eax
    
    xor eax, eax
    
    mov eax, [a]
    and eax, 0000000000011111b
    mov cl, 4
    rol eax, cl
    or ebx, eax
    
    xor eax, eax
    mov eax, [a]
    and eax, 0001111111000000b
    mov cl, 3
    rol eax, cl
    or ebx, eax
    
    xor eax, eax
    mov eax, [b]
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