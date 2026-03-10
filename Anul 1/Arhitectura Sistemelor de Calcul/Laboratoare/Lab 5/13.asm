bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %d ', 0
    print_newline db 10, 0
    s db 1, 5, 3, 8, 2, 9
    l equ $ - s
    d1 resb 100
    d2 resb 100
segment code use32 class=code
    start:
    
    xor ebx, ebx
    
    mov ecx, l
    mov esi, 0
    mov edi, 0
    mov ebx, 0
    
    load:
        mov al, [s + esi]
        
        test esi, 1
        jz par
        jnz impar
        jmp nextbyte
        
        par:
            mov [d1 + edi], al
            inc edi
            jmp nextbyte
        
        impar:
            mov [d2 + ebx], al
            inc ebx
        
        nextbyte:
            inc esi
    loop load
        
    xor eax, eax
    mov ecx, edi
    mov esi, d1
    
    writed1:
        push ecx
        
        lodsb
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop writed1
    
    push print_newline
    call[printf]
    add esp, 4
    
    mov ecx, ebx
    mov esi, d2
    xor eax, eax
    
    writed2:
        push ecx
        
        lodsb
        push eax
        push print_frmt
        call [printf]
        add esp, 2 * 4
        
        pop ecx
    loop writed2

    push    dword 0      
    call    [exit]   