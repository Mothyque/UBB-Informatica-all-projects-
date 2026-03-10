bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src db 5, 25, 55, 127
    len_src equ ($ - src)
    dest times len_src db 0
    doi db 2
    print_frmt db "%d ", 0
    
segment code use32 class = code
start:
    xor eax, eax
    
    mov esi, src
    mov edi, dest
    mov ecx, len_src
    
    nextByte:
        lodsb
        xor ebx, ebx
        
        count1:
            cmp al, 0
            je final

            cbw
            div byte [doi]
            
            cmp ah, 0
            je count1
            
            inc bl
            jmp count1      
            
        final:
            mov al, bl
            stosb
            xor eax, eax
    loop nextByte
        
    mov esi, dest
    mov ecx, len_src
    
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