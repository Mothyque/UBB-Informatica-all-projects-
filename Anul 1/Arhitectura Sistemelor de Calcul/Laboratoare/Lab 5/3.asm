bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db ' %d ', 0
    s1 db 1, 2, 3, 4
    l1 equ ($ - s1)
    s2 db 5, 6, 7, 9
    l2 equ ($ - s2)
    len equ 0
    d resb 100
segment code use32 class=code
start:
    xor eax, eax
    mov esi, s1
    mov edi, d
    mov ecx, l1
    
    loads1:
        lodsb ; al = s1[0]
        stosb ; edi = s1[0]
    loop loads1
    
    xor eax, eax
    mov esi, s2
    add esi, l2 - 1
    mov ecx, l2
    
    
    loads2:
        mov al, [esi]
        stosb ; edi = s2[0]
        dec esi
    loop loads2
        
    xor eax, eax
    mov esi, d
    mov ebx, l1
    add ebx, l2
    mov ecx, ebx
    
    
    
    write:
        push ecx
        
        lodsb 
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop write
    
    push dword 0
    call [exit]
        
    
    
    push eax
    push print_frmt
    call[printf]
    add esp, 2 * 4
    
    push dword 0
    call [exit]
       