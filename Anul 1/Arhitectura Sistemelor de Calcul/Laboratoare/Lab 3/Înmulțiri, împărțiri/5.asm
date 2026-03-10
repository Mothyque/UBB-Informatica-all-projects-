bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a dd 23
    b db 6
    c dw 3
    x dq 4
  
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [b]
    cbw 
    cwd
    
    mov bx, [c]
    
    idiv bx
    cwde
    xor edx, edx
    add eax, [a]
    
    mov edx, eax
    xor eax, eax
    
    mov al, 1
    cbw
    cwde
    
    sub edx, eax ; edx = a + b / c - 1
    
    xor eax, eax
    mov al, [b]
    add al, 2
    cbw
    cwde ; eax = b + 2
    
    xor ebx, ebx
    mov ebx, eax
    
    xor eax, eax
    mov eax, edx
    
    xor edx, edx
    
    idiv ebx
    
    xor ebx, ebx
    mov ebx, [x]
    
    sbb eax, ebx
    
    push eax
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   