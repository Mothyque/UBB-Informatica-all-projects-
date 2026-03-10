bits 32 

global citire_sir, afisare, creeaza_sir_cifre, creeaza_numere   

extern exit, gets, printf           
import exit msvcrt.dll    
import printf msvcrt.dll    
import gets msvcrt.dll    
segment data use32 class=data    
    formart_p db "%d ", 0
    formart_r db "Citeste sir: ", 0
    mesaj db "Sirul este: ", 0
    zece dd 10
segment code use32 class=code
    
    citire_sir:
        push dword formart_r
        call [printf]
        add esp, 1 * 4
        
        mov eax, [esp + 4]
        push eax
        call [gets]
        add esp, 1 * 4
        ret
    
    creeaza_sir_cifre:
        xor eax, eax
        mov esi, [esp + 8]
        mov edi, [esp + 4]
        citeste_cifra:
            lodsb
            
            cmp al, 0
            je final_sir
            
            cmp al, ' '
            je numar_nou
            
            sub al, '0'
            stosb
            jmp next
        
        numar_nou:
            mov al, ' '
            stosb
            jmp next
        
        next:
            jmp citeste_cifra
    final_sir:
        mov al,  ' '
        stosb
        mov al, '.'
        stosb
        ret
    
    creeaza_numere:
        xor ebx, ebx
        xor eax, eax
        xor ecx, ecx
        mov esi, [esp + 8]
        mov edi, [esp + 4]
        citeste_cifre:
            lodsb
            
            cmp al, '.'
            je gata
            
            cmp al, ' ' ; daca avem spatiu am construit deja un numar
            je next_numar
            
            mov bl, al ;bl avem cifra
            mov eax, ecx ;recuperam numarul pe care il creem
            mul dword [zece] ; inmultim cu 10 ca sa adaugam cifra
            add al, bl ; adaugam noua cifra
            mov ecx, eax ;salvam numarul in ecx pentru a putea face load
            xor eax, eax
            xor bl, bl
            jmp next_cifra
        
        next_numar:
            mov eax, ecx ;recuperam numarul pe care il creem ca sa il stocam in sir_final
            stosd
            xor eax, eax
            xor ecx, ecx
            jmp next_cifra
            
        next_cifra:
            jmp citeste_cifre     
    gata:
        mov eax, "." ; punem punct dupa ultimul numar citit
        stosd
        ret
        
    afisare:
        
        push mesaj
        call [printf]
        add esp, 1 * 4
        
        mov esi, [esp + 4]
        write:
            xor eax, eax
            lodsd
            
            cmp eax, "."
            je final_write
            
            push eax
            push formart_p
            call [printf]
            add esp, 2 * 4
            jmp write
            
        final_write:
            ret
     
        push    dword 0     
        call    [exit]      
