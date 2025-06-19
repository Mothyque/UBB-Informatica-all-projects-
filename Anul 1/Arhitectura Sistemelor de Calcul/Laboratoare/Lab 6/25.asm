bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src db 01011100b, 10001001b, 11100101b 
    len_src equ ($ - src)
    dest times len_src db 0
    intermediar times len_src db 0
    print_frmt db "%d ", 0
   
segment code use32 class = code
start:

    mov esi, src
    mov edi, intermediar
    mov ecx, len_src
    xor dl, dl
    load:
        lodsb
        xor bl, bl
        
        mov dl, 8
        rotate:
            shl bl, 1
            shr al, 1
            adc bl, 0
            dec dl
            cmp dl, 0
            jne rotate
        
        mov al, bl
        stosb
    loop load
    
    mov esi, intermediar
    add esi, len_src - 1
    mov edi, dest
    mov ecx, len_src
    
    inverseaza:
        std
        lodsb
        cld
        stosb
    loop inverseaza
    
    
    mov esi, dest
    mov ecx, len_src
    xor eax, eax

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