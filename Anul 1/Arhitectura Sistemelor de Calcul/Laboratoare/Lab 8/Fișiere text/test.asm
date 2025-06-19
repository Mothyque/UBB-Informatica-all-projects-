bits 32 
global start        

extern exit, fopen, fclose, fread, printf, fprintf
              
import exit msvcrt.dll    
import fopen msvcrt.dll    
import printf msvcrt.dll    
import fread msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    


segment data use32 class=data
    nume_fisier_intrare db "input.txt", 0
    nume_fisier_iesire db "output-", 0
    len equ ($ - nume_fisier_iesire + 5)
    nume_fisier_curent times len db 0
    text db "abcdefghij", 0
    text_de_scris times 8 db 0
    mod_acces_intrare db "r", 0
    mod_acces_iesire db "w", 0
    file_descriptor dd 0
    spatiu db " ", 0
    numar db 0
    
segment code use32 class=code
    start:
        
        push dword mod_acces_intrare
        push dword nume_fisier_intrare
        call [fopen]
        add esp, 2 * 4
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je eroare
        
        push dword [file_descriptor]
        push dword 1
        push dword 1
        push dword numar
        call [fread]
            
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
          
        xor edx, edx
        xor ecx, ecx
        mov cl, [numar]
        sub cl, '0'
        mov edx, 0
        creeaza_fisiere:
        push ecx
            mov ecx, 7
            mov esi, nume_fisier_iesire
            mov edi, nume_fisier_curent
            copiaza:
                movsb
            loop copiaza
            
            adauga_sufix:
                add dl, '0'
                mov al, dl
                stosb
                sub dl, '0' 
                
                mov eax, '.txt'
                stosd
                
                push dword mod_acces_iesire
                push dword nume_fisier_curent
                call [fopen]
                add esp, 2 * 4
                
                mov [file_descriptor], eax
                
                cmp eax, 0
                je eroare
                
                formam_textul:
                    mov esi, text
                    mov edi, text_de_scris
                    mov ebx, edx
                    inc ebx
                    bucla:
                        cmp ebx, 0
                        je scrie_in_fisier
                        
                        std 
                        lodsb
                        cld
                        stosb
                        dec ebx
                        jmp bucla
                        
                        
                scrie_in_fisier:     
                push dword text_de_scris
                push dword [file_descriptor]
                call [fprintf]
                add esp, 2 * 4
                
                push dword [file_descriptor]
                call [fclose]
                add esp, 1 * 4
            
            inc dl
            
            pop ecx
        loop creeaza_fisiere
                
                
            
        
    finish:
    eroare:
    
        push    dword 0      
        call    [exit]     
