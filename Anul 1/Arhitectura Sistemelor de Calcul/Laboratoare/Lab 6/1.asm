bits 32

global start
extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class = data
    s dd 127F5678h, 0ABCDABCDh
    len equ ($ - s) / 4 
    d times len dd 0
    simpar dw 0
    spar dw 0 
    
    print_frmt db "%008Xh ", 0
    
segment code use32 class = code
start:
    xor eax, eax
    
    
    mov esi, s
    mov edi, d
    mov ecx, len
    
    nextDword:
        mov bx, 0 ; bx - byte counter
        mov word [simpar], 0
        mov word [spar], 0
        
        nextByte:
            inc bh ; inc byte 
            lodsb
            cbw
            mov dx, ax
            xor bl, 1
            test bl, 1 ; test if odd
            je oddByte
            
            mov ax, word[spar]
            adc ax, dx 
            mov word[spar], ax
            
            jmp resume
            
            
            oddByte:
                mov ax, word[simpar]
                adc ax, dx
                mov word[simpar], ax
            
            resume:
            cmp bh, 4
            jl nextByte
            
            mov ax, word[spar]
            stosw
            mov ax, word[simpar]
            stosw
    loop nextDword
    
    
    mov esi, d
    mov ecx, len
    
    write:
        push ecx
        
        lodsd
        push eax
        push print_frmt
        call[printf]
        add esp, 2 * 4
        
        pop ecx
    loop write
    
    push dword 0
    call [exit]
        
    
    