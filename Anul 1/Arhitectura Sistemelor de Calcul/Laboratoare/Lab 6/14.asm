bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 12345607h, 1A2B3C15h
    
    len_src equ ($ - src) / 4
    len_dest equ len_src * 4
    dest times len_dest db 0
    print_frmt db "%02xh ", 0
segment code use32 class = code
start:
    
    xor eax, eax
    xor edx, edx
    xor ebx, ebx
    mov esi, src
    mov edi, dest
    mov ecx, len_src
    
    loadbytes:
        lodsd
        mov edx, eax
        xor eax, eax
        
        mov ebx, edx
        and ebx, 00000000000000000000000011111111b
        mov eax, ebx
        stosb
        xor eax, eax
        
        mov ebx, edx
        and ebx, 00000000000000001111111100000000b
        shr ebx, 8
        mov eax, ebx
        stosb
        
        xor eax, eax
        
        mov ebx, edx
        and ebx, 00000000111111110000000000000000b
        shr ebx, 16
        mov eax, ebx
        stosb
        xor eax, eax
        
        mov ebx, edx
        and ebx, 11111111000000000000000000000000b
        shr ebx, 24
        mov eax, ebx
        stosb
        xor eax, eax
    loop loadbytes
    
    mov ecx, len_dest
    sorteaza:
        dec ecx
        mov esi, dest
        mov edi, ecx
        
        inner_loop:
            mov al, [esi]
            mov bl, [esi + 1]
            cmp al, bl
            jbe no_swap
            
            mov [esi], bl
            mov [esi + 1], al
            
            no_swap:
                inc esi
                dec edi
                jnz inner_loop
            
            cmp ecx, 1
            jg sorteaza
        
    xor eax, eax
    mov esi, dest
    mov ecx, len_dest
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