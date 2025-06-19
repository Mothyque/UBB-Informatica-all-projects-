bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %d ', 0
    s1 db 1, 3, 5, 7
    l equ $ - s1
    s2 db 2, 6, 9, 4
    d resb 100
    
segment code use32 class=code
    start:
    
    mov esi, 0
    mov edi, 0
    mov ecx, l
    
    loadbiti:
        mov al, [s1 + esi]
        mov [d + edi], al
        inc edi
        
        mov al, [s2 + esi]
        mov [d + edi], al
        inc edi
        
        inc esi
    loop loadbiti
        
    mov esi, d
    xor eax, eax
    mov al, l
    mov dh, 2
    mul dh
    cwde
    
    mov ecx, eax
    
    xor eax, eax
    
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