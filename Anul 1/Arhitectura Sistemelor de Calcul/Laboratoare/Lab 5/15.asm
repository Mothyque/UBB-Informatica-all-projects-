bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %d ', 0
    s1 db 2, 1, 3, 3, 4, 2, 6
    l1 equ ($ - s1)
    s2 db 4, 5, 7, 6, 2, 1
    l2 equ ($ - s2)
    d resb 100
    
segment code use32 class=code
    start:
    
    mov edi, 0
    mov esi, l2
    mov ecx, l2
    load1:
        mov al, [s2 + esi - 1]
        mov [d + edi], al
        inc edi
        dec esi
    loop load1
    
    mov esi, 0
    mov ecx, l1
    load2:
        test esi, 1
        jz par
        jmp impar
        
        impar:
            mov al, [s1 + esi]
            mov [d + edi], al
            inc edi
        par:
            inc esi            
    loop load2
        
    
    xor eax, eax
    mov ecx, edi
    mov esi, d
    write:
        push ecx
        
        lodsb
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop write
    

    push    dword 0      
    call    [exit]   