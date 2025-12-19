; Problema 3
; a) Definiti o functie care intoarce suma produsul a doi vectori.

(defun produs_vectori(v1 v2)
    (cond
        ((null v1) 0)
        (T
            (+
                (* (car v1) (car v2))
                (produs_vectori(cdr v1) (cdr v2))
            )
        )   
    )
)
(format T "Test a) ~% Rezultat: ~d~%~%" (produs_vectori '(2 2 3) '(4 5 6)))


; b) Sa se construiasca o functie care intoarce adancimea unei liste

(defun gaseste_max(l)
    (cond
        ((null l) 0)
        (T 
            (max
                (adancime(car l))
                (gaseste_max(cdr l))
            )
        )
    )
)

(defun adancime(n)
    (cond
        ((atom n) 0)
        (T (+ 1 (gaseste_max n)))
    )
)


(format T "Test b) ~% Rezultat: ~d~%~%" (adancime '(A(B (C)) D)))

; c) Definiti o functie care sorteaza fara pastrarea dublurilor o lista liniara
(defun insereaza(e l)
    (cond
        ((null l) (list e))

        ((= e (car l)) l)

        ((< e (car l)) (cons e l))

        (T(cons (car l) (insereaza e (cdr l))))
    )
)

(defun sorteaza(l)
    (cond 
        ((null l) l)

        (T (insereaza(car l) (sorteaza(cdr l))))
    )
)
(format T "Test c) ~% Rezultat: ~a~%~%" (sorteaza '(3 1 5 3 3 3 3 2 5 8)))

;d) Sa se scrie o functie care intoarce intersectia a doua multimi

(defun exista(e l)
    (cond
        ((null l) nil)
        ((= e (car l)) T)
        (T (exista e (cdr l)))
    )
)

(defun intersectie(a b)
    (cond
        ((null a) nil)
        ((exista (car a) b)
        (cond
            ((exista (car a)(cdr a)) (intersectie (cdr a) b))
            (T (cons (car a) (intersectie (cdr a) b)))
        ))
        (T (intersectie (cdr a) b))
        
    )
)

(format T "Test d) ~% Rezultat: ~a~%~%" (intersectie '(3 1 5 3 2 5 8 6 7 67) '(1 2 3 4 5 6)))
