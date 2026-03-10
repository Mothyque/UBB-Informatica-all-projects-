bits 32 
global start        

extern exit, fopen, fread, fclose,fprintf                
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    
import fread msvcrt.dll   
 
segment data use32 class=data
    nume_fisier_intrare db "input.txt", 0
    nume_fisier_iesire db "output.txt", 0
    mod_acces_intrare db "r", 0
    mod_acces_iesire db "w", 0
    file_descriptor dd 0
    len equ 100
    text times len db 0
    text_final times len db 0 
    caractere_speciale db "!@#$%^&*.:,?;", 0
    lungime equ ($ - caractere_speciale)
    printfrmt db "%s", 0
    
segment code use32 class=code
    start:
        
            
        push dword mod_acces_intrare
        push dword nume_fisier_intrare
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
        mov edi, text_final
        cauta:
            lodsb
            
            cmp al, 0
            je afara
            
            mov ebx, 0
            verifica_caracter_special:
                cmp ebx, lungime
                je nu_e
                
                cmp al, [caractere_speciale + ebx]
                je gasit
                
                inc ebx
                jmp verifica_caracter_special
            nu_e:
                stosb
                jmp next
            
            gasit:
                mov al, 'X'
                stosb
        next:
            jmp cauta
        afara:
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        push dword mod_acces_iesire
        push dword nume_fisier_iesire
        call [fopen]
        add esp, 2 * 4
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je final
        
        push dword text_final
        push dword [file_descriptor]
        call [fprintf]
        add esp, 2 * 4
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        
        
        final:
        push    dword 0      
        call    [exit]       
