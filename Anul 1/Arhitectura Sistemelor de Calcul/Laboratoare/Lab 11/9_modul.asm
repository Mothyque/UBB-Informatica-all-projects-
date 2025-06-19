bits 32 

global start, citeste_sir, transforma_in_numere, cifre_in_numere   

extern exit, printf, gets               
import exit msvcrt.dll    
import gets msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    mesaj db "Citeste sir: ", 0
    printfrmt db "%xh ", 0
    saispe dd 16
segment code use32 class=code
    citeste_sir:
        push dword mesaj
        call [printf]
        add esp, 1 * 4
        
        mov eax, [esp + 4]
        push eax
        call [gets]
        add esp, 1 * 4
        ret
        
    transforma_in_numere:
        mov esi, [esp + 8]
        mov edi, [esp + 4]
        xor ecx, ecx
        xor ebx, ebx
        schimba_caracter:
            lodsb
            
            cmp al, 0
            je final_sir
            
            cmp al, ' '
            je spatiu
             
            cmp al, '9'
            ja litera
            
            sub al, '0'
            jmp spatiu
            
            litera:
                sub al, '7'
                
        spatiu:
            stosb
            
        next:
            jmp schimba_caracter
        final_sir:
            mov al, "."
            stosb
            ret
        
    cifre_in_numere:
        mov edi, [esp + 4]
        mov esi, [esp + 8]
        xor ecx, ecx
        xor ebx, ebx
        calculeaza_numar:
            lodsb
                
            cmp al, "."
            je final_de_tot
            
            cmp al, " "
            je spatiu_cifra
            
            mov bl, al
            mov eax, ecx
            mul dword [saispe]
            
            add eax, ebx
            mov ecx, eax
            
            xor eax, eax
            xor ebx, ebx
            jmp next_cifra
        spatiu_cifra:
            mov eax, ecx
            stosd
            xor ecx, ecx
            xor eax, eax
            
        next_cifra:
            jmp calculeaza_numar
        final_de_tot:
            mov eax, ecx
            stosd
            xor eax, eax
            xor ecx, ecx
            mov eax, '.'
            stosd
            ret
        push    dword 0      
        call    [exit]      
