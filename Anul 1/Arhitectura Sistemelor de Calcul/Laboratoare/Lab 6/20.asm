bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src db 'ana are mere si rerer cojoc dar unu si nu capac '
    len_src equ $ - src
    cuvant times len_src db 0
    invers times len_src db 0
    printfrmt db "%c", 0
    newline db 10, 0
segment code use32 class = code
start:
    cld
    mov esi, src
    mov ecx, len_src 
    mov edi, cuvant
    loadcuvant:
        xor ebx, ebx
        xor edi, edi
        mov edi, cuvant
        
    litera:
        
        lodsb
        
        cmp al, ' '
        je spatiu
        
        inc ebx
        stosb
        jmp litera
        
        
    spatiu:
        
        push edi
        push esi
        push ecx
        
        mov edi, invers
        mov esi, cuvant 
        add esi, ebx 
        dec esi
        mov ecx, ebx
        
        loadinvers:
            std
            lodsb
            cld
            stosb
        loop loadinvers
        
        cld
        mov edi, invers
        mov esi, cuvant
        mov ecx, ebx
        repe cmpsb
        jnz next
        
    afiseaza:
        mov esi, cuvant
        mov ecx, ebx
    write:
        push ecx
        
        lodsb
        push eax
        push printfrmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
        loop write
       
        push newline
        call[printf]
        add esp, 1 * 4
        
    next:
        pop ecx
        sub ecx, ebx
        pop esi
        pop edi
    loop loadcuvant
  
    
    push dword 0
    call [exit]