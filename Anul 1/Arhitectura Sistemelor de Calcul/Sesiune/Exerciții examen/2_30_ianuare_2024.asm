bits 32

global start

extern exit

import exit msvcrt.dll

segment data use32 class=data
    x dw -129, 10 + 100h + 1000b ; |7F|FF|12|01|
    y dw 1001h >> 1001b, 128h & 128 ; |08|00|00|00|
    z dw z, $$ - z ; |adresa lui z ca word|F8|FF
    w dd x + y - z, w - y + x ;? eroare ig
    h dw 101b, ;101  - h, 11h - 11b, h - 11; |05|00| restul nu putem calcula
    c db z - w; |FC|
    e dd 12;xyzw, 'xyzw' ;eroare dar s-ar pune |x|y|z|w|
segment code use32 class = code
    start:
    
        push dword 0
        call [exit]