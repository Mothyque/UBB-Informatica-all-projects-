(defun nr_noduri_nivel (arbore nivel k)
  (cond
    ((null arbore) 0)
    ((equal nivel k) 1)
    (t
	(apply #'+
	       (mapcar #'(lambda(subarbore) 
			  (nr_noduri_nivel subarbore (+ 1 nivel) k)) (cdr arbore)
		 )
	)
	)
      )
    )

(defun nivel_maxim (arbore curent maxim nivel_best)
    (let ((nr_noduri (nr_noduri_nivel arbore 0 curent)))
       (cond
	 ((equal nr_noduri 0) nivel_best)
	 ((> nr_noduri maxim)
	  (nivel_maxim arbore (+ 1 curent) nr_noduri curent)
	  )
	 (t 
	   (nivel_maxim arbore (+ 1 curent) maxim nivel_best)
	   )
	 )
       )
    )

(defun noduri_pe_nivelul_maxim (arbore nivel nivel_maxim)
  (cond
	((null arbore) nil)
	((equal nivel nivel_maxim) (list (car arbore)))
	(t
	  (mapcan #'(lambda(subarbore) (noduri_pe_nivelul_maxim subarbore (+ 1 nivel) nivel_maxim)) (cdr arbore))
	  )
	)
  )

(defun main(arbore)
  (let ((n_maxim (nivel_maxim arbore 0 -1 -1)))
    (noduri_pe_nivelul_maxim arbore 0 n_maxim)))

(defun test()
  (and
  	(equal (main '(A (B) (C))) '(B C))
 	(equal (main '(A (B (D) (E)) (C (F)))) '(D E F))
  )
  )
