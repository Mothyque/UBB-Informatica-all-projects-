bits 32

global functie

extern printf
import printf msvcrt.dll

segment data use32 class=data
    adresa_sir_sursa dd 0 
    lungime dd 0
    inceput dd 0
    final dd 0
    lungime_maxim dd 0 
    format db "%xH ", 0
segment code use32 class=code
    functie:
        mov eax, [esp + 12]
        mov [adresa_sir_sursa], eax
        
        
        mov eax, [esp + 8]
        mov edi, eax
        
        mov eax, [esp + 4]
        mov [lungime], eax
        
        xor ebx, ebx
        mov ecx, [lungime]
        mov esi, 0
        incarca_cuvant:
            push ecx
            
            mov ecx, [adresa_sir_sursa]
            mov al, [ecx + esi]
            
            test al, 1
            jz par
            
            inc bl
            jmp peste
            
            par:
                mov al, [lungime_maxim]
                
                cmp bl, al
                jbe nu_e
                
                mov [lungime_maxim], bl
                xor eax, eax
                mov eax, esi
                mov [final], al
                
                xor eax, eax
                mov eax, esi
                sub eax, ebx
                
                mov [inceput], al
                                
                nu_e:
                    xor ebx, ebx
            
            peste:
            inc esi
            
            pop ecx
        loop incarca_cuvant
        
        
        mov esi, [adresa_sir_sursa]
        add esi, [inceput]
        xor edx, edx
        mov edx, [adresa_sir_sursa]
        add edx, [final]
        formeaza_sir:
            cmp esi, edx
            jae final_formeaza
            
            lodsb   
            stosb
            jmp formeaza_sir
            
        final_formeaza:
        ret