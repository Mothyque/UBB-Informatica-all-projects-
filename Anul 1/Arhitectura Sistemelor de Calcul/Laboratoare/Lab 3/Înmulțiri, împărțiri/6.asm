bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 4
    b db 2
    c db 1
    d db 4
    e dw 5
    x dq 6
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [a]
    idiv byte [b]
    cbw
    cwd
    cwde
    mov ebx, [x]
    
    adc eax, ebx
    
    mov ecx, eax
    xor eax, eax
    
    mov al, [c]
    imul byte [d]
    cbw
    cwd
    cwde
    
    add ecx, eax
    
    xor eax, eax
    mov al, [b]
    idiv byte [c]
    cbw
    cwd
    cwde
    
    sbb ecx, eax
    
    xor eax, eax
    mov eax, [e]
    cwde
    adc ecx, eax
    push ecx
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   