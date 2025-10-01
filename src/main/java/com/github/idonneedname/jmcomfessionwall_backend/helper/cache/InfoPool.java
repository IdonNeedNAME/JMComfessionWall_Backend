package com.github.idonneedname.jmcomfessionwall_backend.helper.cache;

import java.util.ArrayList;

public class InfoPool<T> {
    public ArrayList<T> pool;
    public int readingCount=0;
    public InfoPool() {
        pool = new ArrayList<>();
    }
    public void notifyReadingEnd()
    {
        readingCount--;
    }
    public void notifyReadingStart()
    {
        readingCount++;
    }
    public boolean writable()
    {
        return readingCount==0;
    }
}
