bits 32 
global _transforma_in_numere        

segment data public data use32
    adresa_sir_sursa dd 0
    adresa_sir_destinatie dd 0
    
segment code public code use32 
    _transforma_in_numere:
        push ebp
        mov ebp, esp
                
        mov eax, [ebp + 8]
        mov [adresa_sir_sursa], eax
        
        mov eax, [ebp + 12]
        mov [adresa_sir_destinatie], eax
        
        mov esi, [adresa_sir_sursa]
        mov edi, [adresa_sir_destinatie]
        xor eax, eax
        xor edx, edx
        xor ebx, ebx
        .incarca:
            lodsb
            
            cmp al, 0
            je .finish
            
            cmp al, " "
            je .spatiu
        
            
            cmp al, '1'
            je .unu
            
            shl edx, 1
            jmp .incarca
        
        .unu:
            shl edx, 1
            or edx, 1
            jmp .incarca
            
        .spatiu:
            mov eax, edx
            stosd   
            xor eax, eax
            xor edx, edx
            inc ebx
            jmp .incarca
        
        .finish:
            mov eax, edx
            stosd
            
            xor eax, eax
            xor edx, edx
            
            mov eax, ebx
            
            mov esp, ebp
            pop ebp
            
            ret
    