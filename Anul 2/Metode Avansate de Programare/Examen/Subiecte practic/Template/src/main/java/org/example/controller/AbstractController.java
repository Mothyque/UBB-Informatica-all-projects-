package org.example.controller;

import javafx.scene.control.Alert;
import org.example.domain.Entity;
import org.example.service.Service;
import org.example.utils.Observer;

public abstract class AbstractController<ID, E extends Entity<ID>> implements Observer
{
    protected Service<ID, E> service;

    protected int currentPage = 1;
    protected int pageSize = 5;

    public void setService(Service<ID, E> service)
    {
        this.service = service;
        service.addObserver(this);
        initData();
    }

    @Override
    public abstract void update();

    public void initData()
    {
    }

    public void onPreviousPage()
    {
        if (currentPage > 1)
        {
            currentPage--;
            update();
        }
    }

    public void onNextPage()
    {
        if ((currentPage * pageSize) < service.size())
        {
            currentPage++;
            update();
        }
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    protected void showErrorMessage(String text)
    {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    protected void showInfoMessage(String text)
    {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Info");
        message.setContentText(text);
        message.showAndWait();
    }
}