package com.example.votes;

public class Votes {

    private String title;
    private String a1;
    private String a2;
    private String a3;
    private int a1stat;
    private int a2stat;
    private int a3stat;

    public Votes() {
        title = "";
        a1 = "";
        a2 = "";
        a3 = "";
        a1stat = 0;
        a2stat = 0;
        a3stat = 0;
    }

    public Votes(String title, String a1, String a2, String a3) {
        this.title = title;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        a1stat = 0;
        a2stat = 0;
        a3stat = 0;
    }

    public Votes(String title, String a1, String a2, String a3, int a1stat, int a2stat, int a3stat) {
        this.title = title;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a1stat = a1stat;
        this.a2stat = a2stat;
        this.a3stat = a3stat;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public int getA1stat() {
        return a1stat;
    }

    public void setA1stat(int a1stat) {
        this.a1stat = a1stat;
    }

    public int getA2stat() {
        return a2stat;
    }

    public void setA2stat(int a2stat) {
        this.a2stat = a2stat;
    }

    public int getA3stat() {
        return a3stat;
    }

    public void setA3stat(int a3stat) {
        this.a3stat = a3stat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
