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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((property1 == null) ? 0 : property1.hashCode());
        result = prime * result + property2;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestBean other = (TestBean) obj;
        if (property1 == null) {
            if (other.property1 != null)
                return false;
        } else if (!property1.equals(other.property1))
            return false;
        if (property2 != other.property2)
            return false;
        return true;
    }
}
