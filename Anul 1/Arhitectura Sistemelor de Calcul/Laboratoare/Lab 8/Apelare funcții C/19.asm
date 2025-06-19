bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a db 0 
    b dw 0
    scanfrmtbyte db "%hhx", 0
    scanfrmtword db "%hx", 0
    c dd 0
    da db "DA", 0
    nu db "NU", 0

segment data use32 class=code

start:
    xor eax, eax
    
    mov eax, a
    push eax
    push dword scanfrmtbyte
    call [scanf]
    add esp, 2 * 4
    
    mov eax, b
    push eax
    push dword scanfrmtword
    call [scanf]
    add esp, 2 * 4
    
    
    
    xor edx, edx
    xor ebx, ebx
    xor eax, eax
    
    mov bx, [b]
    mov al, [a]
    mov ecx, 16
    roteste:
        rol bx, 1
        
        cmp bl,al 
        je yes
        
    loop roteste
    jmp no
    
    yes:
        push da
        call [printf]
        add esp, 1 * 4
        jmp finish
        
    no:
        push nu
        call [printf]
        add esp, 1 * 4
    
    finish:
    push dword 0
    call [exit]
    