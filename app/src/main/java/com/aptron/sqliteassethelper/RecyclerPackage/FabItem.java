package com.aptron.sqliteassethelper.RecyclerPackage;

public class FabItem {

    String text1;
    String text2;

    public FabItem(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }
    public FabItem(){

    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
