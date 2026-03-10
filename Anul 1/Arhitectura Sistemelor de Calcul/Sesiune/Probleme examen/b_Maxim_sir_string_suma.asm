bits 32

global procedura

segment data use32 class=data
    adresa_sir_sursa dd 0
    adresa_sir_destinatie dd 0
    lungime dd 0
    suma dw 0 
    maxim db 0
    
segment code use32 class=code
    procedura:
        mov eax, [esp + 12]
        mov [adresa_sir_sursa], eax
        
        mov eax, [esp + 8]
        mov [adresa_sir_destinatie], eax
        
        mov eax, [esp + 4]
        mov [lungime], eax
        
        
        mov esi, [adresa_sir_sursa]
        mov edi, [adresa_sir_destinatie]
        mov ecx, [lungime]
        incarca_octeti:
            xor edx, edx
            xor eax, eax
            xor ebx, ebx
            
            lodsb
            
            mov bl, [maxim]
            cmp al, bl
            jb nu_e4
            
            mov bl, al
            mov [maxim], bl
            
            mov dl, "4"
            
            nu_e4:
            
            lodsb
            
            mov bl, [maxim]
            cmp al, bl
            jb nu_e3
            
            mov bl, al
            mov [maxim], bl
            
            mov dl, "3"
            
            nu_e3:
            
            lodsb
            
            mov bl, [maxim]
            cmp al, bl
            jb nu_e2
            
            mov bl, al
            mov [maxim], bl
            
            mov dl, "2"
            
            nu_e2:
            
            lodsb
            
            mov bl, [maxim]
            cmp al, bl
            jb nu_e1
            
            mov bl, al
            mov [maxim], bl
            
            mov dl, "1"
            
            nu_e1:
            
            mov al, dl
            stosb
            
            xor edx, edx
            
            mov bl, [maxim]
            movsx dx, bl
            
            adc [suma], dx
            
            mov byte [maxim], 0
        loop incarca_octeti
        
        movsx ebx, word [suma]
        ret

