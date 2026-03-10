(defun inlocuieste (arbore x y)
  (cond
    ((null arbore) nil)
    (t
      (cons
	(if (equal (car arbore) x) y (car arbore))
      	(mapcar #'(lambda(subarbore) (inlocuieste subarbore x y)) (cdr arbore))
      )
    )
  )
)
(defun test()
  (inlocuieste '(1 (2 (4)) (3 (2))) '2 '3)
)
