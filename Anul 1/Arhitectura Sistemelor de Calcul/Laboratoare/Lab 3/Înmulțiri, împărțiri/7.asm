bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 1
    b db 2
    c dw 3
    e dw 4
    x dq 5
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [b]
    cbw
    add ax, [c] ; ax = b + c
    mov bx, ax
    
    xor eax, eax
    
    mov al, [a]
    sbb al, 2
    cbw
    cwd
    idiv bx
    cwde
    
    xor ebx, ebx
    
    mov ebx, eax
    
    xor eax, eax
    mov al, [a]
    imul word[c]
    cwde
    add ebx, eax
    
    xor eax, eax
    mov eax, ebx
    xor ebx, ebx
    mov ebx, [x]
    sbb eax, ebx
    cwde

  
    
    push ebx
    
    
    
    
    
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   