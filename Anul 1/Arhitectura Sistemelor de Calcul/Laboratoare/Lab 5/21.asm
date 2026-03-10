bits 32

global start

extern exit
import exit msvcrt.dll
extern printf
import printf msvcrt.dll

segment data use 32 class=data
    print_frmt db ' %d ', 0
    a db 2, 1, 3, -3, -4, 2, -6
    l1 equ ($ - a)
    b db 4, 5, 7, 6, 2, 1
    l2 equ ($ - b)
    r resb 100
    
segment data use 32 class=data
    start:
    
    mov ecx, l2
    mov esi, l2
    sub esi, 1
    mov edi, 0
    
    loadb:
        mov al, [b + esi]
        mov [r + edi], al
        inc edi
        dec esi
    loop loadb
    
    mov ecx, l1
    mov esi, 0
    
    loada:
        mov al, [a + esi]
        cbw
        cwde
        cmp eax, 0
        jg next
        
        mov [r + edi], eax
        inc edi
        
        next:
            inc esi
    loop loada
    mov ecx, edi
    mov esi, r
    xor eax, eax
    
    write:
        push ecx
        
        lodsb
        cbw
        cwde
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop write    
    
    
    
    push    dword 0      
    call    [exit]