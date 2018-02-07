package com.example.hainan.my_application;

import java.util.HashMap;
import java.util.Map;

import Jama.Matrix;
public class gmm {
    public double P_ot_i_lamda(Matrix sigma,Matrix x,Matrix u)
    {
        Matrix matrix=x.minus(u).times(sigma.inverse()).times(x.minus(u).transpose());
        double res=1.0/Math.sqrt(2*Math.PI*Math.abs(sigma.det()))*Math.exp(-0.5*matrix.get(0,0));
        return res;
    }
    public Matrix[] getParamsRR(int k,int hang,int lie)
    {
        Matrix[] matrixes=new Matrix[k];
        for(int i=0;i<k;i++)
        {
            double[][] rr=new double[hang][lie];
            for(int j=0;j<hang;j++)
            {
                for(int p=0;p<lie;p++)
                {
                    if(p==j)
                    rr[j][p]=1;
                }
            }
            matrixes[i]=new Matrix(rr);
        }
        return matrixes;
    }
//c表示类别数，
    public Matrix[][] gmm(double[][] data,int M,int T)
    {
        Matrix[][] ret;
        Gauss gauss = new Gauss();
        ret = gauss.initPara(data,0,0,0,"");
        Matrix[] uarray = ret[0];
        Matrix[] sigmaarray = ret[1];
        Matrix[] weights = ret[2];
        int count=0;
        double pre=Double.MIN_VALUE;
        double now=Double.MIN_VALUE;
        do
        {
            pre=now;
            count++;
            //E步骤
            //存储第n个数据分到第k类的概率
            double[][] P_i_ot_lamda=new double[data.length][M];
            double[] Nk=new double[M];
            for (int i=0;i<data.length;i++)
            {
                //j表示类别
                for(int j=0;j<M;j++)
                {
                    double[][] xx=new double[1][T];
                    xx[0]=data[i];
                    Matrix x=new Matrix(xx);
                    double sum=0;
                    for(int k=0;k<M;k++)
                    {
                        sum=sum+weights[k].get(0,0)*P_ot_i_lamda(sigmaarray[k],x,uarray[k]);
                    }
                    P_i_ot_lamda[i][j]=weights[j].get(0,0)*P_ot_i_lamda(sigmaarray[j],x,uarray[j])/sum;
                   // System.out.println(P_i_ot_lamda[i][j]);
                    if(P_i_ot_lamda[i][j]>1.0/M)
                    {
                        Nk[j]++;
                    }
                }
            }
            //M步骤 使用r重新计算参数
            for(int i=0;i<M;i++)
            {
                double[][] u=new double[1][T];
                Matrix uk=new Matrix(u);
                double[][] s=new double[T][T];
                Matrix sk=new Matrix(s);
                for(int j=0;j<data.length;j++)
                {
                    double[][] xx=new double[1][T];
                    xx[0]=data[j];
                    Matrix x=new Matrix(xx);
                    uk=uk.plus(x.times(P_i_ot_lamda[j][i]));
                    Matrix t=x.minus(uarray[i]).transpose().times(x.minus(uarray[i])).times(P_i_ot_lamda[j][i]);
                    sk=sk.plus(t);
                }
                uk=uk.times(1.0/(Nk[i]+1));
                sk=sk.times(1.0/(Nk[i]+1));
                uarray[i]=uk;
                sigmaarray[i]=sk;
                double[][] w = new double[1][1];
                w[0][0] =  Nk[i]/data.length;
                weights[i]=new Matrix(w);
            }
            //计算极大似然函数
            double sum=0;
            for(int i=0;i<data.length;i++)
            {
                double sum1=0;
                for(int j=0;j<M;j++)
                {
                    double[][] xx=new double[1][T];
                    xx[0]=data[j];
                    Matrix x=new Matrix(xx);
                    sum1=sum1+weights[j].get(0,0)*P_ot_i_lamda(sigmaarray[j],x,uarray[j]);
                }
                sum=sum+Math.log(sum1);
            }
            System.out.println("count="+count+"   "+sum);
            now=sum;
            sigmaarray[0].print(T,T);
            uarray[0].print(T,T);
            System.out.println(weights[0]);
            sigmaarray[1].print(T, T);
            uarray[1].print(T, T);
            System.out.println(weights[1]);
            System.out.println("now="+now+" pre="+pre+"  "+(now>pre));
        }while (Math.abs(now)!=Math.abs(pre));
        Matrix[][] gmmret = new Matrix[3][];
        gmmret[0] = uarray;
        gmmret[1] = sigmaarray;
        gmmret[2] = weights;
        return gmmret;
    }

}
