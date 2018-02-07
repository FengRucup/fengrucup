package com.example.hainan.my_application;

import java.io.File;

import Jama.Matrix;

/**
 * Created by 31722 on 2018/2/7.
 */

public class Speaker_Rec {
    public static void main(String[] args) {
        int n=-1;//第n个人
        String name = "";//第n个人的姓名
        int t=-1;//第n个人的第t段语音
        int N = 100;//训练集的人数
        double[][][] temp_all_data = new double[N][][];
        File f = new File("E:\\data_thchs30\\train");
        File[] wavs = f.listFiles();
        for (int m=0;m<wavs.length;m++){
            String[] strArray = wavs[m].getName().split("\\.");
            int suffixIndex = strArray.length -1;
            if(strArray[suffixIndex].equals("wav")){
                String[] name_order = wavs[m].getName().split("_");
                if(name.equals(name_order[0])){
                    t++;
                }else{
                    n++;
                    t=0;
                    name = name_order[0];
                }
                int k = 0;//k，i为临时变量
                int i = 0;
                wavRead test1 = new wavRead(wavs[m].getAbsolutePath());//读取wav文件
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
                //simulate a frame of speech
                double[] x = new double[160];
                x[2]=10; x[4]=14;
                //double[] dparameters = mfcc.getParameters(x);
                double[] dparameters = mfcc.getParameters(z);
                for (i = 0; i < dparameters.length; i++) {
                    temp_all_data[n][t][i] = dparameters[i];
                }
            }
        }
        double[][][] all_data = new double[n+1][][];
        for(int i=0;i<n+1;i++){
            all_data[i] = temp_all_data[i];
        }
        train_predict tp = new train_predict();
        Matrix[][][] all_lamda = new Matrix[n+1][][];
        all_lamda = tp.gmm_train(all_data,n+1);
        tp.gmm_predict(all_data[0],all_lamda,N);
    }
}
