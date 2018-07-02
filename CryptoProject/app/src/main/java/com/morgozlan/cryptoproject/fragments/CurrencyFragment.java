package com.morgozlan.cryptoproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.morgozlan.cryptoproject.Coin;
import com.morgozlan.cryptoproject.CoinsAdapter;
import com.morgozlan.cryptoproject.HttpRequest;
import com.morgozlan.cryptoproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Mor Gozlan on 23/04/2018.
 */


public class CurrencyFragment extends Fragment {
    private static final String API_URL = "https://api.coinmarketcap.com/v1/ticker/?limit=20";
    private ListView currencyList;
    private ImageView icon;
    private TextView symbol,name,priceUSD,priceBTC,priceBTCTxt,rank,rankTxt,volume,volumeTxt,priceUSDTxt;
    private TextView mTextMessage;
    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.activity_currency, null);
        currencyList = (ListView)root.findViewById(R.id.currencyList);
        icon = (ImageView)root.findViewById(R.id.icon);
        symbol = (TextView)root.findViewById(R.id.symbol);
        name = (TextView)root.findViewById(R.id.name);
        priceUSD = (TextView)root.findViewById(R.id.priceUSD);
        priceBTC = (TextView)root.findViewById(R.id.priceBTC);
        priceBTCTxt = (TextView)root.findViewById(R.id.priceBTCTxt);
        rank = (TextView)root.findViewById(R.id.rank);
        rankTxt = (TextView)root.findViewById(R.id.rankTxt);
        volume = (TextView)root.findViewById(R.id.volume);
        volumeTxt = (TextView)root.findViewById(R.id.volumeTxt);
        priceUSDTxt = (TextView)root.findViewById(R.id.priceUSDTxt);

        currencyList.setOnItemClickListener(new ItemList());
        return root;

    }
    private class ItemList implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ViewGroup viewg=(ViewGroup)view;
            TextView textv=(TextView)viewg.findViewById(R.id.name);

            Toast.makeText(getActivity(), textv.getText().toString(), Toast.LENGTH_SHORT).show();
            //
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadJsonInBackground();
    }

    private void loadJsonInBackground(){
        new Thread(){
            public void run(){
                try{
                    JSONArray arr = new JSONArray(new HttpRequest(API_URL).prepare().sendAndReadString());
                    int len = arr.length();
                    Coin[] coins = new Coin[len];
                    for(int i =0; i<len; i++){
                        JSONObject coinObj = arr.getJSONObject(i);
                        coins [i] = new  Coin (coinObj.getString("name"),
                                Integer.parseInt(coinObj.getString("rank")),
                                Double.parseDouble(coinObj.getString("price_usd")),
                                coinObj.getString("percent_change_24h"),
                                coinObj.getString("symbol"),
                                coinObj.getString("price_btc"),
                                coinObj.getString("id")
                        );
                    }
                     showCoins(coins);
                }catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void showCoins(final Coin [] coins){
        currencyList.post(new Runnable() {
            @Override
            public void run() {
                currencyList.setAdapter(new CoinsAdapter(getActivity(),coins));
            }
        });
    }
}
