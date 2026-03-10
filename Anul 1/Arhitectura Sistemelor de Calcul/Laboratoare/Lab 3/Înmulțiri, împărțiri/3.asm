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
    d db 4
    x dq 5
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [a]
    imul byte[b]
    mov dx, 100
    imul dx
    cwde ; eax = a * b * 100
    
    mov ebx, eax
    
    xor eax, eax
    
    mov al, 8
    cbw
    cwd
    cwde

    sbb eax, ebx
    
    xor ebx, ebx
    mov ebx, [c]
    
    adc eax, ebx
    cwde
    
    mov ecx, eax 
    
    xor eax, eax
    mov al, [d]
    cbw
    cwd
    cwde
    mov ebx, eax
    
    xor eax, eax
    mov eax, ecx
    
    
    xor edx, edx
    idiv ebx
    cwde
    
    xor edx, edx
    mov edx, [x]
    
    add eax, edx
    
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   