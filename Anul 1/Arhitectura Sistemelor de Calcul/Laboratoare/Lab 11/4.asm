bits 32 

global start        

extern exit, afisare_baza16, afisare_baza2, stocheaza_biti          
import exit msvcrt.dll    
segment data use32 class=data
    sir dd 7289, 891, 92, 1, 8, 32
    len_sir equ ($ - sir) / 4
    biti times (len_sir * 5) dd 0

    
segment code use32 class=code
    start:
    
        push dword sir
        mov ecx, len_sir
        call afisare_baza16
            
        push dword biti
        push dword sir
        mov ecx, len_sir
        call stocheaza_biti
        
        push dword sir
        mov ecx, len_sir * 5
        call afisare_baza2
        
        push    dword 0     
        call    [exit]       
