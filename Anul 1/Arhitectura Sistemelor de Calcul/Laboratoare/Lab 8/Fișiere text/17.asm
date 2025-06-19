bits 32 
global start        

extern exit, fprintf, fclose, fopen, scanf               
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fclose msvcrt.dll    
import fprintf msvcrt.dll    
import scanf msvcrt.dll    

segment data use32 class=data
    mod_acces db "w", 0
    nume_fisier db "output.txt", 0
    numar dd 0
    file_descriptor dd 0
    sapte dd 7
    scanfrmt db "%d", 0
    printfrmt db "%d ", 0
    
segment code use32 class=code
    start:
        
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 2 * 4
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je eroare
        
        citeste_numere:
            push dword numar 
            push dword scanfrmt
            call [scanf]
            add esp, 2 * 4
            
            cmp dword [numar], 0
            je final
            
            mov eax, [numar]
            mov edx, 0
            
            idiv dword [sapte]
            
            cmp edx, 0
            jne nu_e_div
            
            mov eax, [numar]
            push dword eax
            push dword printfrmt
            push dword [file_descriptor]
            call [fprintf]
            add esp, 3 * 4
            
        
        nu_e_div:
            jmp citeste_numere
        
        final:
        push dword [file_descriptor]
        call [fclose]
        add esp, 1 * 4
        
        eroare:
        push    dword 0      
        call    [exit]     
