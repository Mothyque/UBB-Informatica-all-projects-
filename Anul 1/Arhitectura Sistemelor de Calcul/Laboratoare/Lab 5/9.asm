bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %d ', 0
    s db 1, 2, 4, 6, 10, 20, 25
    l equ $ - s - 1
    d resb 100
    
segment code use32 class=code
    start:
    
    mov esi, 0
    mov edi, 0
    mov ecx, l
    
    load:
        mov bl, [s + esi]
        inc esi 
        mov al, [s + esi]
        sub al, bl
        cbw 
        cwde
        mov [d + edi], eax
        inc edi
    loop load
    
    mov esi, d
    xor eax, eax
    mov ecx, l
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