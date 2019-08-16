package com.example.quickindex;

/**
 * author : yangjunjin
 * date : 2019/8/17 0:10
 */
public class Friend implements Comparable<Friend> {
    private String name;
    public String pinyin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Friend(String name) {
        this.name = name;
        this.pinyin = CnToCharUntil.getSpell(name,true);
    }


    @Override
    public int compareTo(Friend o) {
        return pinyin.compareTo(o.pinyin);
    }
}
