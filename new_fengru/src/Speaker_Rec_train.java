import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


/**
 * Created by 31722 on 2018/2/7.
 */

public class Speaker_Rec_train {
    private static ArrayList<SpeakerModel> speaker_models = new ArrayList<SpeakerModel>();
    public static void main(String[] args) throws IOException{
        String databaseOutputFile = "speaker_database.dat";
        int t=-1;//第n个人的第t段语音
        int N = 300;//训练集的人数
        double[][] datatemp = new double[N][12];
        File f = new File("E:\\data_thchs30\\train");
        File[] wavs = f.listFiles();
        Map<String,ArrayList<File>> files = new HashMap<>();
        for(int i=0;i<wavs.length;i++){
            String[] name_order = wavs[i].getName().split("_");
            if(files.keySet().contains(name_order[0])){
                files.get(name_order[0]).add(wavs[i]);
            }
            else{
                ArrayList<File> wav = new ArrayList<>();
                wav.add(wavs[i]);
                files.put(name_order[0],wav);
            }
        }
        for(String key:files.keySet()) {
            ArrayList<File> wavlist = files.get(key);
            t = -1;
            for (int m = 0; m < wavlist.size(); m++) {
                String[] strArray = wavlist.get(m).getName().split("\\.");
                int suffixIndex = strArray.length - 1;
                if (strArray[suffixIndex].equals("wav")) {
                    t++;
                    int k = 0;//k，i为临时变量
                    int i = 0;
                    wavRead test1 = new wavRead(wavlist.get(m).getAbsolutePath());//读取wav文件
                    if (!test1.isSuccess()) {
                        System.out.println("read wav failed!");
                        System.exit(1);
                    }
                    int[][] y = test1.getData();//获取语音数据
                    double[] z = new double[y.length * y[0].length];//转换为double
                    //System.out.println(test1.getNumChannels() + "\t" + y.length + "\t" + y[0].length);
                    for (i = 0; i < y.length; i++) {
                        for (int j = 0; j < y[i].length; j++) {
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
                    for (i = 1; i < y.length * y[0].length; ) {
                        i = i * 2;
                    }
                    int nFFTLength = i;//nFFTlength取决于输入的长度
                    if (oisZeroThCepstralCoefficientCalculated) {
                        //take in account the zero-th MFCC
                        nnumberOfMFCCParameters = nnumberOfMFCCParameters + 1;
                    } else {
                        nnumberOfMFCCParameters = nnumberOfMFCCParameters;
                    }

                    MFCC mfcc = new MFCC(nnumberOfMFCCParameters,
                            dsamplingFrequency,
                            nnumberofFilters,
                            nFFTLength,
                            oisLifteringEnabled,
                            nlifteringCoefficient,
                            oisZeroThCepstralCoefficientCalculated);
                    double[] dparameters = mfcc.getParameters(z);
                    for (i = 0; i < dparameters.length; i++) {
                        datatemp[t][i] = dparameters[i];
                    }

                }
            }

            System.out.println(t);
            if(t<31){
                continue;
            }
            double[][] data = new double[t + 1][];
            for (int i = 0; i < t + 1; i++) {
                data[i] = datatemp[i];
            }
            GMM gmm = new GMM(data, 32);
            gmm.fit();
            ArrayList<String> sentences = new ArrayList<String>();
            //sentences.add(fileName.substring(char_for_speaker));

            synchronized (speaker_models) {
                speaker_models.add(new SpeakerModel(gmm.get_means(), gmm.get_covars(), gmm.get_weights(), key, sentences));
            }
            System.out.println(key+"训练完成");
        }
        System.out.println("GENERAL STOP TIME = "+System.currentTimeMillis());
        FileOutputStream fout = new FileOutputStream(databaseOutputFile);
        ObjectOutputStream wy = new ObjectOutputStream(fout);
        wy.writeObject(speaker_models);
        wy.close();

//        all_lamda = g.gmm(data,t+1,M,12);
//
//        for(int j = 0;j<3;j++) {
//            File f_lamda = new File(j+"lamda_"+name+".txt");
//            PrintWriter pw = new PrintWriter(f_lamda);
//            for (int i = 0; i < M; i++) {
//                all_lamda[j][i].print(pw,18,16);
//                pw.println();
//                pw.flush();
//            }
//        }
        System.out.println("OK");
    }
}
