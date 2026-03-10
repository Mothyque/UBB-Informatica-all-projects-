bits 32
global start

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    sir dd 1234A678h, 12785634h, 1A4D3C26h
    len equ ($ - sir) / 4
    sir_word times len dw 0
    doi dw 2
    suma dd 0
    print_frmt db "%d ", 0

segment code use32 class=code
    start:  
        
        mov esi, sir
        mov edi, sir_word
        mov ecx, len
        selecteaza_words:
            xor ebx, ebx
            xor edx, edx
            
            lodsd
            
            mov ebx, eax
            
            and eax, 0x0000FF00
            shr eax, 8
            
            mov edx, eax
            
            mov eax, ebx
            
            and eax, 0xFF000000
            shr eax, 16
            
            add eax, edx
            
            stosw
        loop selecteaza_words
        
        mov esi, sir_word
        mov ecx, len
        afiseaza:
            push ecx
            
            xor ebx, ebx
            lodsw
            
            imparte:
                mov dx, 0
            
                div word [doi]
            
                cmp dx, 0
                je nu
                
                inc ebx
                
                nu:
                
                cmp ax, 0
                jne imparte
            
            add [suma], ebx
            
            pop ecx
        loop afiseaza
            
        push dword [suma]
        push dword print_frmt
        call [printf]
        add esp, 2 * 4
            
        push dword 0
        call [exit]