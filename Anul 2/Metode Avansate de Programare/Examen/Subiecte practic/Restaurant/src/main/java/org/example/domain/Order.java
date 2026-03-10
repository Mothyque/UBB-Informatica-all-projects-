package org.example.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends Entity<Integer>
{
    private Table table;
    private List<MenuItem> menuItems = new ArrayList<>();
    private LocalDateTime date;
    private OrderStatus status;

    public Order(Table table, LocalDateTime date, OrderStatus status)
    {
        this.table = table;
        this.date = date;
        this.status = status;
    }

    public Table getTable()
    {
        return table;
    }

    public List<MenuItem> getMenuItems()
    {
        return menuItems;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public OrderStatus getStatus()
    {
        return status;
    }

    public void setTable(Table table)
    {
        this.table = table;
    }

    public void setMenuItems(List<MenuItem> menuItems)
    {
        this.menuItems = menuItems;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public void setStatus(OrderStatus status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        String numele_produselor = "";
        for(MenuItem item : getMenuItems())
        {
            numele_produselor += item.getItem() + ", ";
        }
        return "Masa: " + table.getId() + " Data: " + date + " Produse: " + numele_produselor;
    }
}
