bits 32
global cdecl

segment data use32 class=data
    adresa_sir_initial dd 0
    adresa_sir_suma dd 0
    lungime dd 0
    saisprezece dd 16
segment code use32 class=code
        
    cdecl:
        
        mov eax, [esp + 12]
        mov [adresa_sir_initial], eax
        
        mov eax, [esp + 8]
        mov [adresa_sir_suma], eax
        
        mov eax, [esp + 4]
        mov [lungime], eax
        
        mov esi, [adresa_sir_initial]
        mov edi, [adresa_sir_suma]
        mov ecx, [lungime]
        incarca_dword:
            push ecx
            
            lodsd
            xor ebx, ebx
                aduna_cifre:
                    xor edx, edx
                    div dword [saisprezece]
                    
                    add ebx, edx
                    
                    cmp eax, 0
                    jne aduna_cifre
                    
                mov eax, ebx
                stosd
                
            pop ecx
        loop incarca_dword
        ret