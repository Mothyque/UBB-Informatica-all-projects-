(defun adancime_maxima (arbore nivel)
  (cond
    ((atom arbore) nivel)
    (t 
      (apply #'max
	     (cons nivel
		   (mapcar #'(lambda(subarbore) (adancime_maxima subarbore (+ 1 nivel))) (cdr arbore))
		   )
	     )
      )
    )
  )

(defun test()
  (equal (adancime_maxima '(A (B (C (D (E))))) '0) '4)
  (equal (adancime_maxima '(A (B) (C) (D (E F))) '0) '3)
  )
