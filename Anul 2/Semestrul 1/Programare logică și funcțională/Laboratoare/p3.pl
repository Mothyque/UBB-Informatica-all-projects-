%Sa se genereze lista aranjamentelor de K elemente, cu elementele unei
% liste date

%candidat(L: lista, Rez: numar)
%candidat(i,o)
candidat([H|_],H).
candidat([_|T],Rez):-
    candidat(T,Rez).

%membru(L:lista, E: numar)
%membru(i,i)
membru([H|_],H).
membru([_|T],E):-
    membru(T,E).

%aranjamente(L: lista, K: numar, A: Lista, Rez: lista)
%aranjamente(i,i,i,o)
aranjamente(_L,0,A,Rez):-
    Rez = A.

aranjamente(L,K,A,Rez):-
    K > 0,
    candidat(L,N),
    \+ membru(A,N),
    K1 is K - 1,
    aranjamente(L,K1,[N|A],Rez).

%main(L:lista, K: numar, Rez: lista)
%main(i,i,o)
main(L,K,Rez):-
    findall(R,aranjamente(L,K,[],R),Rez).

test:- main([2,3,4],2,[[3,2],[4,2],[2,3],[4,3],[2,4],[3,4]]).






