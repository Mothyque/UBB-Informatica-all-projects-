bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 12345678h, 1A2C3C4Dh, 98FCDD76h, 12783A2Bh
    len_src equ ($ - src) / 4
    intermediar times len_src db 0
    dest dd 0FFFFFFFFh
    zece db 10
    print_frmt db "%08Xh ", 0     
segment code use32 class = code
start:
    
    mov esi, src
    mov ecx, len_src
    mov edi, intermediar
    
    loadbyte:
        xor eax, eax  
        lodsd
        
        and eax, 00000000000000001111111100000000b
        shr eax, 8
        test eax, 1
        jnz next

        stosb
        
        next:
        
    loop loadbyte
    
    mov ebx, 11111111111111111111111111111111b
    mov esi, intermediar
    mov ecx, len_src
    xor edx, edx
    
    createDword:
        or eax, 11111111111111111111111111111111b
        lodsb
        cmp al, 0
        je impar
        rol eax, 24
        and ebx, eax
        ror ebx, 8
        jmp nextbyte
        
        impar:
            inc edx
        nextbyte:
    loop createDword
    
    sub edx, 1
    mov ecx, edx
    jecxz final
    rotate:
        ror ebx, 8
    loop rotate
    final:
    push ebx
    push print_frmt
    call [printf]
    push dword 0
    call [exit]
     
        
        