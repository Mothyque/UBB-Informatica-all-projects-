(defun scoate_numere (lista)
  (cond 
    ((null lista) nil)
    ((numberp (car lista))
	(scoate_numere (cdr lista))	
    )
    (t
      (cons (car lista) (scoate_numere (cdr lista)))
      )
    )
  )

(defun lipeste (litera lista)
  (cond
    ((null lista) nil)
    (t
      (cons (list litera (car lista)) (lipeste litera (cdr lista)))
      )
    )
  )

(defun fa_lista_cu_lipit (lista)
  (cond
    ((null (cdr lista)) nil)
    (t
      (append (lipeste (car lista) (cdr lista)) (fa_lista_cu_lipit (cdr lista)))
      )
    )
  )

(defun exista (element lista)
  (cond 
    ((null lista) nil)
    ((equal element (car lista)) T)
    (t
      (exista element (cdr lista))
      )
    )
  )

(defun sterge_duplicate (lista)
  (cond
    ((null lista) nil)
    ((equal (exista (car lista) (cdr lista)) T)
     (sterge_duplicate (cdr lista))
     )
   (t
     (cons (car lista) (sterge_duplicate (cdr lista)))
     )
   )
  )

(defun genereaza_perechi (lista)
  (let ((lista_fara_numere (scoate_numere lista)))
    (let ((lista_lipita (fa_lista_cu_lipit lista_fara_numere)))
      (let ((lista_finala (sterge_duplicate lista_lipita)))
	lista_finala
	)
      )
    )
  )
   
(defun test()
  (and
    (equal (genereaza_perechi '(A 2 A B 3 C D 1)) '((A A) (A B) (A C) (A D) (B C) (B D) (C D)))
    (equal (genereaza_perechi '(X Y Z)) '((X Y) (X Z) (Y Z)))
    (equal (genereaza_perechi '(A B C D)) '((A B) (A C) (A D) (B C) (B D) (C D)))
    (equal (genereaza_perechi '(1 2 3 4 5)) '())
;   (equal (genereaza_perechi '(1 A 2 B 3 4 5 C A B C)) '((A B) (A C) (A A) (B C) (B B) (C C)))
  )
  )
