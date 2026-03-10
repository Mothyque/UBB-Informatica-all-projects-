bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 1
    b dw 2
    c db 3
    d dd 4
    x dq 5
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [a]
    cbw
    imul word [b]
    
    mov bx, ax
    
    xor eax, eax
  
    
    xor eax, eax
    mov ax, 7
    sbb ax, bx
    
    xor ebx, ebx
    mov bx, ax
    
    xor eax, eax
    mov al, [c]
    cbw
    add bx, ax
    
    xor eax, eax
    mov ax, bx
    
    xor ebx, ebx
    mov bl, [a]
    
    idiv bl
    cwde
    xor ebx, ebx
    mov ebx, eax
    xor eax, eax
    
    mov eax, [d]
    sbb eax, ebx
    
    xor ebx, ebx
    mov ebx, 6
    
    sbb eax, ebx
    
    mov ecx, eax
    xor eax, eax
    xor ebx, ebx
    xor edx, edx
    
    mov eax, [x]
    mov eb  x, 2
    idiv ebx
    adc ecx, eax
    push ecx
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   