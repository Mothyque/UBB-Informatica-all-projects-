bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    s1 db 7, 33, 55, 19, 46
    len1 equ $ - s1
    s2 db 33, 21, 7, 13, 27, 19, 55, 1, 46 
    len2 equ $ - s2
    dest times len2 db 0
    print_frmt db "%d ", 0
    
segment code use32 class = code
start:
    mov esi, s2
    mov ecx, len2
    mov edi, dest
    
    load:
        xor eax, eax
        lodsb
        mov bl, al
        mov al, 0
        compara:
            cmp al, len1
            jge negasit
            cbw
            cwde
            cmp bl, [s1 + eax]
            je gasit
            
            inc al
            jmp compara
        
        gasit:
            inc al
            stosb
            jmp next
        negasit:
            xor al, al
            stosb
          
        next:        
    loop load    
    
    mov esi, dest
    mov ecx, len2
    
    write:
        push ecx
        
        lodsb
        push eax
        push print_frmt
        call [printf]
        add esp, 2 * 4
        
        pop ecx
    loop write
    
    push dword 0
    call [exit]
        
    
    