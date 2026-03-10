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
    c db 3
    d dw 9
    e dq 5 
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [b]
    mul byte [c] ; ax = b * c
    
    mov bx, ax ; bx = b *c
    xor eax, eax
    
    mov al, [a]
    cbw
    
    adc ax, bx ; ax = a + (b * c)
    sbb ax, 9
    
    xor ebx, ebx
    mov bx, ax ; bx = a + (b * c)
    
    xor eax, eax
    
    mov al, 2 ; al = 2
    cbw ; ax = 2
    cwd ; dx:ax = 2
    idiv bx ; ax = dx:ax / bx = 2 / (a + (b * c))
    cwde ; eax =  2 / (a + (b * c) - 9)
    
    
    adc eax, [e]
   
    xor ebx, ebx
    mov ebx, [d]
    sbb eax, ebx
    cwde
    
    push eax
    
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   