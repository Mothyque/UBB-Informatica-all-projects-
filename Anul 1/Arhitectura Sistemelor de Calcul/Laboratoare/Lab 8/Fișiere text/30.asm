bits 32 
global start        

extern exit, fopen, fclose, fprintf, gets
               
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    
import gets msvcrt.dll    

segment data use32 class=data
    mod_acces db "w", 0
    nume_fisier db "output.txt", 0
    len equ 32
    cuvant times len db 0
    sir_scris times 100 db 0
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
        mov edi, sir_scris
        citeste_cuvinte:
            push dword cuvant
            call [gets]
            add esp, 1 * 4
            
            mov esi, cuvant
            verifica_cuvant:
                lodsb
                
                cmp al, '$'
                je nu_mai_citi
                
                cmp al, 0
                je am_terminat_cuvant
            
                cmp al, '0'
                jb nu_e
                
                cmp al, '9'
                ja nu_e
                
                jmp stocheaza_cuvant
        
        nu_e:
            jmp verifica_cuvant
        
        stocheaza_cuvant:
            mov esi, cuvant
            stocheaza:
                lodsb
                
                cmp al, 0
                je am_terminat_de_stocat
                
                stosb
                jmp stocheaza
            am_terminat_de_stocat:
                mov al, ' '
                stosb
                
        am_terminat_cuvant:
            jmp citeste_cuvinte
        
        nu_mai_citi:
        
        push dword sir_scris
        push dword [file_descriptor]
        call [fprintf]
        add esp, 2 * 4
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
      
        eroare:
        
        push    dword 0      
        call    [exit]       