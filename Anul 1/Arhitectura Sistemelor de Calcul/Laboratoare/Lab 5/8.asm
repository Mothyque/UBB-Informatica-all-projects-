bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %c ', 0
    s db 'a', 'A', 'b', 'B', '2', '%', 'x', 'M'
    l equ ($ - s)
    d resb 100
    
segment code use32 class=code
    start:
    
    mov esi, s
    mov edi, d
    mov ecx, l
    
     loadbyte:
        lodsb             
        cmp al, 'A'       
        jl next           
        cmp al, 'Z'       
        jg next           
        
        stosb             
        
    next:
        loop loadbyte
    
    xor eax, eax
    mov esi, d
    mov ecx, l
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