bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a dd 0 
    minim dd 0 
    scanfrmtdecimal db "%d", 0
    printfrmt db "%xh", 0

segment data use32 class=code

start:
    xor eax, eax
    
    push dword minim
    push scanfrmtdecimal
    call [scanf]
    add esp, 2 * 4
    
    mov eax, [minim]
    cmp eax, 0
    je over
    
    xor eax, eax
    
    citeste:
        xor eax, eax
        push dword a
        push scanfrmtdecimal
        call [scanf]
        add esp, 2 * 4
        
        mov eax, [a]
        cmp eax, 0
        je over
        
        cmp eax, [minim]
        ja next
        
        mov [minim], eax
        
    next:
    loop citeste
    
    over:
    mov eax, [minim]
    push eax
    push printfrmt
    call [printf]
    add esp, 2 * 4
    
    push dword 0
    call [exit]
    