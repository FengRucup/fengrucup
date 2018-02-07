package com.example.hainan.my_application;

/**
 * Created by lin on 2018/2/4.
 */

public class Chaextractor {

    public static void main(String[] args) {

        int k = 0;//k，i为临时变量
        int i = 0;
        wavRead test1 = new wavRead("D:\\fengru\\data\\data_thchs30\\data\\A2_0.wav");//读取wav文件
        if (!test1.isSuccess()) {
            System.out.println("read wav failed!");
            System.exit(1);
        }
        int[][] y = test1.getData();//获取语音数据
        double[] z = new double[y.length * y[0].length];//转换为double
        //System.out.println(test1.getNumChannels() + "\t" + y.length + "\t" + y[0].length);
        for (i = 0;i < y.length;i++){
            for (int j = 0;j < y[i].length;j++){
                //System.out.println(y[i][j]);
                z[k++] = y[i][j];
                //System.out.println(z[i][j]);
            }
        }
        int nnumberofFilters = 24;//获取MFCC特征值
        int nlifteringCoefficient = 22;
        boolean oisLifteringEnabled = true;
        boolean oisZeroThCepstralCoefficientCalculated = false;//true 代表13个参数，包括第0个
        int nnumberOfMFCCParameters = 12; //without considering 0-th，最终的MFCC结果
        double dsamplingFrequency = 8000.0;
        for (i = 1;i < y.length * y[0].length;){
            i = i * 2;
        }
        int nFFTLength = i;//nFFTlength取决于输入的长度
        if (oisZeroThCepstralCoefficientCalculated) {
            //take in account the zero-th MFCC
            nnumberOfMFCCParameters = nnumberOfMFCCParameters + 1;
        }
        else {
            nnumberOfMFCCParameters = nnumberOfMFCCParameters;
        }

        MFCC mfcc = new MFCC(nnumberOfMFCCParameters,
                dsamplingFrequency,
                nnumberofFilters,
                nFFTLength,
                oisLifteringEnabled,
                nlifteringCoefficient,
                oisZeroThCepstralCoefficientCalculated);

        System.out.println("start\t" + mfcc.toString() + "\tend");

        //simulate a frame of speech
        double[] x = new double[160];
        x[2]=10; x[4]=14;
        //double[] dparameters = mfcc.getParameters(x);
        double[] dparameters = mfcc.getParameters(z);
        System.out.println("MFCC parameters:");
        for (i = 0; i < dparameters.length; i++) {
            System.out.print(" " + dparameters[i]);

        }

    }
}
