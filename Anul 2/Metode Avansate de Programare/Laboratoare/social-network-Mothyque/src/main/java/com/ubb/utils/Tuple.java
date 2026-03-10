package com.ubb.utils;

public class Tuple<E1, E2>
{
    private E1 left;
    private E2 right;

    public Tuple(E1 left, E2 right)
    {
        this.left = left;
        this.right = right;
    }

    public E1 getLeft()
    {
        return left;
    }

    public E2 getRight()
    {
        return right;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        if (left != null ? !left.equals(tuple.left) : tuple.left != null) return false;
        return right != null ? right.equals(tuple.right) : tuple.right == null;
    }

    @Override
    public int hashCode()
    {
        return left.hashCode() + right.hashCode();
    }
}
