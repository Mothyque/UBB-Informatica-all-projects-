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
        c db 10
        d db 6
        
    segment code use32 class=code
        start:
        xor EAX, EAX
        xor EBX, EBX
        xor EDX, EDX
        
        mov AL, [a] 
        mov BL, [b]
        sub AL, BL
        mov EDX, EAX
        
        xor EAX, EAX
        xor EBX, EBX
        
        mov AL, [c]
        mov BL, [b]
        sub AL, BL
        xor EBX, EBX
        mov BL, [d]
        sub AL, BL
        
        add EDX, EAX
        xor EBX, EBX
        mov BL, [d]
        add EDX, EBX
        
        push EDX
        push print_frmt 
        call [printf]
            
        add  esp, 4 * 2

        push    dword 0      
        call    [exit]   