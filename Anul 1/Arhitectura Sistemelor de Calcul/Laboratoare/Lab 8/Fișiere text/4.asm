bits 32 
global start        

extern exit, fopen, fread, fclose, printf               
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import printf msvcrt.dll    
import fread msvcrt.dll   
 
segment data use32 class=data
    nume_fisier db "input.txt", 0
    mod_acces db "r", 0
    file_descriptor dd 0
    len equ 100
    text times len db 0
    printfrmt db  "Numarul de cifre impare din textul dat este: %d", 0
    
segment code use32 class=code
    start:
        
        xor ebx, ebx
            
        push dword mod_acces
        push dword nume_fisier
        call [fopen] 
        add esp, 2 * 4
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je final
        
        push dword [file_descriptor]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp, 4 * 4
        
        
        mov esi, text
        cauta_cifreimpare:
            lodsb
            
            cmp al, 0
            je afara
            
            cmp al, '1'
            jb nu_e
            
            cmp al, '9'
            ja nu_e
            
            sub al, '0'
            test al, 1
            jz nu_e 
            
            inc ebx
        
        nu_e:
            jmp cauta_cifreimpare
        afara:
        push ebx
        push dword printfrmt
        call [printf]
        add esp, 2 * 4
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        final:
        push    dword 0      
        call    [exit]       
