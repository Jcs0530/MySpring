package com.cs.ioc.testBean;

public class Department {
    private User user;
    private int id;

    public Department(User user, int id) {
        this.user = user;
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "user=" + user +
                ", id=" + id +
                '}';
    }
}
