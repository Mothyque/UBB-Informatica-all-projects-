bits 32
global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll


segment data use32 class=data
    numar dd 0
    numere dd 0
    sir_1 resd 100
    sir_2 resd 100
    zece dd 10
    print_frmt db "%d ", 0 
    scan_frmt db "%d", 0

segment code use32 class=code
    start:
        
        push dword numar
        push dword scan_frmt
        call [scanf]
        add esp, 2 * 4
        
        mov ecx, [numar]
        mov edi, sir_1
        citeste_numere:
            push ecx
            
            push dword numere
            push dword scan_frmt
            call [scanf] 
            add esp, 2 * 4
            
            mov eax, [numere]
            stosd
            
            pop ecx
        loop citeste_numere
        
        mov esi, sir_1
        mov ecx, [numar]
        mov edi, sir_2
        adauga_sume:
            xor ebx, ebx
            lodsd
        
            imparte_nr:
                xor edx, edx
                
                div dword [zece]
                
                add ebx, edx
                
                cmp eax, 0
                jne imparte_nr
            
            test ebx, 1
            jnz impar
            mov eax, ebx
            stosd
            
            impar:
        loop adauga_sume
        
        mov esi, sir_2
        afiseaza:
            lodsd
            
            cmp eax, 0
            je gata
            
            push eax
            push dword print_frmt
            call [printf]
            add esp, 2 * 4
            
            jmp afiseaza
            
        gata:
        push dword 0
        call [exit]
        