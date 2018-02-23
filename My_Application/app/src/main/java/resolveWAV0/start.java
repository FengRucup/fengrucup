package resolveWAV0;

import SpeakerRecogTrain.SpeakerModel;
import com.sun.org.glassfish.gmbal.GmbalException;

import java.util.ArrayList;
import java.io.*;

public class start {
	private static ArrayList<SpeakerModel> speaker_models;
	public static void main(String args[]) {
		String speaker_database = "speaker_database2.dat";
		String old_winner = null;
		String new_winner = null;

		double score_temp;

		double score_final;

		ObjectInputStream we;

		try {

			we = new ObjectInputStream(new FileInputStream(speaker_database));

			speaker_models = (ArrayList<SpeakerModel>)we.readObject();

		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		catch (ClassNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		String filedir="/Users/hy/Downloads/A11_0.wav";
		//String filedir = "/Users/hy/冯如IConference/123.wav";
		
		 try
	      {
	         // Open the wav file specified as the first argument
	         WavFile wavFile = WavFile.openWavFile(new File(filedir));

	         // Display information about the wav file
	         wavFile.display();

	         // Get the number of audio channels in the wav file
	         long fs = wavFile.getSampleRate();
	         int numChannels = wavFile.getNumChannels();
	         int numframes = Integer.parseInt(String.valueOf(wavFile.getNumFrames()));
	         
	         double [][] normdata = new double[numChannels][numframes];
	         wavFile.readFrames(normdata, numframes);
	         
	         int [][] data = new int[numChannels][numframes];
	         for(int i=0;i<numChannels;i++) {
	        	 	for(int j=0;j<numframes;j++)
	        	 		data[i][j]=(int) Math.ceil(normdata[i][j]*32768);
	         }
				
	         
	         //去噪
	 		double f_d,f_size,n_f;
			int n;
			f_d = 0.025;  //size of block under consideration = f_d
			f_size = Math.floor(f_d*(wavFile.getSampleRate()));//no of samples in f_d is f_size
			n = numframes;
			n_f = Math.floor(n/f_size);// n_f is total number of blocks in given data
	        
			System.out.println(n_f);
			
			System.out.println("frames "+n);
			
			ArrayList<Integer> tempsi = new ArrayList<Integer>();			
			double hold = 0.05;
			
			for(int i=0;i<n_f;i++) {
				double max = 0;
				for(int j=0;j<f_size;j++) {
					if(Math.abs(normdata[0][(int) (i*f_size+j)])>max) {
						max=Math.abs(normdata[0][(int) (i*f_size+j)]);
					//	System.out.println(max);
						if(max>hold)
							break;
					}
				}
				if(max<=hold) {
					tempsi.add(i*(int)f_size);
					for(int j=0;j<f_size;j++) {
						normdata[0][(int) (i*f_size+j)]=0;
						data[0][(int) (i*f_size+j)]=0;
					}
				}
			}
			//
			System.out.println(tempsi.size());
			
			
//			for(int i=0;i<numframes;i++)
//				System.out.println(data[0][i]);

			
			MFCC mfcc = new MFCC(data[0], (int)fs);
			
			double[][] mfccs = mfcc.getMFCC();
			int len1 = mfccs.length;//4801
			int len2 = mfccs[0].length;//13		

			ArrayList<Integer> si = mfcc.getSi();

			ArrayList<Integer> sp = mfcc.getSp();
			
			int len0 = (int)Math.floor(numframes/len1);
			System.out.println(sp.size());
			System.out.println(si.size());
			
			int [][] segment = new int[2][sp.size()]; 
			
			WAVsegment cutwav = new WAVsegment(filedir,numframes);

			for(int i=0;i<sp.size();i++) {
				double[][] temp = new double[sp.get(i)-si.get(i)][];
				for(int j=0;j<sp.get(i)-si.get(i);j++){
					temp[j] = mfccs[si.get(i)+j];
				}
				score_temp = Double.NEGATIVE_INFINITY;

				score_final = Double.NEGATIVE_INFINITY;

				new_winner = null;
				int cut_left=0;
				int cut_right = 0;

				for(SpeakerModel model : speaker_models){

					score_temp = model.getScore(temp);

					if(score_temp > score_final){

						score_final = score_temp;

						new_winner = model.getName();

					}

				}
				if(new_winner.equals(old_winner)){
					continue;
				}else{

					if(i>0){
						cut_right = sp.get(i-1)*len0;
						String segname = makename("testseg",old_winner);

						System.out.println(segname);

						cutwav.cut(segname, cut_left, cut_right);



						System.out.print(cut_left+",");
						System.out.println(cut_right);
					}
					cut_left = si.get(i)*len0;
					old_winner = new_winner;
				}

				
				
			}
			
			
			
	         
	        wavFile.close();
	        System.out.println("Succeed!");
	      }
	      catch (Exception e)
	      {
	         System.err.println(e);
	      }
	}
	
	
	public static String makename(String dirname,String index) {
		String concat = dirname+"\\seg_"+index+".wav";
		
		return concat;
	}

}
