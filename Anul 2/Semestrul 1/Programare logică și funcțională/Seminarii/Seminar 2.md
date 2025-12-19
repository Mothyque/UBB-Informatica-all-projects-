Seminar 2: Liste în Prolog



**Problema 1**: Să se scrie un predicat care elimină dintr-o listă toate elementele care apar o singură dată.

ex: \[1,2,1,4,1,3,4] => \[1,1,4,1,4]



Sol: - predicat auxiliar care calculează numărul de apariții ale unui element din listă



nr\_aparatii(e; l1,...,ln) =



1\. 0 , lista este vidă,



2\. 1 + nr\_aparitii(e; l2,...,ln) , dacă e = l1,



3\. nr\_aparitii(e; l2...ln) altfel



elimina\_sg\_ap(l1,...,ln; L1...Ln) =



1\. lista vidă, dacă n = 0



2\. elimina\_sg\_ap(l2...ln, L1...Ln) , dacă nr\_aparitii(l1, L1...Ln) = 1,



3\. l1 ⊕ elimina\_sg\_ap(l2,...,ln; L1...Ln), altfel



main(L1...Ln) = elimina\_sg\_ap(L1...Ln; L1...Ln)



Prolog - nedeterminism, backtracking.



Pentru a obține predicate deterministe:



1\. condiții complete pe toate clauzele

&nbsp;  SAU

2\. predicatul "cut"



**Metoda 2 de rezolvare**:



⦁ predicat auxiliar care verifică existența unui element într-o listă

⦁ variabilă auxiliară - lista cu elementele întâlnite până acum

⦁ pentru fiecare element verificăm dacă există în restul listei (l2,...,ln) sau în lista Vizitate



**Problema 2**: Dându-se o listă numerică, să se șteargă toate secvențele de valori crescătoare.



crescatoare(l1,...,ln; v) =



1\. lista vidă, dacă l este vidă;



2\. l1 ⊕ crescatoare(l2,...,ln; l1) dacă l1 >= l2 și v > l1



3\. lista vidă, n = 1 l1 > v



4\. \[l1] , dacă n = 1 și l1 <= v



5\. crescatoare(l2,...,ln, l1), altfel



main(l1,...,ln) =



1\. lista vidă, dacă l vidă



2\. l, dacă n = 1



3\. crescatoare(l1,...,ln, l1)



*IS NU SE FOLOSEȘTE CU LISTE*



