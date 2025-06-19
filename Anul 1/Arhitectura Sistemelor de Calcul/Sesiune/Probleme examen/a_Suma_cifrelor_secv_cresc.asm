bits 32
global start

extern exit, printf, cdecl
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    sir_initial dd -1, 123456, 0xabcdeff, 0xabcdeff, 0xdbcdeff, 0111010101b
    len equ ($ - sir_initial) / 4
    sir_sume times len dd 0
    print_frmt db "%xH ", 0
    bara db "| ", 0
    print_rez db "Perechile sunt: "
        
segment code use32 class=code

    start:
        
        push dword sir_initial
        push dword sir_sume
        push dword len
        call cdecl
        
        push print_rez
        call [printf]
        add esp, 1 * 4
        
        
        mov esi, 0
        xor ebx, ebx
        afiseaza:
            cmp esi, len * 4
            jae afara
            
            mov eax, [sir_sume + esi]
            add esi, 4
            
            cmp esi, len * 4
            jae afara
            
            cmp eax, [sir_sume + esi]
            ja mai_mare
            
            cmp eax, [sir_sume + esi]
            je egale
            
            mov eax, [sir_initial + esi - 4]
            push eax
            push dword print_frmt
            call [printf]
            add esp, 2 * 4
            inc ebx
            jmp afiseaza
            mai_mare:
                cmp ebx, 0
                je treci
                    
                mov eax, [sir_initial + esi - 4]
                push eax
                push dword print_frmt
                call [printf]
                add esp, 2 * 4
                
                push dword bara
                call [printf] 
                add esp, 2 * 4
                
                xor ebx, ebx
                jmp afiseaza
            
            egale:
                cmp ebx, 0
                je treci
                
                mov eax, [sir_initial + esi - 4]
                push eax
                push dword print_frmt
                call [printf]
                add esp, 2 * 4
                
                push dword bara
                call [printf] 
                add esp, 2 * 4
                
                xor ebx, ebx
                
                mov eax, [sir_initial + esi]
                push eax
                push dword print_frmt
                call [printf]
                add esp, 2 * 4
                
            treci:
                jmp afiseaza
        afara:
            cmp ebx, 0
            je nu_mai 
                
            mov eax, [sir_initial + esi - 4]
            push eax
            push dword print_frmt
            call [printf]
            add esp, 2 * 4
            
            push dword bara
            call [printf] 
            add esp, 2 * 4
        
        nu_mai
        push dword 0
        call [exit]