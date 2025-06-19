bits 32 
global start        

extern exit, fopen, fprintf, fclose               
import exit msvcrt.dll  
  
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    

segment data use32 class=data
    nume_fisier db "output.txt", 0
    mod_acces db "w", 0
    text db "ANdrie are 15 ANisorI si II PLaCe MaTTematica.!!", 0
    len equ ($ - text)
    text_nou times len db 0
    file_descriptor dd 0
    
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
        mov edi, text_nou
        incarca:
            lodsb
            
            cmp al, 'A'
            jb nu_e
            
            cmp al, 'Z'
            ja nu_e
            
            add al, 32
            
        nu_e:
            stosb
        loop incarca
        
        push dword text_nou
        push dword [file_descriptor]
        call [fprintf]
        add esp, 2 * 4
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        eroare
        push    dword 0      
        call    [exit]       