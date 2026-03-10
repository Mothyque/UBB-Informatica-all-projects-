bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a dw 1
    b db 2
    c db 3
    d db 4
    e dw 5
    x dq 6
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov ax, [a]
    mov dx, 2
    imul dx
    cwde
    mov ecx, eax
    
    xor edx, edx
    xor eax, eax
    
    mov dl, 2
    mov al, [b]
    cbw
    idiv dl
    xor ah, ah
    cwde ; eax = b / 2
   
    adc eax, ecx
    xor ecx, ecx
    mov ebx, [e]
    adc eax, ebx
    cwde
    push eax
    
    mov ecx, eax
    
    xor eax, eax
    mov al, [c]
    sbb al, [d]
    cbw 
    cwde
    
    xor ebx, ebx
    mov ebx, eax
    
    xor eax, eax
    mov eax, ecx
    
    xor edx, edx
    
    idiv ebx
    cwde
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   