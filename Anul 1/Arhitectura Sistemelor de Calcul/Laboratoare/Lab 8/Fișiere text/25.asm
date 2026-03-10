bits 32 
global start        

extern exit, fopen, fclose, fprintf
             
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    

segment data use32 class=data
    nume_fisier db "output.txt", 0
    text db "89hfu18 r971480y91t 1y8 89y 14t 80thfbu1 ;[qih8h1", 0
    len equ ($ - text)
    text_nou times len db 0
    mod_acces db "w", 0
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
        mov edi, text_nou
        mov ecx, len
        incarca:
            lodsb
            
            cmp al, ' '
            jne copiaza
        
            mov al, 'S'
        
        copiaza:
            stosb
        loop incarca
        
        push dword text_nou
        push dword [file_descriptor]
        call [fprintf]
        add esp, 2 * 4
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        eroare:
        push    dword 0      
        call    [exit]       
