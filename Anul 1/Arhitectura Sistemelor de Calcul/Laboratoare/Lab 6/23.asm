bits 32
global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    src dw 2, 4, 2, 5, 2, 2, 4, 3, 5, 1, 3, 6     
    len_src equ  $ - src
    lmax dw 0 
    printfrmt db "%d " , 0
    
segment code use32 class = code
start:
    
    mov esi, src
    mov ecx, len_src
    xor eax, eax
    xor edx, edx
    xor ebx, ebx
    mov bx, 1
    cautasecventa:
        lodsw
        mov dx, [esi]
        cmp ax, dx
        
        ja sfarsit
            
        inc bx
        jmp next
        
    sfarsit:
        mov ax, bx
        mov bx, 1
        cmp ax, [lmax]
        
        jb next
        
        mov [lmax], ax
        
    next:
    loop cautasecventa
    xor edx, edx
    mov dx, [lmax]
    push edx
    push printfrmt
    call[printf]
    add esp, 2 * 4
    push dword 0
    call [exit]