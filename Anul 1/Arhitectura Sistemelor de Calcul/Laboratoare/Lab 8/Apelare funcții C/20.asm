bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a dd 0 
    b dd 0
    scanfrmt db "%x", 0
    suma dd 0 
    dif dd 0
    newline db 10, 0
    printfrmtsum db "suma este: %xh", 0
    printfrmtdif db "diferenta este: %xh", 0

segment data use32 class=code

start:

    push dword a
    push dword scanfrmt
    call [scanf]
    add esp, 2 * 4
    
    push dword b
    push dword scanfrmt
    call [scanf]
    add esp, 2 * 4
    
    xor eax, eax
    xor ebx, ebx
    
    mov eax, [a]
    mov ebx, [b]
    
    and eax, 0x0000FFFF
    and ebx, 0x0000FFFF
    add eax, ebx
    push eax
    push dword printfrmtsum
    call [printf]
    add esp, 2 * 4
    O
    push dword newline
    call [printf]
    add esp, 1 * 4
    
    
    mov eax, [a]
    mov ebx, [b]
    and eax, 0xFFFF0000
    and ebx, 0xFFFF0000
    shr eax, 16
    shr ebx, 16
    sub eax, ebx
    push eax
    push dword printfrmtdif
    call [printf]
    add esp, 2 * 4
    
    
    push dword 0
    call [exit]
    