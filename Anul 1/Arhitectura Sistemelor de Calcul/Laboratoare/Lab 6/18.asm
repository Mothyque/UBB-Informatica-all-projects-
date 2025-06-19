bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 12AB5678h, 1256ABCDh, 12344344h 
    len_src equ  ($ - src) / 4
    len_sort equ (len_src * 2)
    sir_high times len_src dw 0
    dest times len_src dd 0
    printfrmt db "%Xh " , 0
    newline db 10

segment code use32 class = code
start:
    
    mov esi, src
    mov edi, sir_high
    mov ecx, len_src
    loadhigh:
        lodsd
        and eax, 11111111111111110000000000000000b
        shr eax, 16
        stosw
    loop loadhigh
    

    mov esi, sir_high
    mov ecx, len_src
    dec ecx
    outer_loop:
        push ecx
        
        mov ax, [esi]
        
        mov ebx, 2
        
        inner_loop:
             mov dx, [esi + ebx]
             
             cmp ax, dx
             jb skip_swap
             
             mov [esi + ebx], ax
             mov [esi], dx
             mov ax, dx
        
        skip_swap:
            add ebx, 2
            cmp ebx, len_sort
            je next
        loop inner_loop
        
        next:
            add esi, 2
            pop ecx
    loop outer_loop
    
    mov esi, sir_high
    mov edi, dest
    xor ebx, ebx
    mov ecx, len_src
    loaddest:
        
        xor edx, edx
        xor eax, eax
        lodsw
        and eax, 00000000000000001111111111111111b
        shl eax, 16
        
        mov edx, [src + ebx]
        add ebx, 4
        and edx, 00000000000000001111111111111111b
        or eax, edx
        
        stosd
    loop loaddest
    
    mov esi, dest
    mov ecx, len_src
    writehigh:
        push ecx
        
        lodsd
        push eax
        push printfrmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop writehigh
    
    push dword 0
    call [exit]