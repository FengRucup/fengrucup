package com.example.hainan.my_application;



import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Jama.Matrix;

public class Gauss {
    public int M=10;
    public double[][] midpoint;
    double[][][] lists;
    public Matrix[][] initPara(double x[][],float c,float l,int m0,String v0){
        int k =0;
        int mid;
        double min;
        int minid;
        boolean breakflag= true;
        double temp;

        Random random = new Random();
        for(int i=0;i<M;i++) {
            midpoint[i] = x[i];
        }
        while (true) {
            breakflag =true;
            lists = new double[M][][];
            int tcount =0 ;
            for (int n = 0; n < x.length; n++) {
                min = Double.MAX_VALUE;
                minid = 0;
                for (int i = 0; i < M; i++) {
                    temp = 0;
                    for (int j = 0; j < x[0].length; j++) {
                        temp = (temp + Math.pow((midpoint[i][j] - x[n][j]), 2));
                    }
                    if (temp < min) {
                        min = temp;
                        minid = i;
                    }
                }
                lists[minid][tcount++] = x[n];
            }
            double temp2;
            for (int i = 0; i < M; i++) {
                for (int n = 0; n < x[0].length; n++) {
                    temp2 = 0;
                    for (int j = 0; j < lists[i].length; j++) {
                        temp2 = lists[i][j][n] + temp2;
                    }
                    double[] f = midpoint[i];
                    if(f[n]!=temp2/lists[i].length){
                        breakflag = false;
                    }
                    f[n] =  temp2 / lists[i].length;
                    midpoint[i] = f;
                }
            }
            if(breakflag){
                break;
            }
        }
        Matrix[] uarray = new Matrix[M];
        Matrix[] simaarray = new Matrix[M];
        Matrix[] weights = new Matrix[M];
        Matrix[][] ret = new Matrix[3][];

        for(int i=0;i<M;i++){
            double[][] u = new double[1][x[0].length];
            double[][] sigma = new double[x[0].length][x[0].length];
            double[][] weight = new double[1][1];
            for(int j = 0;j<x[0].length;j++){
                u[0][j]=0;
                for(int n=0;n<lists[i].length;n++){
                    u[0][j] = u[0][j] + lists[i][n][j];
                }
                u[0][j] = u[0][j]/lists[i].length;
                sigma[j][j] = 0;
                for(int n=0;n<lists[i].length;n++){
                    sigma[j][j] = sigma[j][j] + (lists[i][n][j] - u[0][j])*(lists[i][n][j] - u[0][j]);
                }
                sigma[j][j] = sigma[j][j]/lists[i].length;
            }
            uarray[i] = new Matrix(u);
            simaarray[i] = new Matrix(sigma);
            weight[0][0] = lists[i].length/x.length;
            weights[i] = new Matrix(weight);
        }
        ret[0] = uarray;
        ret[1] = simaarray;
        ret[2] = weights;
        return ret;
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
