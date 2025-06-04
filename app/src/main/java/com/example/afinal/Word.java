package com.example.afinal;

public class Word {
    private String word;    //单词
    private String pron;    //音标
    private String interpret;  //中文释义
    private int mastered;  //0未掌握，1已掌握
    private int errorNum;           //错误次数

    public Word(String word, String pron, String definition, int mastered, int errorNum){
        this.word = word;
        this.pron = pron;
        this.mastered=mastered;
        this.interpret = definition;
        this.errorNum = errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setMastered(int mastered) {
        this.mastered = mastered;
    }

    public String getWord(){
        return  word;
    }
    public String getPron(){
        return  pron;
    }
    public String getInterpret(){
        return interpret;
    }
    public int getShowNum() {
        return errorNum;
    }

    public void setShowNum(int wrongCount) {
    }
}

