(defun inaltime (arbore)
  (cond
    ((null arbore) 0)
    (t (+ 1 (max(inaltime (cadr arbore)) (inaltime (caddr arbore)))))
    )
  )

(defun echilibrat (arbore)
  (cond
    ((null arbore) t)
    (t (and
	  (<= (abs (- (inaltime(cadr arbore)) (inaltime (caddr arbore)))) 1)
	  (echilibrat (cadr arbore))
	  (echilibrat (caddr arbore))
	 )
       )
    )
  )
