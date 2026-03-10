bits 32

global start

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    printfrmt db "%d", 0
segment data use32 class=code
start:
    mov al, 8     cbw
    
    push dword 0
    call [exit]
    
