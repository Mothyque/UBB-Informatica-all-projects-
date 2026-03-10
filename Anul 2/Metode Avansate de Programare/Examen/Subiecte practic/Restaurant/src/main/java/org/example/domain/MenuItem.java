package org.example.domain;

public class MenuItem extends Entity<Integer>
{
    private String category;
    private String item;
    private float price;
    private String currency;

    public MenuItem(int id, String category, String item, float price, String currency)
    {
        setId(id);
        this.category = category;
        this.item = item;
        this.price = price;
        this.currency = currency;
    }

    public MenuItem(String category, String item, float price, String currency)
    {
        this.category = category;
        this.item = item;
        this.price = price;
        this.currency = currency;
    }

    public String getCategory() {return category;}
    public String getItem() {return item;}
    public float getPrice() {return price;}
    public String getCurrency() {return currency;}

    public void setCategory(String category){this.category = category;}
    public void setItem(String item){this.item = item;}
    public void setPrice(float price){this.price = price;}
    public void setCurrency(String currency){this.currency = currency;}
}
