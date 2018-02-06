package com.example.hainan.my_application;

/**
 * Created by linto on 2018/2/6.
 */

public class Chaextractor {
    wavRead test1 = new wavRead("");

    public static void main(String[] args) {

        int nnumberofFilters = 24;
        int nlifteringCoefficient = 22;
        boolean oisLifteringEnabled = true;
        boolean oisZeroThCepstralCoefficientCalculated = false;
        int nnumberOfMFCCParameters = 12; //without considering 0-th
        double dsamplingFrequency = 8000.0;
        int nFFTLength = 512;
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
        double[] dparameters = mfcc.getParameters(x);
        System.out.println("MFCC parameters:");
        for (int i = 0; i < dparameters.length; i++) {
            System.out.print(" " + dparameters[i]);

        }

    }
}
