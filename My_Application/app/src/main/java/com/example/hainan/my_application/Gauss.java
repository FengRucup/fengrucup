package com.example.hainan.my_application;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Gauss {
    public int M=10;
    public ArrayList<Float[]> midpoint = new ArrayList<>();
    public Map gaussmix(Float x[][],float c,float l,int m0,String v0){
        int k =0;
        int mid;
        double min;
        int minid;
        boolean breakflag= true;
        double temp;
        ArrayList<Float[]>[] lists = new ArrayList[M];
        HashMap<Integer,ArrayList<Float[]>> w = new HashMap<>();
        Random random = new Random();
        for(int i=0;i<M;i++) {
            mid = random.nextInt(x.length);
            while(midpoint.contains(mid)){
                mid = random.nextInt(x.length);
            }
            midpoint.add(x[mid]);
            lists[i] = new ArrayList<>();
            //lists[i].add(x[mid]);
            w.put(i,lists[i]);
        }
        while (true) {
            breakflag =true;
            for (int n = 0; n < x.length; n++) {
                min = Double.MAX_VALUE;
                minid = 0;
                for (int i = 0; i < M; i++) {
                    temp = 0;
                    for (int j = 0; j < x[0].length; j++) {
                        temp = (temp + Math.pow((midpoint.get(i)[j] - x[n][j]), 2));
                    }
                    if (temp < min) {
                        min = temp;
                        minid = i;
                    }
                }
                lists[minid].add(x[n]);
            }
            float temp2;
            for (int i = 0; i < M; i++) {
                for (int n = 0; n < x[0].length; n++) {
                    temp2 = 0;
                    for (int j = 0; j < lists[i].size(); j++) {
                        temp2 = lists[i].get(j)[n] + temp2;
                    }
                    Float[] f = midpoint.get(i);
                    if(f[n]!=temp2/lists[i].size()){
                        breakflag = false;
                    }
                    f[n] =  temp2 / lists[i].size();
                    midpoint.set(i, f);
                }
            }
            if(breakflag){
                break;
            }
        }
        return w;
    }
    public Map gaussmix(float x[][],float c,float l,float m0[][],float v0[][],float w0[]){
        return new Map() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object o) {
                return false;
            }

            @Override
            public boolean containsValue(Object o) {
                return false;
            }

            @Override
            public Object get(Object o) {
                return null;
            }

            @Override
            public Object put(Object o, Object o2) {
                return null;
            }

            @Override
            public Object remove(Object o) {
                return null;
            }

            @Override
            public void putAll(@NonNull Map map) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set keySet() {
                return null;
            }

            @NonNull
            @Override
            public Collection values() {
                return null;
            }

            @NonNull
            @Override
            public Set<Entry> entrySet() {
                return null;
            }
        };
    }
    public Map gaussmixp(float y[][],float m[][],float v[][],float w[]){
        return new Map() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object o) {
                return false;
            }

            @Override
            public boolean containsValue(Object o) {
                return false;
            }

            @Override
            public Object get(Object o) {
                return null;
            }

            @Override
            public Object put(Object o, Object o2) {
                return null;
            }

            @Override
            public Object remove(Object o) {
                return null;
            }

            @Override
            public void putAll(@NonNull Map map) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set keySet() {
                return null;
            }

            @NonNull
            @Override
            public Collection values() {
                return null;
            }

            @NonNull
            @Override
            public Set<Entry> entrySet() {
                return null;
            }
        };
    }
    private Map gaumix_train_diagonal(float xs[][],float m[][],float v[][],float w[],float c,float l,float sx0[],float mx0[]){
        return new Map() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object o) {
                return false;
            }

            @Override
            public boolean containsValue(Object o) {
                return false;
            }

            @Override
            public Object get(Object o) {
                return null;
            }

            @Override
            public Object put(Object o, Object o2) {
                return null;
            }

            @Override
            public Object remove(Object o) {
                return null;
            }

            @Override
            public void putAll(@NonNull Map map) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set keySet() {
                return null;
            }

            @NonNull
            @Override
            public Collection values() {
                return null;
            }

            @NonNull
            @Override
            public Set<Entry> entrySet() {
                return null;
            }
        };
    }
    public Map kmeans(float d[][],int k,String x0,int l){
        return new Map() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object o) {
                return false;
            }

            @Override
            public boolean containsValue(Object o) {
                return false;
            }

            @Override
            public Object get(Object o) {
                return null;
            }

            @Override
            public Object put(Object o, Object o2) {
                return null;
            }

            @Override
            public Object remove(Object o) {
                return null;
            }

            @Override
            public void putAll(@NonNull Map map) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set keySet() {
                return null;
            }

            @NonNull
            @Override
            public Collection values() {
                return null;
            }

            @NonNull
            @Override
            public Set<Entry> entrySet() {
                return null;
            }
        };
    }

}
