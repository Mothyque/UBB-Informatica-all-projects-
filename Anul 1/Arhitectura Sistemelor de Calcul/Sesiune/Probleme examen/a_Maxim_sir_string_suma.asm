bits 32

global start

extern exit, procedura, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    sir dd 1234A678h, 12345678h, 1AC3B47Dh, 0xFEDC9876
    len equ ($ - sir) / 4
    sir_string times len db 0
    newline db 10, 0
    print_string db "Sirul este %s", 0
    print_suma db "Suma este %d", 0
    
segment code use32 class=code
    start:
        push dword sir
        push dword sir_string
        push dword len
        call procedura
        
        push dword sir_string
        push dword print_string
        call [printf]
        add esp, 2 * 4
        
        push ebx
        push dword print_suma
        call [printf]
        add esp, 2 * 4
        
        push dword 0
        call [exit]
