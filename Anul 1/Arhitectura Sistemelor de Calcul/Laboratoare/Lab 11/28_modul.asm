bits 32 
global start, citeste_sir, fa_sir_litere_mici, fa_sir_litere_mari, afiseaza_sir_litere_mici, afiseaza_sir_litere_mari

extern exit, gets, printf          
import exit msvcrt.dll    
import printf msvcrt.dll    
import gets msvcrt.dll    
segment data use32 class=data
       printfrmt db "Citeste sir: ", 0
       litere_mici db "Sirul de litere mici este: ", 0
       litere_mari db "Sirul de litere mari este: ", 0
       frmt db  "%c", 0
       newline db 10, 0
        
segment code use32 class=code
    citeste_sir:
        push dword printfrmt
        call [printf]
        add esp, 1 * 4
        
        
        mov eax, [esp + 4]
        push eax
        call [gets]
        add esp, 1 * 4
        ret
    
    fa_sir_litere_mici:
        mov esi, [esp + 4]
        mov edi, [esp + 8]
        
        .formeaza_sir:
            lodsb
            
            cmp al, 0
            je .afara
            
            cmp al, 'a'
            jb .nue
            
            cmp al, 'z'
            ja .nue
            
            stosb
            jmp .formeaza_sir
            
        .nue:    
            jmp .formeaza_sir
        .afara:
            ret
            
    fa_sir_litere_mari:
        mov esi, [esp + 4]
        mov edi, [esp + 8]
        
        .formeaza_sir:
            lodsb
            
            cmp al, 0
            je .afara
            
            cmp al, 'A'
            jb .nue
            
            cmp al, 'Z'
            ja .nue
            
            stosb
            jmp .formeaza_sir
            
        .nue:    
            jmp .formeaza_sir
        .afara:
            ret
        
    afiseaza_sir_litere_mici:
        
        push dword litere_mici
        call [printf]
        add esp, 1 * 4
        
        mov esi, [esp + 4]
        .write:
            lodsb 
            
            cmp al, 0
            je .finish
            
            push eax
            push dword frmt
            call [printf]
            add esp, 2 * 4
            
            jmp .write
        .finish:
            push dword newline
            call [printf]
            add esp, 1 * 4
            ret
    
    afiseaza_sir_litere_mari:
        push dword litere_mari
        call [printf]
        add esp, 1 * 4
        
        mov esi, [esp + 4]
        .write:
            lodsb 
            
            cmp al, 0
            je .    finish
            
            push eax
            push dword frmt
            call [printf]
            add esp, 2 * 4
            jmp .write
        
        .finish:
            ret