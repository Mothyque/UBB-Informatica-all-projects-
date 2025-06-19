bits 32 

global concatenare_cif, concatenare_carac, afisare_siruri   

extern exit, printf
import exit msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    printfrmt db "Sirul este: %s", 0
    newline db 10, 0
    
segment code use32 class=code
    
    concatenare_cif:
        mov esi, [esp + 8]
        mov edi, [esp + 12]
        loadcif1:
            lodsb
            
            cmp al, 0 
            je finish1
            
            cmp al, '0'
            jb next1
            
            cmp al, '9'
            ja next1
            
            stosb
            
            next1:
            jmp loadcif1
        
            finish1:

        mov esi, [esp + 4]
        loadcif2:
            lodsb
            
            cmp al, 0 
            je finish2
            
            cmp al, '0'
            jb next2
            
            cmp al, '9'
            ja next2
            
            stosb
            
            next2:
            jmp loadcif2
        
            finish2:
                ret
        
    concatenare_carac:
        mov esi, [esp + 4]
        mov edi, [esp + 12]
        concateneaza_doi:
            lodsb
            
            cmp al, 0
            je next_sir
            
            stosb
            jmp concateneaza_doi
        next_sir:
        mov esi, [esp + 8]
        concateneaza_unu:
            lodsb
            
            cmp al, 0
            je final
            
            stosb
            jmp concateneaza_unu
            
        final:
        ret
    
    afisare_siruri:
        mov esi, [esp + 4]
        push esi
        push printfrmt
        call [printf]
        add esp, 2 * 4
        
        push dword newline
        call [printf]
        add esp, 1 * 4
        
        ret
        
    
    push    dword 0      
    call    [exit]      
