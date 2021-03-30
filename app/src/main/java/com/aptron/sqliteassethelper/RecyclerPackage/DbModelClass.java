package com.aptron.sqliteassethelper.RecyclerPackage;

import java.util.Comparator;

public class DbModelClass {
    String text1;
    String text2;

    public DbModelClass(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
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
    public static Comparator<DbModelClass> ByDownload =new Comparator<DbModelClass>() {
        @Override
        public int compare(DbModelClass o1, DbModelClass o2) {
            return String.valueOf(o1.text1).compareTo(String.valueOf(o2.text1));
        }
    };
}
