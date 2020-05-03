package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d("MainActivity", getHealth(Arrays.asList(5, -5, 4, -2, 3, 1)) + "");
        Log.d("MainActivity", getHealth(Arrays.asList(-5,4,-2,3,1,-1,-6,-1,0,5)) + "");

//        B b = new B();
//        C c=new C();
//        b=c;

    }

    class A{
        public void printName(){
            Log.d("==","A");
        }
    }


    class B extends A{
        public void printName(){
            Log.d("==","B");
        }
    }


    class C extends A{
        public void printName(){
            Log.d("==","C");
        }
    }

    int getHealth(List<Integer> dungeon) {
        int health = 0;
        int first = 0;
        if (dungeon == null || dungeon.size() ==0){
            return health;
        }
        for (int index = 0; index < dungeon.size(); index++) {
//            if (index == 0 && dungeon.get(index) >= 0) {
//                first = dungeon.get(index);
//            } else {
                health += dungeon.get(index);
//            }
            Log.d("MainActivity", " value : index = " + health);
        }
//        if(health < 0){
//            health = health * -1;
//        }
        return  health + 10;
    }

}
