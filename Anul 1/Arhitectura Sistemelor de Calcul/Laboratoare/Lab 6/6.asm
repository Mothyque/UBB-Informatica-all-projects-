bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dw -22, 145, -48, 127
    len_src equ ($ - src) / 2
    dest times len_src db 0
    doi dw 2
    print_frmt db "%d ", 0
    
segment code use32 class = code
start:
    xor eax, eax
    
    mov esi, src
    mov edi, dest
    mov ecx, len_src
    
    nextWord:
        lodsw
        cwde
        
        xor ebx, ebx

        cmp eax, 0
        jl count0
        
        count1:
            cmp eax, 0
            je final
            
            mov dx, 0
            push dx
            push ax
            pop eax
            
            div word [doi]
            
            cmp dx, 0
            je count1
            
            inc bl
            jmp count1      
        
        count0:
            cmp eax, 0
            je final
            
            mov dx, 0
            push dx
            push ax
            pop eax
            div word [doi]
            
            cmp dx, 0
            jne count0
            
            inc bl
            jmp count0
            
        final:
            mov al, bl
            stosb
            xor eax, eax
    loop nextWord
        
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