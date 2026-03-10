package com.ubb.repository.file;

import com.ubb.domain.Entity;
import com.ubb.repository.InMemoryRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Optional;

public abstract class FileRepository <ID, E extends Entity<ID>> extends InMemoryRepository<ID, E>
{
    private final String fileName;
    public FileRepository(String fileName)
    {
        super(new HashMap<>());
        this.fileName = fileName;
        loadData();
    }

    private void loadData()
    {
        File file = new File(fileName);
        if(!file.exists())
        {
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while((line = br.readLine()) != null)
            {
                E entity = extractEntity(line.trim().split(","));
                super.save(entity);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected abstract E extractEntity(String[] attributes);

    protected abstract String createEntityAsString(E entity);

    @Override
    public Optional<E> save(E entity)
    {
        Optional<E> result = super.save(entity);
        writeToFile();
        return result;
    }

    @Override
    public Optional<E> delete(ID id)
    {
        Optional<E> result = super.delete(id);
        writeToFile();
        return result;
    }

    @Override
    public Optional<E> update(E entity)
    {
        Optional<E> result = super.update(entity);
        writeToFile();
        return result;
    }

    private void writeToFile()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
        {
            for (E entity : super.findAll())
            {
                bw.write(createEntityAsString(entity));
                bw.newLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
