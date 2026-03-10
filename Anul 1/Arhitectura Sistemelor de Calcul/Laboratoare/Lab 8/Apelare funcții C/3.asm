bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a dd 0 
    b dd 0
    rezultat dq 0 
    scanfrmtdecimal db "%d", 0
    printfrmt db "%d + %d = %d", 0

segment data use32 class=code

start:
    
    push dword a
    push scanfrmtdecimal
    call [scanf]
    add esp, 2 * 4
    
    push dword b
    push scanfrmtdecimal
    call [scanf]
    add esp, 2 * 4

    mov eax, [a]
    add eax, dword [b]
    
    push eax
    push dword [b]
    push dword [a]
    push printfrmt
    call[printf]
    add esp, 2 * 4
    
    
    
    push dword 0
    call [exit]
    