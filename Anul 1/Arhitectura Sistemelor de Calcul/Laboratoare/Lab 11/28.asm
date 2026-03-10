bits 32 
global start        

extern exit, citeste_sir, fa_sir_litere_mici, fa_sir_litere_mari, afiseaza_sir_litere_mari, afiseaza_sir_litere_mici            
import exit msvcrt.dll    
segment data use32 class=data
    len equ 100
    text times len db 0
    litere_mari times len db 0
    litere_mici times len db 0
    
segment code use32 class=code
    start:
    
        push dword text
        call citeste_sir
        
        push dword litere_mici
        push dword text
        call fa_sir_litere_mici
            
        push dword litere_mari
        push dword text
        call fa_sir_litere_mari
        
        push dword litere_mici
        call afiseaza_sir_litere_mici
        
        push dword litere_mari
        call afiseaza_sir_litere_mari
        
        push    dword 0      
        call    [exit]       
