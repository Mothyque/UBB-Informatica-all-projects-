(defun cauta-in-lista (arbori nod)
  (cond
    ((null arbori) nil)
    (t
      (let ((rezultat (cale (car arbori) nod)))
	(cond
	  (rezultat rezultat)
	  (t (cauta-in-lista (cdr arbori) nod))
	  )
	)
      )
    )
  )

(defun cale (arbore nod)
  (cond 
    ((null arbore) nil)
    ((equal (car arbore) nod) (list nod))
    (t
      (let ((cale-copil (cauta-in-lista (cdr arbore) nod)))
	(cond
	  (cale-copil  (cons(car arbore) cale-copil))
	  (t nil)
	)
	)
      )
    )
  )
(defun test()
  (equal (cale '(A (B) (C (D) (E))) 'E) '(A C E))
  (equal (cale '(A (B)) 'A) '(A))
  (equal (cale '(A (B)) 'X) NIL)
  )
