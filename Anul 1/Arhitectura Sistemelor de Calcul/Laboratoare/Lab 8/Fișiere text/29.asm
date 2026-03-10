bits 32 

global start        

extern exit, fopen, fread, fclose, printf
import exit msvcrt.dll  
import fopen msvcrt.dll  
import fread msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    nume_fisier db "input.txt", 0  
    mod_acces db "r", 0                                   
    descriptor_fis dd -1         
    len equ 100                  
    text times len db 0   
    maxim dd 0
    zece dd 10
    printfrmt db "%d", 0
     
segment code use32 class=code
start:
    
    push dword mod_acces
    push dword nume_fisier
    call [fopen]
    add esp, 2 * 4
    
    mov [descriptor_fis], eax
    
    push dword [descriptor_fis]
    push dword len
    push dword 1
    push dword text
    call [fread]
    add esp, 4 * 4
    
    mov ecx, len
    mov esi, text
    xor eax, eax
    xor edx, edx
    xor ebx, ebx
    cauta:
        lodsb 
        
        cmp al, " "
        je spatiu
        
        sub al, "0"
        
        mov bl, al
        
        xor al, al
        mov eax, edx
        
        mul dword [zece]
        
        mov edx, eax
        
        add edx, ebx
        
        xor ebx, ebx
        xor eax, eax
        
        jmp next
        
    spatiu:
        cmp edx, [maxim]
        jb nu
        
        mov [maxim], edx
        
    nu:
        xor edx, edx
    next:
    loop cauta
    
    
    push dword [maxim]
    push printfrmt 
    call [printf]
    add esp, 2 * 4
    
    push dword [descriptor_fis]
    call [fclose]
    add esp, 4