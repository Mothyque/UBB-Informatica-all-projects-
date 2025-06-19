bits 32
global start        

extern exit, printf, scanf              
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    scanfrmt db "%d", 0
    printfrmt db "%d/%d=%d", 0
    a dd 0
    b dd 0
    
segment code use32 class=code
    start:
        
        push dword a
        push dword scanfrmt 
        call [scanf]
        add esp, 2 * 4
        
        push dword b
        push dword scanfrmt 
        call [scanf]
        add esp, 2 * 4
    
        xor eax, eax
        xor edx, edx
        
        mov eax, [a]
        mov edx, 0
        div dword [b]
        push eax
        
        mov eax, [b]
        push eax
        
        mov eax, [a]
        push eax
        
        push dword printfrmt
        
        call [printf]
        add esp, 4 * 4
        
    
        push    dword 0      
        call    [exit]       