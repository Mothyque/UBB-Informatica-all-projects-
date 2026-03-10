bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src1 db 12h, 15h, 37h, 92h, 7Ah
    len_src1 equ ($ - src1)
    src2 db 11h, 20h, 99h
    len_src2 equ ($ - src2)  
    len_aux equ (len_src1 +len_src2)
    aux times len_aux db 0
    intermediar times len_aux db 0
    dest times len_aux db 0
    print_frmt db "%02xh ", 0

segment code use32 class = code
start:
    xor eax, eax
    xor ebx, ebx
    xor edx, edx
    xor ecx, ecx
    
    mov al, len_src1
    mov bl, len_src2
    cmp al, bl
    jge stocheaza2
    
    mov dl, al
    jmp next
        
    
    stocheaza2:
        mov dl, bl
        jmp next
        
    next:
    
    mov esi, src1
    mov ecx, len_src1
    mov edi, aux
    loadaux1:
        lodsb
        stosb
    loop loadaux1
    
    mov esi, src2
    mov ecx, len_src2
    loadaux2:
        lodsb
        stosb
    loop loadaux2
    
    mov esi, aux
    mov edi, intermediar
    mov ecx, edx
    
    loaddest:
        lodsb
        cbw
        mov bl, [esi + len_src1 - 1]
        mov bh, 0
        cmp ax, bx
        jg stocheazaal
        
        mov ax, bx
        
        stocheazaal:
            stosb
    loop loaddest
    
    xor ebx, ebx
    xor eax, eax
    
    mov ecx, len_aux
    sub ecx, edx
    mov esi, intermediar
    mov edi, dest
    
    completare:
        lodsb
        cmp al, 0
        jne nextbyte
        
        inc bl
        test bl, 1
        jz par
        
        mov al, 1
        jmp nextbyte
        
        par:
        mov al, 0
            
        nextbyte:
            stosb
        
    loop completare
        
    
    
    mov esi, dest
    mov ecx, len_aux
    sub ecx, edx
    
    write:
        push ecx
        
        lodsb
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop write

    push dword 0
    call [exit]