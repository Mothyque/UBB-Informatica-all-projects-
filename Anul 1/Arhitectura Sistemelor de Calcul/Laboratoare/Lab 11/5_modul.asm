bits 32 
global start, citeste_a, citeste_b, citeste_c, calcul     

extern exit, scanf, printf           
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    frmt_a db "a este: ", 0
    frmt_b db "b este: ", 0
    frmt_c db "c este: ", 0
    citire db "%d", 0
    frmt_rez db "rezultatul a + b - c este: %d", 0
        
segment code use32 class=code        
        citeste_a:
            push dword frmt_a 
            call [printf]
            add esp, 1 * 4
            
            mov eax, [esp + 4]
            push eax
            push dword citire
            call [scanf]
            add esp, 2 * 4
            ret 
            
        citeste_b:
            push dword frmt_b 
            call [printf]
            add esp, 1 * 4
            
            mov eax, [esp + 4]
            push eax
            push dword citire
            call [scanf]
            add esp, 2 * 4
            ret 
            
            
        citeste_c:
            push dword frmt_c
            call [printf]
            add esp, 1 * 4
            
            mov eax, [esp + 4]
            push eax
            push dword citire
            call [scanf]
            add esp, 2 * 4
            ret 
            
            
        calcul:
            add eax, ebx
            sub eax, edx
            push eax
            push dword frmt_rez
            call [printf]
            add esp, 2 * 4
            ret
            
        push    dword 0      
        call    [exit]       
