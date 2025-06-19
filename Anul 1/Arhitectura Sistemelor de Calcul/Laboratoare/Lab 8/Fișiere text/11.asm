bits 32 
global start        

extern exit, gets, fopen, fclose, fprintf
              
import exit msvcrt.dll
import fclose msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import gets msvcrt.dll

segment data use32 class=data
    nume_fisier db "output.txt", 0
    mod_acces db "w", 0
    len equ 100
    cuvant times len db 0
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
        
        citeste:
            push dword cuvant
            add esp, 1 * 4
            call [gets]
            
            cmp byte [cuvant], '$'
            je afara

            scrie_in_fisier:
                push dword cuvant
                push dword [file_descriptor]
                call [fprintf]
                add esp, 2 * 4
            
            jmp citeste
            
        afara:
        
        push dword [file_descriptor]
        call [fclose]
        
        eroare:
        push    dword 0     
        call    [exit]       
