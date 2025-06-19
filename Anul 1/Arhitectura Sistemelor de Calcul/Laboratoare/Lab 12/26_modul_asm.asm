bits 32 

global _nr_maxim

segment data public data use32
    adresa_sir_sursa dd 0
    zece dd 10
    maxim dd 0
segment code public code use32
    _nr_maxim:
        push ebp
        mov ebp, esp
         
        mov eax, [ebp + 8]
        mov [adresa_sir_sursa], eax
        
        mov esi, [adresa_sir_sursa]
        xor eax, eax
        xor edx, edx
        
        incarca_numere:
            lodsb
            
            cmp al, 0
            je am_terminat
            
            cmp al, ' '
            je spatiu
            
            sub al, '0'
            push eax
            
            mov eax, edx
            mov edx, 0
            
            mul dword [zece]
            mov edx, 0
            
            mov edx, eax
            mov eax, 0
            
            pop eax
            add edx, eax
            
            xor eax, eax
            jmp incarca_numere
            
        spatiu:
            xor eax, eax
            
            mov eax, edx
            mov edx, [maxim]
            cmp eax, edx
            jb nu_am_gasit
            
            mov [maxim], eax
            
            xor eax, eax
            xor edx, edx
            
        nu_am_gasit: 
            xor edx, edx
            xor eax, eax
            jmp incarca_numere
            
            
        am_terminat:
            
            mov eax, [maxim]
            cmp eax, edx
            ja nici_la_final
            
            mov dword [maxim], 0
            mov [maxim], edx
            
        nici_la_final:
            
            mov eax, [maxim]
            
            mov esp, ebp
            pop ebp
            
            ret 
        