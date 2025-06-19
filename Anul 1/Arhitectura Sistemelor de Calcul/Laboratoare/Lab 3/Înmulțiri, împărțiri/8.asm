 bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a dw 1
    b dw 2
    c db 3
    d db 4
    e dd 5
    x dq 6
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov ax, 1
    idiv word [a]
    cwde
    
    mov ebx, eax
    
    xor eax, eax
    mov ax, 200
    imul word [b]
    cwde
    
    adc eax, ebx
    
    xor ebx, ebx
    mov ecx, eax
    xor eax, eax
    mov bl, [d]
    add bl, 1
    add al, [c]
    cbw
    
    idiv bx
    cbw 
    cwde
    
    sbb ecx, eax
    
    xor eax, eax
    mov eax, [x]
    cdq
    idiv word[a]
    
    add ecx, eax
    
    xor eax, eax
    mov eax, ecx
    
    add eax, [e]
    cwde
    push eax
    
    push print_frmt 
    call [printf]
    
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   