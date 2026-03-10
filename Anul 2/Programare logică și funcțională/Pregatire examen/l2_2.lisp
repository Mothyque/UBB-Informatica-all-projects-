(defun noduri_nivel_k (arbore nivel k)
  (cond
    ((null arbore) nil)
    ((equal nivel k) (list (car arbore)))
    (t
      (mapcan #'(lambda (subarbore) (noduri_nivel_k subarbore (+ 1 nivel) k)) (cdr arbore))
    )
    )
  )

(defun test()
  (equal (noduri_nivel_k '(A (B) (C)) '0 '1) '(B C))
  (equal (noduri_nivel_k '(A (B (C (D)))) '0 '2) '(C))
)
