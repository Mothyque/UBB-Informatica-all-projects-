bits 32

global start

extern exit, fprintf, fopen, fclose
import exit msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
    text db "23yqihfug2vbn1ucib284gh1ABHYSFDHB", 0
    len equ $ - text
    filename db "output.txt", 0
    mode db "w", 0
    descriptor_fis dd -1
    text_nou times len db 0
    
segment data use32 class=code
start:

    push dword mode
    push dword filename
    call [fopen]
    add esp, 2 * 4
    
    mov [descriptor_fis], eax
    
    mov esi, text
    mov edi, text_nou
    mov ecx, len
    modifica_sir:
        lodsb 
        cmp al, "a"
        jae transforma
        
        stosb
        jmp next
        
    transforma:
        mov bl, "a" - "A"
        sub al, bl
        stosb
    next:
    loop modifica_sir
    
    push dword text_nou
    push dword [descriptor_fis]
    call [fprintf]
    add esp, 2 * 4
    
    
    push dword [descriptor_fis]
    call [fclose]
    add esp, 1 * 4
    
    push dword 0
    call [exit]
    
