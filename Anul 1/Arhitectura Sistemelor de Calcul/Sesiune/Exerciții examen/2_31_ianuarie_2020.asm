bits 32
global start

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    x dw -256, 256h ; |00|FF|56|02
    y dw 256|-256, 256h & 256 ; |00|FF|00|00
    z db $-z, y-x ;00|04|
      db 'y' - 'x', 'y-x' ; |01|y|-|x|
    a db 512 >> 2 ;-512 << 2 nu avem cum reprezenta numarul pe byte, se genereaza doar |80|00|
    b dw z - a;|(z-a) genereaza syntax error, ce se genereaza este |FA|FF|
    c dd ($ - b) + (d - $),;$ - 2*y + 3 error relative call to absolute adress not supported by obj format, se genereaza |06|00|00|00|
    d db -128, 128^(~128) ; |80||FF|
    e times 2 resw 6 ; |00|00| * 6
    times 2 dd 1234h, 5678h ; |34|12|00|00|78|56|00|00||34|12|00|00|78|56|00|00|
    ;printfrmt db "%xH", 0
segment code use32 class=code
    start:
        
        push dword 0 
        call [exit]