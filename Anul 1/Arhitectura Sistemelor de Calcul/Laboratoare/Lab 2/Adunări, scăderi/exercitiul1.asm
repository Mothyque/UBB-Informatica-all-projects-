bits 32


global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    a db 5
    b db 7
    c db 9
    d db 6
    print_frmt db "Rezultatul este %d", 0
    
segment code use32 class=code
    start:
        mov EAX, 0

        mov AL, [a] ; AL = a
        mov BL, [d] ; BL = b;
        add AL, BL  ; AL = a + b
        
        xor EBX, EBX ; curatam EBX
        
        mov BL, [c] ; BL = c
        sub EBX, EAX ; EBX =  c - (a + b)
        
        mov EDX, EBX
        
        xor EBX, EBX
        xor EAX, EAX
        mov AL, [d] ; AL = b
        mov BL, [b] ; BL = d
        add AL, BL  ; AL = b + d
        
        ;add EDX, EAX ; EDX = c - (a + d) + (b + d)
        push EDX
        push print_frmt 
        call [printf]
        
        add  esp, 4 * 2
        
        push    dword 0      
        call    [exit]       
