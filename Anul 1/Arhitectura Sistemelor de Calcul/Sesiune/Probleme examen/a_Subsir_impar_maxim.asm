bits 32

global start

extern exit, printf, functie
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    sir db 12, 13, 10b, 7, 3, 100, 101b, 11h, 27, -1, 4
    len equ ($ - sir) 
    sir_final times len db  0
    printf_frmt db "%xH ", 0

segment code use32 class=code
    start:
        
        push dword sir
        push dword sir_final
        push dword len
        call functie
        
        xor eax, eax
        mov esi, sir_final
        afisare:
            lodsb
            
            cmp al, 0
            je iesi
            
            push eax
            push dword printf_frmt
            call [printf]
            add esp, 2 * 4
            
            jmp afisare
        iesi:   
        push dword 0
        call [exit]