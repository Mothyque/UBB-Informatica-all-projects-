% a) Sa se determine cea mai lunga secventa de numere pare consecutive
% dintr-o lista( daca sunt mai mult secvente de lungime maxima, una
% dintre ele).

% max_secv(A: lista, Lc: numar, Lm: numar, B: lista, C: lista, Rez:
% lista)
% max_secv(i,i,i,i,i,o)
max_secv([Ha|Ta],Lc,Lm,B,C, Rez):-
    Ha mod 2 =:= 0,!,
    Lc_nou is Lc + 1,
    max_secv(Ta,Lc_nou,Lm,[Ha|B],C, Rez).

max_secv([Ha|Ta],Lc,Lm,_B,C, Rez):-
    Ha mod 2 =:= 1,
    Lc =< Lm,!,
    max_secv(Ta,0,Lm,[],C, Rez).

max_secv([Ha|Ta],Lc,Lm,B,_C, Rez):-
    Ha mod 2 =:= 1,
    Lm < Lc,
    reverse(B,B_corect),
    max_secv(Ta,0,Lc,[],B_corect, Rez).

max_secv([], Lc, Lm, B,_C, Rez):-
    Lc > Lm,!,
    reverse(B,B_corect),
    Rez = B_corect.

max_secv([], Lc, Lm, _B, C, Rez):-
    Lc =< Lm,
    Rez = C.

%main1(Lista: lista, Rezultat: lista)
main1(Lista,Rezultat):-
      max_secv(Lista,0,0,[],[],Rezultat).


test1:-
    main1([],[]), %lista vida
    main1([1,1,1],[]), %lista fara numere pare
    main1([1,2,3],[2]), %o singura secventa para
    main1([1,2,2,3,4,4,4,4],[4,4,4,4]), %2 secvente pare de lungimi diferite
    main1([2,3,4,4,5,6,8,10,12,14,16,18],[6,8,10,12,14,16,18]), %secventa para cea mai lunga e la final
    main1([1,2,3,4,6,8,1,2,4],[4,6,8]). %secventa para cea mai lunga NU e cea de la final.



% b) Se da o lista eterogena, formata din numere intregi si liste de
% numere intregi. Sa se inlocuiasca fiecare sublista cu cea mai lunga
% secventa de numere pare consecutive din sublista respectiva.

%inlocuire(L: lista, B: lista, Rez: lista)
%inlocuire(i,i,o)
inlocuire([H|T],B,Rez):-
    is_list(H),!,
    main1(H,Sublista),
    inlocuire(T,[Sublista|B],Rez).

inlocuire([H|T],B,Rez):-
    inlocuire(T,[H|B],Rez).

inlocuire([], B, Rez):-
    reverse(B, B_corect),
    Rez = B_corect.

%main2 (L: lista)
main2(L, Rezultat):-inlocuire(L,[], Rezultat).

test2:-
    main2([],[]), %lista vida
    main2([1,3,5,7],[1,3,5,7]), %lista fara subliste
    main2([1,[3,5,7],9],[1,[],9]), %lista fara subliste ce contin secvente pare
    main2([1,[],3],[1,[],3]), %lista cu sublista vida
    main2([1,2,[3,4,5,6,8,10,12],5,6,[1,2,3],[1,3]],[1,2,[6,8,10,12],5,6,[2],[]]). %lista ce contine subliste care au secvente crescatoare
