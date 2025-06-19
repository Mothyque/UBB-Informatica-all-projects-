bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a dw 0 
    b dw 0
    scanfrmt db "%d", 0
    c dd 0
    printfrmt db "%08xh", 0

segment data use32 class=code

start:
    
    xor edx, edx
    mov eax, a
    push eax
    push dword scanfrmt
    call [scanf]
    add esp, 2 * 4
    
    mov eax, b
    push eax
    push dword scanfrmt
    call [scanf]
    add esp, 2 * 4
    
    mov ax, [a]
    mov bx, [b]
    sub ax, bx
    mov edx, eax
    shl edx, 16
    
    mov ax, [a]
    mov bx, [b]
    add ax, bx
    add edx, eax
    
    mov [c], edx
    
    
    
    push dword [c]
    push dword printfrmt
    call [printf]
    add esp, 2 * 4
    
    
    push dword 0
    call [exit]
    