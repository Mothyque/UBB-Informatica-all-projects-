bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db '%d ', 0
    s1 db 1, 2, 3, 4, 2, 1
    len equ $ - s1
    s2 db 5, 6, 7, 8, 11, 31
    d resb 100
    
    
segment code use32 class=code
start:

    
    mov esi, 0
    mov ecx, len
    load1:
        test esi, 1
        jz par
        jnz impar
        
        par:
            mov al, [s1 + esi]
            mov edx, [s2 + esi]
            cbw 
            cwde
            add eax, edx
            mov [d + esi], eax
            xor eax, eax
            xor edx, edx
            inc esi      
        
        impar:
            mov al, [s1 + esi]
            mov edx, [s2 + esi]
            cbw
            cwde
            sub eax, edx
            mov [d + esi], eax
            xor eax, eax
            xor edx, edx
            inc esi        
    loop load1       
    
    
    xor eax, eax
    xor ecx, ecx
    mov esi, d
 
    mov ecx, len
    
    afisare:
        push ecx
        
        lodsb 
        cbw
        cwde
        push eax
        push print_frmt 
        call [printf]
        add  esp, 2 * 4
        
        xor eax, eax
        
        pop ecx
    loop afisare
    
    push    dword 0
    call    [exit]
    
    