(defun inaltime_max (arbore nivel)
    (cond
        ((atom arbore) nivel)
        (t
            (apply #'max
                (mapcar #'
                    (lambda(elem) (inaltime_max elem (+ 1 nivel)))
                    arbore
                )
            )
        )
    )
)

(defun main (arbore)
    (inaltime_max arbore 0)
)