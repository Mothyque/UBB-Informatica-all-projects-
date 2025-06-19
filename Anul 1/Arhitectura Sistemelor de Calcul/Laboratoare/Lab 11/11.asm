bits 32 

global start        

extern exit,citire_sir, transforma_in_numere, printf, scrie_sir               
import exit msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    sir_initial resb 100
    sir_numere resd 25
    
segment code use32 class=code
    start:
 
        push sir_initial
        call citire_sir
        
        push sir_initial
        push sir_numere
        call transforma_in_numere
        
        push sir_numere
        call scrie_sir
            
        push    dword 0      
        call    [exit]       
