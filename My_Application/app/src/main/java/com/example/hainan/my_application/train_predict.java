package com.example.hainan.my_application;

import Jama.Matrix;

/**
 * Created by hainan on 2018/2/5.
 */

public class train_predict {
    public void gmm_predict(double[][] data,Matrix[][][] all_lamda,int N){
        gmm g = new gmm();
        int M= 10;
        int T = data[0].length;
        double[] score = new double[N];
        for(int i=0;i<N;i++) {
            Matrix[] weights;
            Matrix[] sigmaarray;
            Matrix[] uarray;
            weights = all_lamda[i][2];
            sigmaarray = all_lamda[i][1];
            uarray = all_lamda[i][0];
            score[i] = 0;
            for(int j = 0;j<data.length;j++) {
                double[][] xx=new double[1][T];
                xx[0]=data[i];
                Matrix x=new Matrix(xx);
                double sum = 0;
                for (int k = 0; k < M; k++) {
                    sum = sum + weights[k].get(0, 0) * g.P_ot_i_lamda(sigmaarray[k], x, uarray[k]);
                }
                score[i] = score[i] + Math.log(sum);
            }
        }
        double max = 0;
        int maxid = 0;
        for(int i = 0;i < N;i++){
            if(score[i] > max){
                max = score[i];
                maxid = i;
            }
        }
        System.out.println(maxid);
    }
    public Matrix[][][] gmm_train(double[][][] all_data,int N){
        gmm g = new gmm();
        //int N =0 ;//说话人数
        int M =0;//gmm模型阶数
        Matrix[][][] all_lamda = new Matrix[N][3][];
        for(int i=0;i<N;i++){
            all_lamda[i] = g.gmm(all_data[i], M, all_data[i][0].length);
        }
        return all_lamda;
    }
}
