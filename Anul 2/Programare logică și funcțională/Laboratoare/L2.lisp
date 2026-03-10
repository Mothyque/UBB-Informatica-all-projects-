; Sa se decida daca daca un arbore de tipul 2 (A(B) (C (D) (E))) este echilibrat 

(defun inaltime (arb)
    (cond
        ((null arb) 0)
        (T (+ 1 (max(inaltime(cadr arb)) (inaltime(caddr arb)))))
    )
)

(defun echilibrat (arb)
    (cond
        ((null arb) T)
        (T(and 
            (<= (abs (- (inaltime(cadr arb)) (inaltime(caddr arb)))) 1) 
            (echilibrat (cadr arb)) 
            (echilibrat (caddr arb)))
        )
    )
)

(format t "Test 1 (Echilibrat): ~a~%" (echilibrat '(A (B) (C))))
(format t "Test 2 (Dezechilibrat): ~a~%" (echilibrat '(A (B (C (D) nil)) nil)))