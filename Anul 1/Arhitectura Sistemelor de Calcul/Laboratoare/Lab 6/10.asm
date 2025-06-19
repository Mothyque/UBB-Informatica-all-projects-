bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dw 1234h, 1A2Ch, 98FCh, 1278h
    len_src equ ($ - src) / 2
    b1 times len_src db 0
    b2 times len_src db 0
    print_frmt db "%0Xh ", 0
    newline db 10, 0
segment code use32 class = code
start:
    
    mov esi, src
    mov ecx, len_src
    mov edi, b1
    
    loadb1:
        xor eax, eax  
        lodsw
        and ax, 1111111100000000b
        shr ax, 8
        stosb
    loop loadb1
    
    mov esi, src
    mov ecx, len_src
    mov edi, b2
    loadb2:
        xor eax, eax  
        lodsw
        and ax, 0000000011111111b
        stosb
    loop loadb2
    
    mov esi, b1
    mov ecx, len_src 
    writeb1:
        push ecx
        
        lodsb
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        pop ecx
    loop writeb1
    
    push newline
    call[printf]
    add esp, 4
    
    mov esi, b2
    mov ecx, len_src 
    writeb2:
        push ecx
        
        lodsb
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        pop ecx
    loop writeb2
    
    
    push dword 0
    call [exit]
     
        
        