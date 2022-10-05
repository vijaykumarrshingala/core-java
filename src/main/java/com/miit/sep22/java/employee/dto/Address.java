package com.miit.sep22.java.employee.dto;

public class Address {

    private String line1;
    private String line2;
    private String pinCode;

    public Address(String line1, String line2, String pinCode) {
        this.line1 = line1;
        this.line2 = line2;
        this.pinCode = pinCode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
