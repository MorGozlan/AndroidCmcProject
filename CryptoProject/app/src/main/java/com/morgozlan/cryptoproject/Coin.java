package com.morgozlan.cryptoproject;

/**
 * Created by Mor Gozlan on 14/12/2017.
 */

public class Coin {
    public final String name,symbol,price_btc,id;
    public final int rank;
    public final  double price;
    public final  String percent_change_24h;
    // public String img_url;

    public Coin(String name, int rank, double price,String percent_change_24h,String symbol,String price_btc,String id) {
        this.name = name;
        this.rank = rank;
        this.price = price;
        this.percent_change_24h = percent_change_24h;
        this.symbol = symbol;
        this.price_btc = price_btc;
        this.id = id;
        //this.img_url = img_url;

    }
}
