package org.example.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa de bază care implementează conceptul de "Subiect" (sau Observable)
 * din design pattern-ul Observer.
 * Permite altor obiecte (Observer) să se aboneze și să fie notificate
 * atunci când starea acestui obiect se modifică.
 */
public class Observable
{
    // Lista de observatori care așteaptă să fie notificați
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Abonează un nou observator la acest subiect.
     * @param observer Obiectul care implementează interfața Observer.
     */
    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    /**
     * Dezabonează un observator existent.
     * @param observer Obiectul care nu mai dorește să primească notificări.
     */
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    /**
     * Parcurge lista de observatori abonați și apelează metoda update() pentru fiecare.
     * Această metodă trebuie apelată de clasele copil (ex: Service)
     * ori de câte ori datele se modifică (Add, Update, Delete).
     */
    protected void notifyObservers()
    {
        for (Observer observer : observers)
        {
            observer.update();
        }
    }
}