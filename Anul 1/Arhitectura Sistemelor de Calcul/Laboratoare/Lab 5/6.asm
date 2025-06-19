bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %d ', 0
    s db 1, 2, 3, 4, 5, 6, 7, 8
    l equ $ - s
    d resb 100
    
segment code use32 class=code
    start:
    
    mov esi, 0
    mov edi, 0
    mov ecx, l
    
    loadbitipari:
        test esi, 1
        jnz impar
        
        mov al, [s + esi]
        mov [d + edi], al
        inc edi
        
        impar:
            inc esi
    loop loadbitipari
    
    mov esi, 0
    mov ecx, l
    loadbitiimpari:
        test esi, 1
        jz par
        
        mov al, [s + esi]
        mov [d + edi], al
        inc edi
        par:
            inc esi
        
        
    loop loadbitiimpari
    
    mov esi, d
    mov ecx, l
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