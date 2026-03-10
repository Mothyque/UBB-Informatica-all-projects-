package org.example.service;

import org.example.domain.MenuItem;
import org.example.repository.MenuItemRepo;

import java.util.*;

public class MenuItemService
{
    private MenuItemRepo menuItemRepo;

    public MenuItemService(MenuItemRepo menuItemRepo)
    {
        this.menuItemRepo = menuItemRepo;
    }

    public void add(String category, String item, float price, String currency)
    {
        menuItemRepo.save(new MenuItem(category, item, price, currency));
    }

    public void delete(int id)
    {
        menuItemRepo.delete(id);
    }

    public Iterable<MenuItem> getAll()
    {
        return menuItemRepo.findAll();
    }

    public Map<String, List<MenuItem>> getMenu()
    {
        Map<String, List<MenuItem>> menu = new HashMap<>();
        Iterable<MenuItem> items = menuItemRepo.findAll();
        for(MenuItem item : items)
        {
            menu.putIfAbsent(item.getCategory(), new ArrayList<>());
            menu.get(item.getCategory()).add(item);
        }
        return menu;
    }
}
