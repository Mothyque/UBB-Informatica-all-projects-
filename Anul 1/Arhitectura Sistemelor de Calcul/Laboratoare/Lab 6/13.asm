bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 12345607h, 1A2B3C15h, 13A33412h
    len_src equ ($ - src) / 4
    dest times len_src db 0
    sapte db 7
    print_frmt db "%02xh ", 0
segment code use32 class = code
start:
    
    mov esi, src
    mov ecx, len_src
    mov edi, dest
    
    load:
        xor eax, eax
        xor ebx, ebx
        lodsd
        
        and eax, 00000000000000000000000011111111b
        mov bl, al
        mov ah, 0
        div byte [sapte]
        
        cmp ah, 0
        jne next
        
        mov al, bl
        stosb
        
        next:
    loop load
    
    mov esi, dest
    mov ecx, len_src
    xor eax, eax
    write:
        push ecx
        
        lodsb
        cmp al, 0
        je skip
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
    skip:
        pop ecx
    loop write
    
    
    push dword 0
    call [exit]
     
        
        