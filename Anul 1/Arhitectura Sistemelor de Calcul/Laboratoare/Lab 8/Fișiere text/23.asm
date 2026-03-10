bits 32

global start

extern exit, fprintf, fopen, fclose
import exit msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
    filename db "output.txt", 0
    mode db "w", 0
    descriptor_fis dd -1
    number dw 0xF873
    saisprezece dw 16
    sir_cifre times 9 db 0
    hexdigits db "0123456789ABCDEF", 0
    
segment data use32 class=code
start:

    push dword mode
    push dword filename
    call [fopen]
    add esp, 2 * 4
    
    mov [descriptor_fis], eax
    
    mov esi, 7
    xor eax, eax
    xor ebx, ebx
    mov ax, [number]
    imparte:
        xor edx, edx
        
        div word [saisprezece]
        mov bl, [hexdigits + edx]
        mov byte [sir_cifre + esi], 10        ;newline
        dec esi
        mov [sir_cifre + esi], bl
        dec esi
        
        cmp ax, 0
        je finish
        
        jmp imparte
    finish:
    
    push dword sir_cifre
    push dword [descriptor_fis]
    call [fprintf]
    add esp, 2 * 4
    
    
    push dword [descriptor_fis]
    call [fclose]
    add esp, 1 * 4
    
    push dword 0
    call [exit]
    
