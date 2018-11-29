package com.goldennode.client;

public class TestBean {
    public TestBean(String property1, int property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    public TestBean() {
    }

    private String property1;
    private int property2;

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public int getProperty2() {
        return property2;
    }

    public void setProperty2(int property2) {
        this.property2 = property2;
    }

    @Override
    public String toString() {
        return "TestBean [property1=" + property1 + ", property2=" + property2 + "]";
    }
}
