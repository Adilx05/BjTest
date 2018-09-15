package com.kosezade.blackjackturkan;

import android.app.Notification;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String [] kartTur = {"As","2","3","4","5","6","7","8","9","10","J","Q","K"};
    private String [] kartRenk = {"Karo","Sinek","Kupa","Maça"};
    private String rakip1,rakip2,rakip3,rakip4,biz1,biz2,biz3,biz4,zorluk;
    private Integer rakipPuan,bizPuan,bizSkor,rakipSkor;
    private TextView rakipEli,bizimEl,skor,durum, zorlukTv1;
    private Button verBt,durBt,restartBt;
    private Boolean kazandik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = null;
        sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        try
        {
            zorluk = sharedPref.getString("mySettings",null);
        }
        catch (Exception ex){

        }

        //ilk çalışmayı kontrol edecek
        if (zorluk == null)
        {
            zorluk = "kolay";
            editor.putString("mySettings","kolay");
            editor.commit();
        }

        rakipPuan = 0;
        bizPuan = 0;
        rakipEli = (TextView)findViewById(R.id.rakipEli);
        bizimEl = (TextView)findViewById(R.id.bizimEl);
        restartBt = (Button)findViewById(R.id.yenidenBaslat);
        skor = (TextView)findViewById(R.id.elSonucu);
        verBt = (Button)findViewById(R.id.verBt);
        durBt = (Button)findViewById(R.id.tamamBt);
        durum = (TextView)findViewById(R.id.skorTablosu);
        zorlukTv1 = (TextView)findViewById(R.id.zorlukTv);
        zorlukTv1.setText("Zorluk : " + zorluk);

        rakip1 = kartSecRakip();
        rakip2 = kartSecRakip();
        biz1 = kartSecBiz();
        biz2 = kartSecBiz();
        bizSkor = 0;
        rakipSkor = 0;
        durum.setText("Skor"+ "\n" + "Siz :" + bizSkor + "\n" + "Rakip :" + rakipSkor);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rakipPuan = 0;
                bizPuan = 0;
                durBt.setEnabled(true);
                verBt.setEnabled(true);

                ayarlariSifirla();

                rakip1 = kartSecRakip();
                rakip2 = kartSecRakip();

                biz1 = kartSecBiz();
                biz2 = kartSecBiz();

                rakipEli.setText("Rakibin Eli : " + "Gizli");
                bizimEl.setText("Bizim Elimiz : " + biz1 + " ve " + biz2);
                skor.setText("Eliniz " + bizPuan + " puan");


            }
        };
        restartBt.setOnClickListener(onClickListener);

        rakipEli.setText("Rakibin Eli : " + "Gizli");
        bizimEl.setText("Bizim Elimiz : " + biz1 + " ve " + biz2);
        skor.setText("Eliniz " + bizPuan + " puan");

        View.OnClickListener onClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biz3 == null)
                {
                    biz3 = kartSecBiz();
                    bizimEl.setText(bizimEl.getText() + " ve " + biz3);
                    if (bizPuan > 21)
                    {
                        kazandik = false;
                       sonucKontrol();
                        skorYaz();
                    }
                    else {
                        skor.setText("Eliniz " + bizPuan + " puan");
                    }
                }

                else {
                    biz4 = kartSecBiz();
                    bizimEl.setText(bizimEl.getText() + " ve " + biz4);
                    verBt.setEnabled(false);
                    durBt.setEnabled(false);
                    if (bizPuan > 21)
                    {
                        kazandik = false;
                        sonucKontrol();
                        skorYaz();
                    }
                    else {
                        skor.setText("Eliniz " + bizPuan + " puan");
                    }
                }
            }
        };
        verBt.setOnClickListener(onClickListener1);


        View.OnClickListener onClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rakipOyna();

            }
        };
        durBt.setOnClickListener(onClickListener2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Aşkım için BlackJack Örneği", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void rakipOyna() {


        if (zorluk == "kolay")
        {
            if (rakip3 == null  && rakipPuan < 12)
            {
                rakip3 = kartSecRakip();
                rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2 + " ve " + rakip3);
                if (rakipPuan > 21)
                {
                    kazandik = true;
                    sonucKontrol();
                }
                else if (rakipPuan < 12)
                {
                    rakip4 = kartSecRakip();
                    rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2 + " ve " + rakip3 + " ve " + rakip4);
                    if (rakipPuan > 21)
                    {
                        kazandik = true;
                        sonucKontrol();
                    }
                    else {
                        if (bizPuan > rakipPuan)
                        {
                            kazandik = true;
                            sonucKontrol();
                        }
                        else {
                            kazandik = false;
                            sonucKontrol();
                        }
                    }
                }
                else {
                    if (bizPuan > rakipPuan)
                    {
                        kazandik = true;
                        sonucKontrol();
                    }
                    else {
                        kazandik = false;
                        sonucKontrol();
                    }
                }

            }
            else {
                rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2);
                if (bizPuan > rakipPuan)
                {
                    kazandik = true;
                    sonucKontrol();
                }
                else if (bizPuan > rakipPuan){
                    kazandik = false;
                    sonucKontrol();
                }
                else if (bizPuan.equals(rakipPuan)){
                    skor.setText("Berabere !" + rakipSkor + " siz : " + bizSkor);
                }
            }
        }

        else if (zorluk == "orta")
        {
            if (rakip3 == null  && rakipPuan < 15)
            {
                rakip3 = kartSecRakip();
                rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2 + " ve " + rakip3);
                if (rakipPuan > 21)
                {
                    kazandik = true;
                    sonucKontrol();
                }
                else if (rakipPuan < 15)
                {
                    rakip4 = kartSecRakip();
                    rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2 + " ve " + rakip3 + " ve " + rakip4);
                    if (rakipPuan > 21)
                    {
                        kazandik = true;
                        sonucKontrol();
                    }
                    else {
                        if (bizPuan > rakipPuan)
                        {
                            kazandik = true;
                            sonucKontrol();
                        }
                        else {
                            kazandik = false;
                            sonucKontrol();
                        }
                    }
                }
                else {
                    if (bizPuan > rakipPuan)
                    {
                        kazandik = true;
                        sonucKontrol();
                    }
                    else {
                        kazandik = false;
                        sonucKontrol();
                    }
                }

            }
            else {
                rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2);
                if (bizPuan > rakipPuan)
                {
                    kazandik = true;
                    sonucKontrol();
                }
                else if (bizPuan > rakipPuan){
                    kazandik = false;
                    sonucKontrol();
                }
                else if (bizPuan.equals(rakipPuan)){
                    skor.setText("Berabere !" + rakipSkor + " siz : " + bizSkor);
                }
            }
        }
        else if (zorluk == "zor"){
            if (rakip3 == null  && rakipPuan < 17)
            {
                rakip3 = kartSecRakip();
                rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2 + " ve " + rakip3);
                if (rakipPuan > 21)
                {
                    kazandik = true;
                    sonucKontrol();
                }
                else if (rakipPuan < 17)
                {
                    rakip4 = kartSecRakip();
                    rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2 + " ve " + rakip3 + " ve " + rakip4);
                    if (rakipPuan > 21)
                    {
                        kazandik = true;
                        sonucKontrol();
                    }
                    else {
                        if (bizPuan > rakipPuan)
                        {
                            kazandik = true;
                            sonucKontrol();
                        }
                        else {
                            kazandik = false;
                            sonucKontrol();
                        }
                    }
                }
                else {
                    if (bizPuan > rakipPuan)
                    {
                        kazandik = true;
                        sonucKontrol();
                    }
                    else {
                        kazandik = false;
                        sonucKontrol();
                    }
                }

            }
            else {
                rakipEli.setText("Rakip eli : " + rakip1 + " ve " + rakip2);
                if (bizPuan > rakipPuan)
                {
                    kazandik = true;
                    sonucKontrol();
                }
                else if (bizPuan > rakipPuan){
                    kazandik = false;
                    sonucKontrol();
                }
                else if (bizPuan.equals(rakipPuan)){
                    skor.setText("Berabere !" + rakipSkor + " siz : " + bizSkor);
                }
            }
        }
    }

    private void sonucKontrol() {
        if (kazandik)
        {
            bizSkor +=1;
            skor.setText("Kazandınız ! " + "Sizin Eliniz :" + bizPuan+ " Rakibinizin Eli : " + rakipPuan);
            skorYaz();
        }
        else {
            rakipSkor +=1;
            skor.setText("Kaybettiniz ! " + "Sizin Eliniz :" + bizPuan + " Rakibinizin Eli : " + rakipPuan);
            skorYaz();
        }

        verBt.setEnabled(false);
        durBt.setEnabled(false);
    }

    private void skorYaz() {
        durum.setText("Skor"+ "\n" + "Siz :" + bizSkor + "\n" + "Rakip :" + rakipSkor);
    }

    private void ayarlariSifirla() {
        biz1 = null;
        biz2 = null;
        biz3 = null;
        biz4 = null;
        rakip1 = null;
        rakip2 = null;
        rakip3 = null;
        rakip4 = null;
    }

    private String kartSecBiz(){
        String kart = null;
        Integer sayi = null;

        Random rnd = new Random();
        sayi = rnd.nextInt(13);
        kart = kartRenk[rnd.nextInt(4)];
        kart = kart + " " + kartTur[sayi];
        if (sayi > 9)
        {
            bizPuan = bizPuan + 10;
        }
        else if (sayi == 0)
        {
            if (bizPuan > 21)
            {
                bizPuan = bizPuan + 1;
            }
            else {
                bizPuan = bizPuan + 11 ;
            }
        }

        else {
            bizPuan = bizPuan + sayi + 1;
        }

        return kart;
    }

    private String kartSecRakip() {

        String kart = null;
        Integer sayi = null;

        Random rnd = new Random();
        sayi = rnd.nextInt(13);
        kart = kartRenk[rnd.nextInt(4)];
        kart = kart + " " + kartTur[sayi];
        if (sayi > 9)
        {
            rakipPuan = rakipPuan + 10;
        }
        else if (sayi == 0)
        {
            if (rakipPuan > 21)
            {
                rakipPuan = rakipPuan + 1;
            }
            else {
                rakipPuan = rakipPuan + 11 ;
            }
        }

        else {
            rakipPuan = rakipPuan + sayi + 1;
        }

        return kart;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        SharedPreferences sharedPref = null;
        sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            editor.putString("mySettings","kolay");
            editor.commit();
            zorluk = "kolay";
            zorlukTv1.setText("Zorluk : " + zorluk);
        }
        else if (id == R.id.action_settings2)
        {
            editor.putString("mySettings","orta");
            editor.commit();
            zorluk = "orta";
            zorlukTv1.setText("Zorluk : " + zorluk);
        }
        else if (id == R.id.action_settings3)
        {
            editor.putString("mySettings","zor");
            editor.commit();
            zorluk = "zor";
            zorlukTv1.setText("Zorluk : " + zorluk);
        }

        return super.onOptionsItemSelected(item);
    }
}
