bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 12345678h, 1A2B3C4Dh, 1FE98DC7h
    len_src equ ($ - src) / 4
    dest times len_src db 0
    trei dw 3
    print_frmt db "%0Xh ", 0
    
segment code use32 class = code
start:
    mov esi, src
    mov ecx, len_src
    mov edi, dest
    xor eax, eax
    xor edx, edx
    loadDword:
        xor ebx, ebx
        lodsd
        and eax, 11111111000000000000000000000000b
        shr eax, 24
        mov ebx, eax
        mov edx, 0
        div word [trei]
        cmp edx, 0
        jne nextDword
        
        mov eax, ebx
        stosb
        
        nextDword:
            
    loop loadDword
    
    mov esi, dest
    mov ecx, len_src
    
    write:
        push ecx
        lodsb
        cmp al, 0
        je nuafisa
        
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        nuafisa:
        
        pop ecx
    loop write

    push dword 0
    call [exit]
     
        
        