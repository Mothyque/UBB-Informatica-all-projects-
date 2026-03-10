bits 32

global start

extern exit
import exit msvcrt.dll
extern printf
import printf msvcrt.dll

segment data use 32 class=data
    print_frmt db ' %d ', 0
    a db 2, 1, 3, -3, -4, 2, -6
    l equ ($ - a)
    b db 4, 5, -5, 7, -6, -2, 1
    r resb 100
    
segment data use 32 class=data
    start:
        mov ecx, l
        mov esi, 0
        mov edi, 0
        
    load:
        mov al, [a + esi]
        test al, 1
        jnz next
        
        movsx eax, al
        cmp eax, 0
        jge next
        
        mov [r + edi], eax
        inc edi
        
    next:
        mov al, [b + esi]
        test al, 1
        jnz nextbyte
        
        movsx eax, al
        cmp eax, 0
        jge nextbyte
        
        mov [r + edi], eax
        inc edi
    
    nextbyte:
        inc esi
        loop load

    mov esi, r
    mov ecx, edi
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

            