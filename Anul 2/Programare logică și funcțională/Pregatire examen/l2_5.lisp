(defun nivel_nod (arbore nivel k)
  (cond
    ((null arbore) nil)
    ((equal (car arbore) k) (list nivel))
    (t
      (mapcan #'(lambda (subarbore) (nivel_nod subarbore (+ 1 nivel) k)) (cdr arbore))
      )
    )
  )
(defun main (arbore k)
  (car (nivel_nod arbore 0 k)))

(defun test()
  (equal (main '(A (B)) 'B) '1)
  (equal (main '(A (B)) 'A) '0)
  (equal (main '(A (B)) 'C) nil)
  )
