package com.morgozlan.cryptoproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mor Gozlan on 11/12/2017.
 */

public class CoinsAdapter extends BaseAdapter {
    //context of raw data & context
    private final Coin [] coins;
    private final Context context;
    //dependency injection to context & raw data
    public CoinsAdapter(Context context, Coin[] coins) {
        this.coins = coins;
        this.context = context;
    }

    @Override //how many items to adapt
    public int getCount() {
        return coins.length ;
    }

    @Override//get raw item - from adapter by index
    public Coin getItem(int i) {
        return coins[i];
    }

    @Override//get raw item id - by index
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override//get view - to be show on the screen
    public View getView(int i, View view, ViewGroup parent) {

        if(view == null){//Lazy instantiaion - create views only when needed - to allow recycling
            view = LayoutInflater.from(context).inflate(R.layout.item,null);
        }
        final Coin coin = getItem(i);
        ((TextView)view.findViewById(R.id.symbol)).setText(coin.symbol);
        ((TextView)view.findViewById(R.id.name)).setText(coin.name);
        ((TextView)view.findViewById(R.id.rank)).setText(""+coin.rank);
        ((TextView)view.findViewById(R.id.priceUSD)).setText("$"+coin.price);
        ((TextView)view.findViewById(R.id.priceBTC)).setText(""+coin.price_btc);
        ((TextView)view.findViewById(R.id.volume)).setText(""+coin.percent_change_24h+"%");

        Double x =  Double.parseDouble(coin.percent_change_24h);
        if (x > 0){
            ((TextView)view.findViewById(R.id.volume)).setText("+"+coin.percent_change_24h);
            ((TextView)view.findViewById(R.id.volume)).setTextColor(colorByRes(R.color.plus));
        }else
        {
            //((TextView)view.findViewById(R.id.volume)).setText("-"+coin.percent_change_24h);
            ((TextView)view.findViewById(R.id.volume)).setTextColor(colorByRes(R.color.minus));
        };
        ImageLoadingTask.loadImage("https://images.coinviewer.io/currencies/32x32/"+coin.id.toLowerCase()+".png",
                ((ImageView)view.findViewById(R.id.iconCoins)));
        //setImageURI(Uri.parse("https://images.coinviewer.io/currencies/32x32/"+coin.name+".png"));

        //((TextView)view).setText("symbol: "+coin.symbol+"\n"+"rank: "+coin.rank+"\n"+"name: "+coin.name+"\n"+"price: "+coin.price+"$\n"+"volume (24H): "+coin.percent_change_24h+"%"+"\n"+"price btc: "+coin.price_btc);

        return view;

    }

    private int colorByRes(int resName){
        return context.getResources().getColor(resName);
    }
    //1) class - image loading - loadInBackground - when done - sync back to UI main thread
    //2) class - uses - flyweight dataStructure with lazy loading - if image for item - isn't exists yet - then - load it
    //3) if already loaded (exists) - re use

}
