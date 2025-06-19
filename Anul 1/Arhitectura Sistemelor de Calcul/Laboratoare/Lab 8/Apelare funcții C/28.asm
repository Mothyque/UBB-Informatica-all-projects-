bits 32 
global start        

extern exit, scanf, printf              
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll    
segment data use32 class=data
    scanfrmt db "%d", 0
    printfrmt db "%d", 0
    numar dd 0
    maxim dd 0
    
segment code use32 class=code
    start:
        
        citeste:
            xor edx, edx
            xor ebx, ebx
            
            push dword numar
            push dword printfrmt
            call [scanf]
            add esp, 2 * 4
            
            cmp dword [numar], 0
            je afara
            
            mov edx, [maxim]
            mov ebx, [numar]
            cmp edx, ebx
            ja nu_gasit
            
            mov [maxim], ebx
            
        nu_gasit:
            mov dword [numar], 0
            jmp citeste
        
        afara:
            mov edx, [maxim]
            push dword edx
            push dword printfrmt
            call [printf]
            add esp, 2 * 4
        
        push    dword 0      
        call    [exit]       