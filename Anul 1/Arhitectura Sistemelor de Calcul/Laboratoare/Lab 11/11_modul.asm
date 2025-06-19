bits 32 

global start, citire_sir, transforma_in_numere, scrie_sir     

extern exit, gets, printf              
import exit msvcrt.dll    
import printf msvcrt.dll    
import gets msvcrt.dll    
segment data use32 class=data
    frmt db "Citeste sir: ", 0
    printfrmt db "Sirul este: ", 0
    frmt_afisare db "%xH ", 0
segment code use32 class=code
    citire_sir:
        
        push dword frmt
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
        xor eax, eax
        xor edx, edx
        
        .incarca:
            clc
            lodsb
            
            cmp al, 0
            je .afara
            
            cmp al, " "
            je .spatiu
        
            
            cmp al, '1'
            je .unu
            
            shl edx, 1
            jmp .incarca
        
        .unu:
            stc 
            rcl edx, 1
            jmp .incarca
            
        .spatiu:
            mov eax, edx
            stosd
            xor eax, eax
            xor edx, edx
            jmp .incarca
        .afara:
            mov eax, edx
            stosd
            
            mov eax, '.'
            stosd
            ret
    
    scrie_sir:
        mov esi, [esp + 4]
        push dword printfrmt
        call [printf]
        add esp, 1 * 4
        
        .write:
            lodsd
            
            cmp eax, '.'
            je .finish
        
            push eax
            push dword frmt_afisare
            call [printf]
            add esp, 2 * 4
            
            jmp .write
        .finish:
        ret
        
