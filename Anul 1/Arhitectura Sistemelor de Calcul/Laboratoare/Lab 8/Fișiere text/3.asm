bits 32 

global start        

extern exit, fopen, fread, fclose, printf
import printf msvcrt.dll
import exit msvcrt.dll  
import fopen msvcrt.dll  
import fread msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
    nume_fisier db "input.txt", 0
    mod_acces db "r", 0
    
    fils_desc dd -1
    len equ 100
    text times len db 0
    printfrmt db "%d ", 0
    
segment data use32 class=code
start:
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp, 2 * 4
    
    mov [fils_desc], eax
    
    push dword [fils_desc]
    push dword len
    push dword 1
    push dword text
    call [fread]
    add esp, 4 * 4
    
    xor eax, eax
    xor ebx, ebx
    
    mov esi, text
    mov ecx, len
    
    cauta:        
        lodsb
        cmp al, 0
        je next 
        
        test al, 1
        jnz next
        
        inc bl
    next:
    loop cauta
        
    push ebx
    push dword printfrmt
    call [printf]
    add esp, 2 * 4
    
        
    push dword [fils_desc]
    call [fclose]
    add esp, 1 * 4
    

    push dword 0
    call [exit]