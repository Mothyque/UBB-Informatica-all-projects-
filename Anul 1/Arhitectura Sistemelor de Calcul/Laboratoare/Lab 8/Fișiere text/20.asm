bits 32

global start

extern exit, fopen, fclose, fprintf, 
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll

segment data use32 class=data
    nume_fisier db "output.txt", 0 
    mod_acces db "w", 0
    
    descriptor_fis dd -1
    
    text db "mahbga sdhfnam jnsdh nacshba", 0
    len equ $ - text

    text_aux times len db "", 0
    
    
segment data use32 class=code
start:
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp, 2 * 4 
    
    mov [descriptor_fis], eax
    
    xor ebx, ebx
    xor edx, edx
    mov esi, text
    mov edi, text_aux
    mov ecx, len
    mov ebx, "1"
    modifica:
        lodsb
        mov edx, ebx
        sub edx, "0"
        
        test edx, 1
        jnz pozitie_impara
        
        xor eax, eax
        mov eax, ebx
        stosb
        jmp next
        
    pozitie_impara:
        stosb
    
    next:
        inc ebx
        cmp ebx, ":"
        jne resume
        
        mov ebx, "0"
        
    resume:
    loop modifica
    
    push dword text_aux
    push dword [descriptor_fis]
    call [fprintf]
    add esp, 2 * 4
    
    push dword [descriptor_fis]
    call [fclose]
    add esp, 1 * 4
    
    push dword 0
    call [exit]