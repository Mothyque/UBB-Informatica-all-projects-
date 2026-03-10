(defun interclasare (lista1 lista2)
  (cond
    ((and (null lista1) (null lista2)) nil)
    ((null lista1) (cons (car lista2) (interclasare lista1 (cdr lista2))))
    ((null lista2) (cons (car lista1) (interclasare (cdr lista1) lista2)))
    (t
      (cond
	((>= (car lista1) (car lista2)) (cons (car lista2) (interclasare lista1 (cdr lista2))))
	(t (cons (car lista1) (interclasare (cdr lista1) lista2)))
	)
      )
    )
  )

(defun inlocuieste_cu_lista (lista1 lista2 e)
  (cond
    ((null lista1) nil)
    ((equal (car lista1) e) (append lista2 (inlocuieste_cu_lista (cdr lista1) lista2 e)))
    (t
      (cons (car lista1) (inlocuieste_cu_lista (cdr lista1) lista2 e))
    )
    )
  )

(defun puterea10 (n)
  (cond
    ((equal n 0) 1)
    (t
      (* 10 (puterea10 (- n 1)))
      )
    )
  )

(defun transforma_numar(lista)
  (cond
    ((null lista) 0)
    (t
      (+ (* (car lista) (puterea10 (length (cdr lista)))) (transforma_numar (cdr lista)))
      )
    )
  )

(defun suma_liste (lista1 lista2)
  (+ (transforma_numar lista1) (transforma_numar lista2)))


(defun cmmdc (a b)
  (cond
    ((equal b nil) a)
    ((equal 0 (mod a b)) b)
    (t 
      (cmmdc b (mod a b))
      )
    )
  )

(defun cmmdc_sir(lista)
  (cond
    ((null lista) nil)
    (t 
      (cmmdc (car lista) (cmmdc_sir (cdr lista)))
      )
    )
  )
