bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    a dd 0 
    b dd 0
    scanfrmtdecimal db "%d", 0
    scanfrmthexa db "%x", 0
    c dd 0
    printfrmt db "%d", 0

segment data use32 class=code

start:
    
    push dword a
    push dword scanfrmtdecimal
    call [scanf]
    add esp, 2 * 4
    
    push dword b
    push dword scanfrmthexa
    call [scanf]
    add esp, 2 * 4
    
    
    mov eax, [a]
    add eax, dword [b]
    xor ebx, ebx
    
    mov ecx, 32
    testeaza:
        test eax, 1
        jz par
        
        
        inc ebx
        
    par:
        shr eax, 1
    loop testeaza
    
    push ebx
    push printfrmt
    call [printf]
    add esp, 2 * 4
    push dword 0
    call [exit]
    