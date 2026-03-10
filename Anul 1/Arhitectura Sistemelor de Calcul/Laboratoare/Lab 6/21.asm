bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dw 12345, 20778, 4596 
    len_src equ  ($ - src) / 2
    len_aux equ (len_src * 2)
    len_dest equ (len_src * 5)
    sir_final times len_dest dw 10
    sir_aux times len_dest dw 10
    zece dw 10
    printfrmt db "%d ", 0
    

segment code use32 class = code
start:
    
    cld
    xor ebx, ebx
    xor edx, edx
    xor eax, eax
    mov esi, src
    mov edi, sir_aux
    add esi, len_aux
    mov ecx, len_aux
    inc ecx
    
    loadword:
        std
        lodsw
        cld
    imparte:
        xor edx, edx
        xor ebx, ebx

        cmp ax, 0
        je next
        
        xor dx, dx
        mov bx, [zece]
        div bx
        
        mov bx, dx ; bx - restul
        mov dx, ax ; dx - catul
        mov ax, bx ; ax - restul
        
        stosw
        
        mov ax, dx
        jmp imparte
        
    next:
    loop loadword
    
    mov esi, sir_aux
    mov edi, sir_final
    add esi, len_dest
    add esi, len_dest
    mov ecx, len_dest
    dec ecx
    loadinvers:
        std
        lodsw
        
        cmp ax, 10
        je nuadauga
        
        cld
        stosw
    nuadauga:
    loop loadinvers
        
    mov esi, sir_final
    mov ecx, len_dest
    write:
        push ecx
        lodsw
        cmp ax, 10
        je nuafisa
        
        push eax
        push printfrmt
        call[printf]
        add esp, 2 * 4
        pop ecx
    nuafisa:
    loop write
    
        
    push dword 0
    call [exit]