%Sa se scrie un predicat care elimina dintr-o lista toate elementele
% care se repeta.

%nr_ap(L:lista, E: numar, Cnt: numar, Rez: numar)

%L: lista de numere
%E: elementul cautat
%Cnt: numarul de aparitii curent
%Rez: numarul total de aparitii (rezultatul)

%returneaza numarul de aparitii ale unui numar intr-o lista

%nr_ap(i,i,i,o),(i,i,i,i) (Pentru teste)
nr_ap([],_,Cnt,Rez):-
    Rez = Cnt.

nr_ap([H|T],E,Cnt,Rez):-
    H =:= E,!,
    Cnt1 is Cnt + 1,
    nr_ap(T,E,Cnt1,Rez).

nr_ap([_|T],E,Cnt,Rez):-
    nr_ap(T,E,Cnt,Rez).


%elimina_dup(L:Lista,Init:Lista,Acc:Lista,Rez:Lista)

% L: lista curenta
% Init: lista initiala
% Acc: lista acumulata momentan,
% Rez: lista rezultat

% elimina duplicatele din lista L

% elimina_dup(i,i,i,o), (i,i,i,i) (cand testam un rezultat).


elimina_dup([],_,Acc,Rez):-
    reverse(Acc,Rez).

elimina_dup([H|T],Init,Acc,Rez):-
    nr_ap(Init,H,0,Nr), %in nr salvez numarul de aparitii al elementului
    Nr =:= 1,!,
    elimina_dup(T,Init,[H|Acc],Rez).

elimina_dup([_|T],Init,Acc,Rez):-
    elimina_dup(T,Init,Acc,Rez).

%main(i,o),(i,i)
main(L,Rez):-elimina_dup(L,L,[],Rez).

test:-
    main([1,2,3,4,3,3,3,3,67,5,67],[1,2,4,5]),
    main([1,2,2,2,2,2,3],[1,3]),
    main([],[]),
    main([1,2,3,4,5,6],[1,2,3,4,5,6]),
    main([67,67,67,67,67],[]),
    main([1,2,1,4,1,3,4],[2,3]),
    main([1,2,3,1,2,3],[]),
    main([1,2,3,4,5],[1,2,3,4,5]),
    main([1,2,3,4,3,2,1,5,6,2,3],[4,5,6]).







