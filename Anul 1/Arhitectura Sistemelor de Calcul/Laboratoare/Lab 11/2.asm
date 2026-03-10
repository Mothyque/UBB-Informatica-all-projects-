bits 32 

global start        

extern exit, citire_sir, afisare, printf, creeaza_sir_cifre, creeaza_numere           
import exit msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    len equ 100
    text times len db 0
    sir_aux times len * 4 db 0
    sir_final times len dd 0

segment code use32 class=code
    start:
        
        push dword text
        call citire_sir
        
        push dword text
        push dword sir_aux
        call creeaza_sir_cifre
        
        push dword sir_aux
        push dword sir_final
        call creeaza_numere
        
        push dword sir_final
        call afisare
        
        push    dword 0     
        call    [exit]      
