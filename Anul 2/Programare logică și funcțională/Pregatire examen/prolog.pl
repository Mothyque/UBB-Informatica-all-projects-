selecteazaComb(E, [E|T], T).

selecteazaComb(E, [_|T], R):-  
    selecteazaComb(E,T,R).

selecteazaPerm(E, [E|T], T).

selecteazaPerm(E, [H|T], [H|R]):-
    selecteazaPerm(E, T, R).