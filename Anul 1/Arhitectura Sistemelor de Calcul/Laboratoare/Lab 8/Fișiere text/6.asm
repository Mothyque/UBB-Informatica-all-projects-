bits 32 
global start        

extern exit, fread, fopen, fclose, printf              
import exit msvcrt.dll    
import fclose msvcrt.dll    
import printf msvcrt.dll    
import fopen msvcrt.dll    
import fread msvcrt.dll    


segment data use32 class=data
    nume_fisier db "input.txt", 0
    len equ 100
    text times len db 0
    cifre times 11 dd 0
    mod_acces db "r", 0
    file_descriptor dd 0
    print_frmt db "Cifra care apare cel mai des este: %d si apare de %d ori", 0
    cifra_maxima dd 0
    frecventa_maxima dd 0
    
    
segment code use32 class=code
    start:
        
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 2 * 4
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je eroare
        
        push dword [file_descriptor]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp, 4 * 4
        
        mov ecx, 0
        incarca:
            cmp ecx, 10 
            je final
            xor ebx, ebx
            mov esi, text
            compara:
                xor eax, eax
                lodsb
                
                cmp al, 0
                je am_terminat
                
                cmp al, '0'
                jb nu_e
                
                cmp al, '9'
                ja nu_e
                
                sub al, '0'
                
                cmp eax, ecx
                jne nu_e
                
                inc ebx
                
            nu_e:                  
               jmp compara
            am_terminat:
                xor edx, edx
                mov edx, [frecventa_maxima]
                cmp ebx, edx
                jb nu_am_gasit
                
                mov [frecventa_maxima], ebx
                mov [cifra_maxima], ecx
            
            nu_am_gasit:
                inc ecx
                jmp incarca
        
        final:
        push dword [frecventa_maxima]
        push dword [cifra_maxima]
        push dword print_frmt
        call [printf]
        add esp, 3 * 4
        
        
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        eroare:
        push    dword 0      
        call    [exit]       
