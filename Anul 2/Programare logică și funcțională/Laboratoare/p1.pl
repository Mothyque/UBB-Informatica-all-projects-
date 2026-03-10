%PROBLEMA 1
%a) Sa se scrie un predicat care intoarce diferenta a doua multimi.
%exista(E: element, L: lista)
exista(E,[E|_]):-!.
exista(E,[_|T]):-
       exista(E,T).

%dif(A: lista, B: lista, Rez: lista)
dif([],_,[]):-!.

dif([Ha|Ta],B,Rez):-
    exista(Ha,B),!,
    dif(Ta,B,Rez).

dif([Ha|Ta],B,[Ha|Rez]):-
    \+ exista(Ha,B),
    dif(Ta,B,Rez).

%test1 - incercam sa facem diferenta a 2 liste vide
testa1:-
    dif([],[],R),
    R = [].

%test2 - A vida
testa2:-
    dif([],[1,2,3,4,5],R),
    R = [].

%test3 - B vida
testa3:-
    dif([1,2,3],[],R),
    R = [1,2,3].

%test4 - A nevida, B nevida, A != B
testa4:-
    dif([1,2,3,4],[1,5,6,7,8],R),
    R = [2,3,4].

%test5 - A = B
testa5:-
    dif([1,2,3,4],[1,2,3,4],R),
    R = [].


%b) Sa se scrie un predicat care adauga intr-o lista dupa fiecare
% element par valoarea 1.

%adauga(L: lista, Rez: lista)
adauga([],[]):-!.

 adauga([H|T],[H,1|RezT]):-
    0 is H mod 2,!,
    adauga(T,RezT).

adauga([H|T],[H|RezT]):-
    1 is H mod 2,!,
    adauga(T,RezT).

%test1 - incercam sa adaugam intr-o lista vida
testb1:-
    adauga([],R),
    R = [].

% test2 - incercam sa adaugam intr-o lista care contine doar elemente
% impare
testb2:-
    adauga([1,1,1,1],R),
    R = [1,1,1,1].

%test3 - adaugam intr-o lista care contine doar elemente pare
testb3:-
    adauga([2,4,6,8],R),
    R = [2,1,4,1,6,1,8,1].

%test4 - adaugam intr-o lista mixta (care are ambele paritati)
testb4:-
    adauga([1,2,3,4,5],R),
    R = [1,2,1,3,4,1,5].
