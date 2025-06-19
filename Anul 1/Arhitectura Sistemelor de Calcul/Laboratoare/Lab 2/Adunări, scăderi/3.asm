bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %d", 0
    a db 5
    b db 4
    c db 12
    d db 6
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    mov AL, [c] ;AL = c
    mov Bl, [d] ; BL = d
    add AL, BL ; AL = c + d
    
    mov EDX, EAX ; EDX = c + d
    
    xor EAX, EAX
    xor EBX, EBX
    
    mov AL, [a] ;AL = a
    mov BL, [d] ;BL = d
    add AL, BL ; AL = a + d
    
    sub EDX, EAX
    
    xor EAX, EAX
    mov AL, [b]
    
    add EDX, EAX
    
    push EDX
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   