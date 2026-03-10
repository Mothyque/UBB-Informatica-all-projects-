bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dd 0702090Ah, 0B0C0304h, 05060108h
    len_src equ ($ - src) / 4
    len_dest equ len_src * 2
    dest times len_dest db 0
    
    print_frmt db "%0Xh ", 0
    
segment code use32 class = code
start:
    mov edi, dest
    mov esi, src
    mov ecx, len_src
    
    nextWord:
        xor eax, eax
        add esi, 3 ;incepem de la al 4-lea nibble
        mov bl, 0
        
        nextTwoBytes:
            std ;inversam ordinea
            lodsb ;incarcam ultimul byte
            shl al, 4 ;il mutam spre stanga pentru a fi prima cifra in byte 
            xor dl, dl 
            or dl, al ; il salvam in dl
            lodsb 
            or al, dl ;salvam partea high alaturi de cea low a byte-ului
            cld
            stosb ; salvam byte-ul 
            inc bl ; 
            cmp bl, 2
        jne nextTwoBytes
        add esi, 5
    
    loop nextWord
       
    xor eax, eax
    xor ebx, ebx
    mov esi, dest
    mov ecx, len_dest
    outerLoop:
        cmp ecx, 1
        jle done    
        
        lodsb
        mov edi, esi
        
        mov ebx, ecx
        
        innerLoop:
            xor edx, edx
            dec ebx
            scasb
            jle doNothing
            
            mov al, [edi - 1]
            mov dl, [esi - 1] 
            mov [edi - 1], dl
            mov [esi - 1], al
            
            doNothing:
            cmp ebx, 1
            jg innerLoop
    loop outerLoop
    done:
    
    mov esi, dest
    mov ecx, len_dest
    
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
    
    