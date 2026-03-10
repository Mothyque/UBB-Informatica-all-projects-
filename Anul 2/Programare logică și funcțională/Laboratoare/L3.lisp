(defun main(l)
    (mapcan #'(lambda(x)
            (cond
                ((atom x) (list x))
                (T (main x))
            )
        )    
    l)
)

(format T "Test: ~a" (main '(((A B) C) (D F E) A (S E F))))