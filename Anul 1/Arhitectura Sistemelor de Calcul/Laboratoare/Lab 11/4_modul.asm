bits 32 

global start, afisare_baza2, afisare_baza16, stocheaza_biti

extern exit, printf               
import exit msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    frmt_p_16 db "%xh", 0
    frmt_p_2 db "%d", 0
    spatiu db " ", 0
    newline db 10, 0
    
segment code use32 class=code        
    afisare_baza16:
        mov esi, [esp + 4]
        write:
            push ecx
            
            lodsd 
            push eax
            push dword frmt_p_16 
            call [printf]
            add esp, 2 * 4
            
            push dword spatiu
            call [printf]
            add esp, 1 * 4
            
            pop ecx
        loop write
        push dword newline
        call [printf]
        add esp, 1 * 4
        ret
        
    stocheaza_biti:
        mov esi, [esp + 4]
        mov edi, [esp + 8]
        load_cuvant:
            push ecx
            
            lodsd
            mov bl, 32
            afiseaza_biti:
                dec bl 
                xor edx, edx
                
                shr eax, 1
                adc edx, 0
                
                push eax
                xor eax, eax
                
                mov eax, edx
                stosd
                
                pop eax
                
                cmp bl, 0
                je out
                
                jmp afiseaza_biti
            out:    
                mov eax, ' '
                stosd
                
            pop ecx
        loop load_cuvant
        ret
            
    afisare_baza2:
        mov esi, [esp + 4]
            
        write2:
            push ecx
            lodsd 
            cmp eax, ' '
            je afis_spatiu
            
            cmp eax, 0
            je final
            
            push eax
            push dword frmt_p_2
            call [printf]
            add esp, 2 * 4
            jmp repeta
            
        afis_spatiu:
            push dword spatiu
            call [printf]
            add esp, 1 * 4
            
        repeta:
            pop ecx
            loop write2
        final:
            ret     
