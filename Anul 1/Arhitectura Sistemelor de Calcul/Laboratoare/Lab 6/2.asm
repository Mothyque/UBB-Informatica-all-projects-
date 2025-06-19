bits 32 
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dw 1432h, 8675h, 0ADBCh
    len equ ($ - src) / 2
    d times len dd 0
    len2 equ len * 4
    sort times len2 db 0
    print_frmt db "%008Xh ", 0
    
segment data use32 class = code
start:
    xor eax, eax
    
    mov esi, src
    mov edi, sort
    mov ecx, len
    cld
    nextWord:
        mov eax, 0
        mov edx, 0
        nextByte:
            lodsb 
            mov ebx, 0
            mov bh, al
            mov bl, 0
            or bl, 0x0F
            
            and al, bl
            stosb
            
            mov al, bh
            mov bl, 0
            or bl, 0xF0
            
            and al, bl
            shr al, 4
            stosb
            
            inc edx
            
        cmp edx, 2
        jne nextByte
        
    loop nextWord
    
    cld
    xor edx, edx
    mov esi, 0
    
    extraLoop:
        add edx, 4
        cmp edx, len2 ; ultimul word a fost procesat
        jg final
        cmp esi, len2
        je final
    outerLoop:
        cmp esi, edx ;word-ul curent a fost procesat
        je done ; treci la urmatorul
        
        mov edi, esi
        inc edi
        
        cmp edi, edx
        je resume
        
        innerLoop:
            cmp edi, edx
            je resume
                
                mov al, [sort + esi]
                cmp al, [sort + edi]
                
                jge next
                
                    mov bl, [sort + edi]
                    mov [sort + esi], bl
                    mov [sort + edi], al
                
                next:
            inc edi
        jmp innerLoop
            resume:
            inc esi
    jmp outerLoop
    done:
    
    loop extraLoop
    final:
    
    cld
    
    mov esi, sort
    mov edi, d
    mov ecx, len
    
    getWord:
        movsd
    loop getWord
    
    cld
    mov esi, d
    mov ecx, len
    write:
        push ecx
        
        lodsd
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4      

        pop ecx         
    loop write
    
    push dword 0         
    call [exit]     
        