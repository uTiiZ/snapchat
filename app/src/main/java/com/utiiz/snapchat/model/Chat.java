package com.utiiz.snapchat.model;

import com.utiiz.snapchat.interfaces.ChatInterface;

import java.util.UUID;

public class Chat implements Comparable{

    public UUID id;
    public String name;
    public int height;
    public int color;

    public Chat(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.height = ChatInterface.HEIGHT_NOT_INITIALIZED;
        this.color = ChatInterface.COLOR_NOT_INITIALIZED;
    }

    public Chat(String name, int height, int color) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.height = height;
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int compareTo(Object o) {
        Chat compare = (Chat) o;

        if (compare.getName().equals(this.name) && compare.getName().equals(this.name)){
            return 0;
        }
        return 1;
    }
}
