bits 32 
global start        

extern exit, citeste_a, citeste_b, citeste_c, calcul              
import exit msvcrt.dll    
segment data use32 class=data
    a dd 0
    b dd 0
    c dd 0
    rezultat dd 0
    
segment code use32 class=code
    start:
        
        push dword a
        call citeste_a
        
        push dword b
        call citeste_b
        
        push dword c 
        call citeste_c
        
        mov eax, [a]
        mov ebx, [b]
        mov edx, [c]
        call calcul
        
        push    dword 0      
        call    [exit]       
