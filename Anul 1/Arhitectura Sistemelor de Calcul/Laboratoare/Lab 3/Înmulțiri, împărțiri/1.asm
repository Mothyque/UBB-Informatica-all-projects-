bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 1
    b dw 14
    c dq 3
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [a]
    imul byte [a]
    
    
    sbb ax, [b]
    adc ax, 7 ;ax = a * a - b + 7
    
    
    mov cl, [a]
    adc cl, 2 ; cl = a + 2
    
    idiv cl ; al = ax / cl
    xor ah, ah
    cbw
    cwde
    
    add eax, [c]
    
    push eax
    
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   