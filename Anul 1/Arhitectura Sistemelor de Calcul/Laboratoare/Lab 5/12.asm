bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %c ', 0
    s1 db 'a', 'b', 'c', 'd', 'e', 'f'
    l1 equ ($ - s1)
    s2 db '1', '2', '3', '4', '5'
    l2 equ ($ - s2)
    d resb 100
    
segment code use32 class=code
    start:
    
    mov esi, 0
    mov edi, 0
    mov ecx, l2
    
    loadpare:
        mov al, [s2 + esi]
        
        test al, 1
        jz par
        jmp nextbyte1
        par:
            mov [d + edi], al
            inc edi
            jmp nextbyte1
        nextbyte1:
            inc esi
    loop loadpare
    
    mov esi, 0
    mov ecx, l1
    
    loadimpare:
        mov al, [s1 + esi]
        test al, 1
        jnz impare
        jmp nextbyte
        impare:
            mov [d + edi], al
            inc edi
            jmp nextbyte
        nextbyte:
            inc esi
    loop loadimpare
    
    xor eax, eax
    mov esi, d
    mov ecx, edi
    
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