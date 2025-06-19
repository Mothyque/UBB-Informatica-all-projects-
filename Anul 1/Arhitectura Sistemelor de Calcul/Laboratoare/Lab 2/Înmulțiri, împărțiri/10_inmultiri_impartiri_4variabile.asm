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
    d dw 4
    
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [b]
    sub al, [a]
    add al, 2
    mov dh, 20
    mul dh
    
    mov bx, ax 
    
    xor eax, eax
    xor edx, edx
    
    mov al, [c]
    mov dh, 10
    mul dh
    
    sub bx, ax ;bx = 20 * (b - a + 2 ) - 10 * c
    
    xor eax, eax
    mov ax, bx
    
    xor edx, edx
    xor ebx, ebx
    
    mov bx, 3
    
    mul bx     ; Rezultatul merge in DX:AX
    
    xor ebx, ebx
    mov bl, 5
    
    div bl  ; Imparti pe AX la 5. Ar fi mers un div BX si atunci imparteai DX:AX la 5 si era corect. (pe numere mici e corect si cum ai scris tu, pentru ca in DX nu e nimic, dar daca ai un numar suficient de mare incat sa ajunga niste biti si in DX, atunci iti da gresit)
    xor ah, ah
    
    push eax
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   