bits 32 

global start        

extern exit, citeste_sir, transforma_in_numere, printf, cifre_in_numere              
import exit msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    sir times 100 db 0
    sir_aux times 100 db 0
    sir_final times 100 dd 0
    
    printfrmt db "%xh ", 0
segment code use32  class=code
    start:
        
        push dword sir
        call citeste_sir
        
        push dword sir
        push dword sir_aux
        call transforma_in_numere
        
        push dword sir_aux
        push dword sir_final
        call cifre_in_numere
        
        mov esi, sir_final
        xor eax, eax
        write:
            lodsd
            
            cmp eax, "."
            je gata
            
            push eax
            push dword printfrmt
            call [printf]
            add esp, 2 * 4
            jmp write
        gata:
        push    dword 0      
        call    [exit]      
