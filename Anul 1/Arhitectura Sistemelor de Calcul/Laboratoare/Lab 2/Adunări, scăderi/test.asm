bits 32 
global start        

extern exit               
import exit msvcrt.dll    
segment data use32 class=data

segment code use32 class=code
    start:
        mov eax, 10100101101101101100011111011000b
        mov ecx, 32
        
        afiseazab_biti:
            xor edx, edx
            dec ecx
            shl eax, 1
            adc edx, 1
        loop afiseazab_biti
        
        push    dword 0      
        call    [exit]       
