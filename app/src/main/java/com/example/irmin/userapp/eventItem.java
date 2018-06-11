package com.example.irmin.userapp;

public class eventItem {
    private  String title2;
    private  String content2;
    private  int amount2;
    private  String category2;
    private  String tel2;
    private  String add2;

    public String getTitle2(){
        return title2;
    }

    public String getContent2(){
        return content2;
    }

    public int getAmount2(){ return amount2; }

    public String getCategory2(){ return category2; }

    public String getTel2(){ return tel2; }

    public String getAdd2() {return add2; }

    public eventItem(String title2, String content2, int amount2, String category2, String tel2, String add2){
        this.title2 = title2;
        this.content2 = content2;
        this.amount2 = amount2;
        this.category2 = category2;
        this.tel2 = tel2;
        this.add2 = add2;
    }
}
