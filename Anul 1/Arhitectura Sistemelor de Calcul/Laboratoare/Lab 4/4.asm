bits 32

;Se da octetul A. Sa se obtina numarul intreg n reprezentat de bitii 2-4 ai lui A. Sa se obtina apoi in B octetul rezultat prin rotirea spre dreapta a lui A cu n pozitii. Sa se obtina dublucuvantul C:
;bitii 8-15 ai lui C sunt 0
;bitii 16-23 ai lui C coincid cu bitii lui B
;bitii 24-31 ai lui C coincid cu bitii lui A
;bitii 0-7 ai lui C sunt 1

global start        

extern exit              
import exit msvcrt.dll
extern printf    
import printf msvcrt.dll

segment data use32 class=data
    print_frmt db "Rezultatul este %u", 0
    a db 10001110b
    b dw 0
    c dd 0
    n db 0
    ; c = 10001110110100010000000011111111
    ; b = 11010001
    ; n = 011
segment code use32 class=code
    start:
    xor EAX, EAX
    xor EBX, EBX
    xor EDX, EDX
    
    mov al, [a]
    and al, 00011100b
    mov cl, 2
    ror al, cl
    mov [n], al
    
    mov al, [a]
    mov cl, [n]
    ror al, cl
    or [b], al
    
    or ebx, 0000000011111111b
    
    xor eax, eax
    mov eax, [b]
    and eax, 00000000000000000000000011111111b
    mov cl, 16
    rol eax, cl
    or ebx, eax
    
    xor eax, eax
    mov eax, [a]
    and eax, 00000000000000000000000011111111b
    mov cl, 24
    rol eax, cl
    or ebx, eax
    
    push ebx
    push print_frmt 
    call [printf]
        
    add  esp, 4 * 2

    push    dword 0      
    call    [exit]   