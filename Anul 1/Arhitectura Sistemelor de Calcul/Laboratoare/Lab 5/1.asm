bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %d ', 0
    s db 1, 2, 3, 4
    l equ ($ - s - 1)
    d resb 100
    
segment code use32 class=code
    start:
    
    mov esi, s
    mov edi, d
    mov ecx, l
    
    lodsb ; al = s[0]
    mov bl, al ; bl = s[0]
    
    load:
        mov ah, bl ; ah  =s[i - 1]
        lodsb ; al = s[i]  
        mov bl, al ; bl = s[i]
        mul ah ; ax = al * ah
        stosw  ; se salveaza in edi pe ax 
    loop load
    
    xor eax, eax
    mov esi, d
    mov ecx, l
    
    write:
        push ecx
        
        lodsw
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop write
    

    push    dword 0      
    call    [exit]   