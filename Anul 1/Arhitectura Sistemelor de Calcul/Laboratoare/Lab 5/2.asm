bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db '%c ', 0
    s db '+', '4', '2', 'a', '@', '3', '$', '*'
    len equ ($ - s)
    d resb 100
    
    
segment code use32 class=code
start:
    mov esi, s
    mov edi, d
    mov ecx, len
    
    load:
        lodsb
        cmp al, '!'
        je special
        
        cmp al, '@'
        je special
        
        cmp al, '#'
        je special
        
        cmp al, '$'
        je special
        
        cmp al, '%'
        je special
        
        cmp al, '^'
        je special
        
        cmp al, '&'
        je special
        
        cmp al, '*'
        je special
        
        jmp not_special
        
        special:
            stosb
        not_special:
    loop load
    
    xor eax, eax
    mov esi, d
    mov ecx, len
    afisare:
        push ecx
        
        lodsb
        push eax
        push print_frmt 
        call [printf]
        add  esp, 4 * 2
        
        pop ecx
    loop afisare 
    
    push    dword 0      
    call    [exit] 