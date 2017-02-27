
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.opencv.ml.SVM;

import java.io.IOException;
import java.util.Scanner;
import java.lang.ClassLoader;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class Face_SVM 
{

    static {

        System.load("/opt/share/OpenCV/java/libopencv_java320.so");
        //System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

    }

    public static void main( String[] args )
    {
        String trainingDir = "src/main/resources/faces/";

        Mat testImage = Imgcodecs.imread("src/main/resources/gentleman_2.pgm",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

        File rootFile = new File(trainingDir);

        List<String> resultsImages = new ArrayList<String>();

        File[] listOfFiles = rootFile.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File name is " + listOfFiles[i].getName());
                resultsImages.add(listOfFiles[i].getName());

            } else if (listOfFiles[i].isDirectory()) {

                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        LinkedList<String> trainFileNames  = new LinkedList<String>();
        LinkedList<String> trainLabels = new LinkedList<String>();

        for(String name : resultsImages) {
            System.out.println("\n" + name);

            String extensionRemoved = name.split("\\.")[0];
            System.out.println("WithoutExtension = " + extensionRemoved);

            String[] parts = extensionRemoved.split("_");
            System.out.println("label = " + parts[1]);

            trainFileNames.add(name);
            trainLabels.add(parts[1]);

        }

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                //System.out.println(name);

                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = rootFile.listFiles(imgFilter);

        int num_files = imageFiles.length;
        System.out.println("num_files => ");
        System.out.println(num_files);

        int img_area = 92*112;

        Mat trainingMat = new Mat(num_files,img_area,CvType.CV_32FC1);
        Mat labels = new Mat(num_files, 1, CvType.CV_32SC1);

        System.out.println("trainFiles = " + trainFileNames.size());
        for(int index=0; index < trainFileNames.size(); index++){

            String fileName = trainFileNames.get(index);
            Mat img_mat = Imgcodecs.imread(trainingDir+fileName, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
            	    
            int column_index = 0; 
            for (int i = 0; i<img_mat.rows(); i++) {
                for (int j = 0; j < img_mat.cols(); j++) {
                    trainingMat.put(index, column_index++, img_mat.get(i, j));
	    	}
	    }
	    System.out.println("label = " + trainLabels.get(index) + " <- " + trainFileNames.get(index));	
            labels.put( index, 0, Float.parseFloat(trainLabels.get(index) ));
            
        }

        SVM svm = SVM.create();
        svm.setKernel(0);

        svm.train(trainingMat, 0,labels);

        Mat predictData= new Mat();
        testImage.reshape(1,1).convertTo(predictData,CvType.CV_32F);
        
        float outlabel =  svm.predict(predictData);

        System.out.println("\n predicted_label = " + outlabel + "\n");

	if (outlabel == 2.0){
		System.out.println("... the predition is corret ...");
	} else {
		System.out.println("... the prediction is woring ...");
	}

    }
}
