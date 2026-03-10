bits 32 

global start        

extern exit, concatenare_cif, concatenare_carac, afisare_siruri,printf        
import exit msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    sir1 db "bg 13f7h7unui1v 1bufihnv 12", 0
    len1 equ $ - sir1
    sir2 db "bhksfde 723b hf1893bh 1ui3091", 0
    len2 equ $ - sir2
    
    concatenare1 times (len1 + len2) db 0
    concatenare2 times (len1 + len2) db 0
    concatenare3 times (len1 + len2) db 0
        
    printfrmt db "%s", 0
segment code use32 class=code
    start:
        
        push dword concatenare1
        push dword sir1
        push dword sir2
        call concatenare_cif
        
        push dword concatenare2
        push dword sir2
        push dword sir1
        call concatenare_cif
        
        push dword concatenare3
        push dword sir1
        push dword sir2
        call concatenare_carac
        
        push dword concatenare1
        call afisare_siruri
        
        push dword concatenare2
        call afisare_siruri
        
        push dword concatenare3
        call afisare_siruri
        
        push    dword 0      
        call    [exit]      
