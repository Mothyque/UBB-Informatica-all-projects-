bits 32
global start

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    sir dq 0x4b6d699acee2a80a, 0xb98e265a0ca56a70, 0x375b6fe4a377b864, 0xccee41174d54af2d, 0xcf64460cc35849f4
    len equ ($ - sir) / 4
    sir2 times len dd 0
    print_frmt db "%d", 0
    
segment code use32 class=code
    start:
        
        mov esi, sir
        mov edi, sir2
        mov ecx, len
        load_quad_word:
            lodsd
            
            mov ebx, eax
            
            lodsd
            
            cmp eax, ebx
            jl nu
            
            stosd
            
            nu:
        loop load_quad_word
        
        mov esi, sir2
        xor ebx, ebx
        vocale:
            lodsd
            
            cmp eax, 0
            je finish
            
            and eax, 0x0000FF00
            shr eax, 8
            
            cmp al, 'a'
            je vocala
            
            cmp al, 'e'
            je vocala
            
            cmp al, 'i'
            je vocala
            
            cmp al, 'o'
            je vocala
            
            cmp al, 'u'
            je vocala
            
            cmp al, 'A'
            je vocala
            
            cmp al, 'E'
            je vocala
            
            cmp al, 'I'
            je vocala
            
            cmp al, 'O'
            je vocala
            
            cmp al, 'U'
            je vocala
            
            jmp vocale
            
            vocala:
                inc ebx
            jmp vocale
            
        
        finish:
        push ebx 
        push dword print_frmt
        call [printf]
        add esp, 2 * 4
            
        push dword 0
        call [exit]