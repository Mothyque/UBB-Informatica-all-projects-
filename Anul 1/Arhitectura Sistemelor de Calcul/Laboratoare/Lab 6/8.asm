bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 12345678h, 1A2C3C4Dh, 98FCDC76h
    len_src equ ($ - src) / 4
    intermediar times len_src db 0
    dest times len_src db 0
    zece db 10
    print_frmt db "%0Xh ", 0
    
segment code use32 class = code
start:
    mov esi, src
    mov ecx, len_src
    mov edi, intermediar
    xor eax, eax
    xor edx, edx
    loadDword:
        xor ebx, ebx
        lodsd
        and eax, 00000000111111110000000000000000b
        shr eax, 16
        stosb
                    
    loop loadDword
    
    mov esi, intermediar
    mov ecx, len_src
    mov edi, dest
    
    palindrom:
        xor eax, eax
        lodsb 
        mov bl, al
        cmp bl, 100
        jge treicifre
        
        cmp bl, 10
        jnge store
        
        
        mov ah, 0
        div byte [zece]
        
        cmp ah, al 
        je store
        jmp next
        
        treicifre:
            mov ah, 0
            div byte [zece]
            
            mov dh, ah
            mov ah, 0
            
            div byte [zece]
            
            cmp al, dh
            je store
            jmp next
        
        store:
            mov al, bl
            stosb
        next:
    loop palindrom
    
    mov esi, dest
    mov ecx, len_src
    xor eax, eax
    write:
        push ecx
        lodsb
        cmp al, 0
        je nuafisa
        
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        nuafisa:
        
        pop ecx
    loop write

    push dword 0
    call [exit]
     
        
        