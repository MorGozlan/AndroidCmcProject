package com.morgozlan.cryptoproject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.morgozlan.cryptoproject.fragments.AlertFragment;
import com.morgozlan.cryptoproject.fragments.CurrencyFragment;
import com.morgozlan.cryptoproject.fragments.PortfolioFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.coinmarketcap.com/v1/ticker/?limit=20";
    //private static int SPLASH_TIME_OUT = 4000;
    private Context self = this;

    private ListView currencyList;
    private ImageView icon;
    private TextView symbol, name, priceUSD, priceBTC, priceBTCTxt, rank, rankTxt, volume, volumeTxt, priceUSDTxt;
    private TextView mTextMessage;

    private static Fragment alertFragment = new AlertFragment();
    private static Fragment portfolioFragment = new PortfolioFragment();
    private static Fragment currencyFragment = new CurrencyFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.txt);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //currencyList = (ListView)findViewById(R.id.currencyList);
        icon = (ImageView) findViewById(R.id.icon);
        symbol = (TextView) findViewById(R.id.symbol);
        name = (TextView) findViewById(R.id.name);
        priceUSD = (TextView) findViewById(R.id.priceUSD);
        priceBTC = (TextView) findViewById(R.id.priceBTC);
        priceBTCTxt = (TextView) findViewById(R.id.priceBTCTxt);
        rank = (TextView) findViewById(R.id.rank);
        rankTxt = (TextView) findViewById(R.id.rankTxt);
        volume = (TextView) findViewById(R.id.volume);
        volumeTxt = (TextView) findViewById(R.id.volumeTxt);
        priceUSDTxt = (TextView) findViewById(R.id.priceUSDTxt);

        //currencyList.setOnItemClickListener(new ItemList());


    }

    private class ItemList implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ViewGroup viewg=(ViewGroup)view;
            TextView textv=(TextView)viewg.findViewById(R.id.name);

            Toast.makeText(MainActivity.this, textv.getText().toString(), Toast.LENGTH_SHORT).show();
            //
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        loadJsonInBackground();

        openFragment(currencyFragment);

    }
    private void loadJsonInBackground(){
    new Thread(){
        public void run(){
            try{
                JSONArray arr = new JSONArray(new HttpRequest(API_URL).prepare().sendAndReadString());
                int len = arr.length();
                Coin [] coins = new Coin[len];
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
               // showCoins(coins);
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
                currencyList.setAdapter(new CoinsAdapter(MainActivity.this,coins));
            }
        });
    }


    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = getFragment(item.getItemId());
            openFragment(fragment);
            return fragment != null;
        }
    };

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragment != null){//if exists
            transaction.replace(R.id.frame, fragment);//replace to it
            transaction.addToBackStack(null);//no name to be remembered in backStack
            transaction.commit();//execute transaction
        }
    }

    private Fragment getFragment (int itemId){
        switch (itemId) {
            case R.id.navigation_home:
                return currencyFragment;
            case R.id.navigation_portfolio:
                return portfolioFragment;
            case R.id.navigation_alert:
                return alertFragment;
        }
        return null;
    }

}
