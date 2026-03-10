bits 32 
global start        

extern exit, printf, fopen, fclose, fread               
import exit msvcrt.dll    
import printf msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import fread msvcrt.dll    

segment data use32 class=data
    mod_acces db "r", 0
    nume_fisier db "input.txt",0 
    file_descriptor dd 0
    len equ 100
    text times len db 0
    printfrmt db "Numarul de cuvinte este:%d!", 0
    
segment code use32 class=code
    start:
        
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 2 * 4
        
        mov [file_descriptor], eax
        
        mov eax, 0
        je eroare
        
        push dword [file_descriptor]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp, 4 * 4
        
        mov esi, text
        mov ebx, 0
        mov edx, 0
        cauta_cuvinte:
            lodsb
                
            cmp al, 0
            je finish
            
            cmp al, ' '
            je cuvant
            
            cmp al, '.'
            je cuvant
            
        cuvant:
            cmp edx, 0
            je nu_era_cuvant
            
        nu_era_cuvant:
        
        finish:
            cmp edx, 0
            je nu_avem_cuvant_la_final
            
            inc ebx
            
        nu_avem_cuvant_la_final:
        
        push ebx
        push dword printfrmt
        call [printf]
        add esp, 2 * 4
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        eroare:
        push    dword 0      
        call    [exit]     
