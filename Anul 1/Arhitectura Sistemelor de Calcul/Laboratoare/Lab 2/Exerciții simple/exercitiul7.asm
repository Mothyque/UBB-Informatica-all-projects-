bits 32


global start        

extern exit              
import exit msvcrt.dll  
extern printf  
import printf msvcrt.dll

segment data use32 class=data
    a dd 256
    b dd 1
    print_frmt db "Rezultatul este %d", 0
   
    
segment code use32 class=code
    start:
        mov EAX, [a]
        xor EDX, EDX
        mov EBX, [b]
        
        div EBX ; EAX / EBX = a / b
        
        push EAX
        push print_frmt
        call[printf]
        
        add esp, 4 * 2 
        
        push    dword 0      
        call    [exit]       
