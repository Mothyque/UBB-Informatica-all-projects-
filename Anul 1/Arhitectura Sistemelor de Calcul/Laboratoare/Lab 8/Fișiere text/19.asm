bits 32 
global start        

extern exit, fclose, fopen, fprintf
               
import exit msvcrt.dll    
import fclose msvcrt.dll    
import fopen msvcrt.dll    
import fprintf msvcrt.dll    

segment data use32 class=data
    text db "q5892yh5g9p012gujg1", 0
    len equ ($ - text)
    nume_fisier db "output.txt", 0
    mod_acces db "w", 0
    file_descriptor dd 0
    printfrmt db "%d", 0
segment code use32 class=code
    start:
        
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 2 * 4
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je eroare
        
        mov esi, text
        mov ecx, len
        xor ebx, ebx
        aduna:  
            xor eax, eax
            
            lodsb
            
            cmp al, '1'
            jb nu_e
            
            cmp al, '9'
            ja nu_e
                
            sub al, '0'
            
            add ebx, eax
             
        nu_e:
        loop aduna
        
        push ebx
        push dword printfrmt
        push dword [file_descriptor]
        call [fprintf]
        add esp, 2 * 4
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        eroare:
        push    dword 0      
        call    [exit]       