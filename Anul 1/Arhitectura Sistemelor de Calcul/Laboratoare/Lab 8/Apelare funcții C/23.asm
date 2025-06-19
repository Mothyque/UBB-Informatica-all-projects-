bits 32

global start

extern exit, printf, scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 class=data
    n dd 0 
    scanfrmt db "%x", 0
    printfrmtfara db "Numarul in baza 10 fara semn este: %u", 0
    newline db 10, 0
    printfrmtcu db "Numarul in baza 10 cu semn este: %d", 0

segment data use32 class=code

start:

    push dword n
    push dword scanfrmt
    call [scanf]
    add esp, 2 * 4
    
    xor eax, eax
    
    mov al, [n]
    push eax
    push dword printfrmtfara
    call [printf]
    add esp, 2 * 4
    
    push dword newline
    call[printf]
    add esp, 1 * 4
    
    xor eax, eax
    
    mov al, [n]
    cbw
    cwde
    push eax
    push dword printfrmtcu
    call [printf]
    add esp, 2 * 4
    
    push dword 0
    call [exit]
    