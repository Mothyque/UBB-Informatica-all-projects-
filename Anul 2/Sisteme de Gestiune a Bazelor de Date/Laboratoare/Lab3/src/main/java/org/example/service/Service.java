package org.example.service;

import org.example.domain.Entity;
import org.example.domain.exception.ValidationException;
import org.example.domain.validator.Validator;
import org.example.repository.Repository;
import org.example.utils.Observable;

import java.util.Optional;

/**
 * Clasă generică de Service care acționează ca un strat de business (Business Logic Layer)
 * între Controller (interfața grafică) și Repository (baza de date).
 * * Extinde clasa Observable pentru a implementa design pattern-ul Observer:
 * orice modificare a datelor (adăugare, ștergere, modificare) va notifica automat
 * interfața grafică pentru a se actualiza.
 *
 * @param <ID> Tipul identificatorului entității (ex: Integer)
 * @param <E>  Tipul entității gestionate (trebuie să extindă clasa de bază Entity)
 */
public class Service<ID, E extends Entity<ID>> extends Observable
{
    private final Repository<ID, E> repository;
    private final Validator<E> validator;

    /**
     * Constructor care inițializează Service-ul cu un repository și un validator specific.
     * @param repository Obiectul responsabil de operațiile CRUD pe baza de date.
     * @param validator  Obiectul responsabil de validarea regulilor de business pentru entitate.
     */
    public Service(Repository<ID, E> repository, Validator<E> validator)
    {
        this.repository = repository;
        this.validator = validator;
    }

    /**
     * Constructor care inițializează Service-ul doar cu un repository, fără validare suplimentară.
     * @param repository Obiectul responsabil de operațiile CRUD pe baza de date.
     */
    public Service(Repository<ID, E> repository)
    {
        this.repository = repository;
        this.validator = null;
    }

    /**
     * Validează și adaugă o nouă entitate în baza de date.
     * Dacă operațiunea are succes, notifică toți observatorii (interfața grafică) pentru a-și face refresh.
     * * @param entity Entitatea care urmează să fie adăugată.
     * @return Optional conținând entitatea salvată dacă a reușit, sau empty dacă a eșuat.
     * @throws ValidationException dacă entitatea nu respectă regulile de business (validator).
     */
    public Optional<E> add(E entity) throws ValidationException
    {
        if (validator != null)
        {
            validator.validate(entity);
        }

        Optional<E> saved = repository.save(entity);

        if (saved.isPresent())
        {
            notifyObservers();
        }
        return saved;
    }

    /**
     * Șterge o entitate din baza de date pe baza identificatorului său.
     * Dacă operațiunea are succes, notifică observatorii pentru a actualiza interfața.
     * * @param id Identificatorul entității care trebuie ștearsă.
     * @return Optional conținând entitatea ștearsă, sau empty dacă entitatea nu a fost găsită.
     */
    public Optional<E> delete(ID id)
    {
        Optional<E> deleted = repository.delete(id);
        if (deleted.isPresent())
        {
            notifyObservers();
        }
        return deleted;
    }

    /**
     * Validează și actualizează datele unei entități existente în baza de date.
     * Dacă operațiunea are succes, notifică observatorii pentru a actualiza vizualizarea.
     * * @param entity Entitatea conținând noile date (trebuie să aibă un ID valid).
     * @return Optional conținând entitatea actualizată, sau empty dacă nu a fost găsită.
     * @throws ValidationException dacă noile date ale entității sunt invalide.
     */
    public Optional<E> update(E entity) throws ValidationException
    {
        if (validator != null)
        {
            validator.validate(entity);
        }

        Optional<E> updated = repository.update(entity);
        if (updated.isPresent())
        {
            notifyObservers();
        }
        return updated;
    }

    /**
     * Caută o entitate specifică în baza de date folosind identificatorul ei.
     * * @param id Identificatorul entității căutate.
     * @return Optional cu entitatea găsită, sau empty în caz contrar.
     */
    public Optional<E> findOne(ID id)
    {
        return repository.findOne(id);
    }

    /**
     * Returnează toate entitățile gestionate de acest Service.
     * * @return Un obiect de tip Iterable care conține toate entitățile din baza de date.
     */
    public Iterable<E> findAll()
    {
        return repository.findAll();
    }

    /**
     * Returnează numărul total de entități din baza de date gestionate de acest Service.
     * * @return Numărul de înregistrări (int).
     */
    public int size()
    {
        return repository.size();
    }
}