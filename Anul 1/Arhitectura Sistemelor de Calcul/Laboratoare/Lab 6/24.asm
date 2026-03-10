bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 12345, 20778, 4596 
    len_src equ  ($ - src) / 4
    dest times len_src dd 0
    doi dd 2
    printfrmt db "%d " , 0
    

segment code use32 class = code
start:
    cld
    mov esi, src
    mov ecx, len_src
    mov edi, dest
    load:
        lodsd
        mov ebx, eax
        push ebx
        xor bl, bl

        
    imparte:
        cmp eax, 0
        je verifica
        
        mov edx, 0
        div dword [doi]
        
        cmp edx, 1
        je impar
        
        jmp next
    impar:
        inc bl
    
    next:
        jmp imparte
        
    verifica:
        test bl, 1
        jz parbiti
        
        jmp imparbiti
    
    parbiti:
        pop ebx
        mov eax, ebx
        stosd
        jmp resume
    
    imparbiti:
        pop ebx
        
    resume:
    loop load
    
    mov esi, dest
    mov ecx, len_src
    write:
        push ecx
        
        lodsd
        cmp eax, 0
        je no
        
        push eax
        push printfrmt
        call [printf]
        add esp, 2 * 4
    no:
        pop ecx
    loop write
    
    push dword 0
    call [exit]