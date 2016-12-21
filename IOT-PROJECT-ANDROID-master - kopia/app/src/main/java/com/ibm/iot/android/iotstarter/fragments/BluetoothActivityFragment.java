package com.ibm.iot.android.iotstarter.fragments;
import com.ibm.iot.android.iotstarter.activities.BluetoothActivity;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ibm.iot.android.iotstarter.R;
import com.ibm.iot.android.iotstarter.utils.WekaUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.filters.unsupervised.attribute.Remove;

import static android.R.attr.path;
import static android.R.id.text1;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BluetoothActivityFragment extends Fragment {
    Classifier classifier;
    private View view;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    int i = 0;
    //Object testData[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};

    public BluetoothActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        text1 = (TextView) view.findViewById(R.id.textView);
        text2 = (TextView) view.findViewById(R.id.textView2);
        text3 = (TextView) view.findViewById(R.id.textView3);
        text4 = (TextView) view.findViewById(R.id.textView4);
        text5 = (TextView) view.findViewById(R.id.textView5);
        text6 = (TextView) view.findViewById(R.id.textView6);
        text7 = (TextView) view.findViewById(R.id.textView7);
        return view;
    }
    public void setText1(String text) {text1.setText(text);}
    public void setText2(String text) {text2.setText(text);}
    public void setText3(String text) {
        text3.setText(text);
    }
    public void setText4(String text) {
        text4.setText(text);
    }
    public void setText5(String text) {
        text5.setText(text);
    }
    public void setText6(String text) {
        text6.setText(text);
    }
    public void setText7(String text) {
        text7.setText(text);
    }

  /*  public void CreateArray(String[] data) {
        for (int i = 1; i <= 180; i++){
            testData[i] = data[i];
    }
        testData[181] = "?";


    }
    */
    double[] testData = new double[181];
    Instance single_window;

    private FastVector getFormatDefaultInstanceAttribute() {
        List<Attribute> attributes1 = new ArrayList<>();

        attributes1.add(new Attribute("AccX1"));
        attributes1.add(new Attribute("AccY1"));
        attributes1.add(new Attribute("AccZ1"));
        attributes1.add(new Attribute("GyrX1"));
        attributes1.add(new Attribute("GyrY1"));
        attributes1.add(new Attribute("GyrZ1"));
        attributes1.add(new Attribute("AccX2"));
        attributes1.add(new Attribute("AccY2"));
        attributes1.add(new Attribute("AccZ2"));
        attributes1.add(new Attribute("GyrX2"));
        attributes1.add(new Attribute("GyrY2"));
        attributes1.add(new Attribute("GyrZ2"));
        attributes1.add(new Attribute("AccX3"));
        attributes1.add(new Attribute("AccY3"));
        attributes1.add(new Attribute("AccZ3"));
        attributes1.add(new Attribute("GyrX3"));
        attributes1.add(new Attribute("GyrY3"));
        attributes1.add(new Attribute("GyrZ3"));
        attributes1.add(new Attribute("AccX4"));
        attributes1.add(new Attribute("AccY4"));
        attributes1.add(new Attribute("AccZ4"));
        attributes1.add(new Attribute("GyrX4"));
        attributes1.add(new Attribute("GyrY4"));
        attributes1.add(new Attribute("GyrZ4"));
        attributes1.add(new Attribute("AccX5"));
        attributes1.add(new Attribute("AccY5"));
        attributes1.add(new Attribute("AccZ5"));
        attributes1.add(new Attribute("GyrX5"));
        attributes1.add(new Attribute("GyrY5"));
        attributes1.add(new Attribute("GyrZ5"));
        attributes1.add(new Attribute("AccX6"));
        attributes1.add(new Attribute("AccY6"));
        attributes1.add(new Attribute("AccZ6"));
        attributes1.add(new Attribute("GyrX6"));
        attributes1.add(new Attribute("GyrY6"));
        attributes1.add(new Attribute("GyrZ6"));
        attributes1.add(new Attribute("AccX7"));
        attributes1.add(new Attribute("AccY7"));
        attributes1.add(new Attribute("AccZ7"));
        attributes1.add(new Attribute("GyrX7"));
        attributes1.add(new Attribute("GyrY7"));
        attributes1.add(new Attribute("GyrZ7"));
        attributes1.add(new Attribute("AccX8"));
        attributes1.add(new Attribute("AccY8"));
        attributes1.add(new Attribute("AccZ8"));
        attributes1.add(new Attribute("GyrX8"));
        attributes1.add(new Attribute("GyrY8"));
        attributes1.add(new Attribute("GyrZ8"));
        attributes1.add(new Attribute("AccX9"));
        attributes1.add(new Attribute("AccY9"));
        attributes1.add(new Attribute("AccZ9"));
        attributes1.add(new Attribute("GyrX9"));
        attributes1.add(new Attribute("GyrY9"));
        attributes1.add(new Attribute("GyrZ9"));
        attributes1.add(new Attribute("AccX10"));
        attributes1.add(new Attribute("AccY10"));
        attributes1.add(new Attribute("AccZ10"));
        attributes1.add(new Attribute("GyrX10"));
        attributes1.add(new Attribute("GyrY10"));
        attributes1.add(new Attribute("GyrZ10"));
        attributes1.add(new Attribute("AccX11"));
        attributes1.add(new Attribute("AccY11"));
        attributes1.add(new Attribute("AccZ11"));
        attributes1.add(new Attribute("GyrX11"));
        attributes1.add(new Attribute("GyrY11"));
        attributes1.add(new Attribute("GyrZ11"));
        attributes1.add(new Attribute("AccX12"));
        attributes1.add(new Attribute("AccY12"));
        attributes1.add(new Attribute("AccZ12"));
        attributes1.add(new Attribute("GyrX12"));
        attributes1.add(new Attribute("GyrY12"));
        attributes1.add(new Attribute("GyrZ12"));
        attributes1.add(new Attribute("AccX13"));
        attributes1.add(new Attribute("AccY13"));
        attributes1.add(new Attribute("AccZ13"));
        attributes1.add(new Attribute("GyrX13"));
        attributes1.add(new Attribute("GyrY13"));
        attributes1.add(new Attribute("GyrZ13"));
        attributes1.add(new Attribute("AccX14"));
        attributes1.add(new Attribute("AccY14"));
        attributes1.add(new Attribute("AccZ14"));
        attributes1.add(new Attribute("GyrX14"));
        attributes1.add(new Attribute("GyrY14"));
        attributes1.add(new Attribute("GyrZ14"));
        attributes1.add(new Attribute("AccX15"));
        attributes1.add(new Attribute("AccY15"));
        attributes1.add(new Attribute("AccZ15"));
        attributes1.add(new Attribute("GyrX15"));
        attributes1.add(new Attribute("GyrY15"));
        attributes1.add(new Attribute("GyrZ15"));
        attributes1.add(new Attribute("AccX16"));
        attributes1.add(new Attribute("AccY16"));
        attributes1.add(new Attribute("AccZ16"));
        attributes1.add(new Attribute("GyrX16"));
        attributes1.add(new Attribute("GyrY16"));
        attributes1.add(new Attribute("GyrZ16"));
        attributes1.add(new Attribute("AccX17"));
        attributes1.add(new Attribute("AccY17"));
        attributes1.add(new Attribute("AccZ17"));
        attributes1.add(new Attribute("GyrX17"));
        attributes1.add(new Attribute("GyrY17"));
        attributes1.add(new Attribute("GyrZ17"));
        attributes1.add(new Attribute("AccX18"));
        attributes1.add(new Attribute("AccY18"));
        attributes1.add(new Attribute("AccZ18"));
        attributes1.add(new Attribute("GyrX18"));
        attributes1.add(new Attribute("GyrY18"));
        attributes1.add(new Attribute("GyrZ18"));
        attributes1.add(new Attribute("AccX19"));
        attributes1.add(new Attribute("AccY19"));
        attributes1.add(new Attribute("AccZ19"));
        attributes1.add(new Attribute("GyrX19"));
        attributes1.add(new Attribute("GyrY19"));
        attributes1.add(new Attribute("GyrZ19"));
        attributes1.add(new Attribute("AccX20"));
        attributes1.add(new Attribute("AccY20"));
        attributes1.add(new Attribute("AccZ20"));
        attributes1.add(new Attribute("GyrX20"));
        attributes1.add(new Attribute("GyrY20"));
        attributes1.add(new Attribute("GyrZ20"));
        attributes1.add(new Attribute("AccX21"));
        attributes1.add(new Attribute("AccY21"));
        attributes1.add(new Attribute("AccZ21"));
        attributes1.add(new Attribute("GyrX21"));
        attributes1.add(new Attribute("GyrY21"));
        attributes1.add(new Attribute("GyrZ21"));
        attributes1.add(new Attribute("AccX22"));
        attributes1.add(new Attribute("AccY22"));
        attributes1.add(new Attribute("AccZ22"));
        attributes1.add(new Attribute("GyrX22"));
        attributes1.add(new Attribute("GyrY22"));
        attributes1.add(new Attribute("GyrZ22"));
        attributes1.add(new Attribute("AccX23"));
        attributes1.add(new Attribute("AccY23"));
        attributes1.add(new Attribute("AccZ23"));
        attributes1.add(new Attribute("GyrX23"));
        attributes1.add(new Attribute("GyrY23"));
        attributes1.add(new Attribute("GyrZ23"));
        attributes1.add(new Attribute("AccX24"));
        attributes1.add(new Attribute("AccY24"));
        attributes1.add(new Attribute("AccZ24"));
        attributes1.add(new Attribute("GyrX24"));
        attributes1.add(new Attribute("GyrY24"));
        attributes1.add(new Attribute("GyrZ24"));
        attributes1.add(new Attribute("AccX25"));
        attributes1.add(new Attribute("AccY25"));
        attributes1.add(new Attribute("AccZ25"));
        attributes1.add(new Attribute("GyrX25"));
        attributes1.add(new Attribute("GyrY25"));
        attributes1.add(new Attribute("GyrZ25"));
        attributes1.add(new Attribute("AccX26"));
        attributes1.add(new Attribute("AccY26"));
        attributes1.add(new Attribute("AccZ26"));
        attributes1.add(new Attribute("GyrX26"));
        attributes1.add(new Attribute("GyrY26"));
        attributes1.add(new Attribute("GyrZ26"));
        attributes1.add(new Attribute("AccX27"));
        attributes1.add(new Attribute("AccY27"));
        attributes1.add(new Attribute("AccZ27"));
        attributes1.add(new Attribute("GyrX27"));
        attributes1.add(new Attribute("GyrY27"));
        attributes1.add(new Attribute("GyrZ27"));
        attributes1.add(new Attribute("AccX28"));
        attributes1.add(new Attribute("AccY28"));
        attributes1.add(new Attribute("AccZ28"));
        attributes1.add(new Attribute("GyrX28"));
        attributes1.add(new Attribute("GyrY28"));
        attributes1.add(new Attribute("GyrZ28"));
        attributes1.add(new Attribute("AccX29"));
        attributes1.add(new Attribute("AccY29"));
        attributes1.add(new Attribute("AccZ29"));
        attributes1.add(new Attribute("GyrX29"));
        attributes1.add(new Attribute("GyrY29"));
        attributes1.add(new Attribute("GyrZ29"));
        attributes1.add(new Attribute("AccX30"));
        attributes1.add(new Attribute("AccY30"));
        attributes1.add(new Attribute("AccZ30"));
        attributes1.add(new Attribute("GyrX30"));
        attributes1.add(new Attribute("GyrY30"));
        attributes1.add(new Attribute("GyrZ30"));

        FastVector fvClassVal = new FastVector(6);             // Sista label
        fvClassVal.addElement("up");
        fvClassVal.addElement("down");
        fvClassVal.addElement("left");
        fvClassVal.addElement("right");
        fvClassVal.addElement("tiltleft");
        fvClassVal.addElement("tiltright");
        attributes1.add(new Attribute("test", fvClassVal));   // Sista label

        //-----------Klistra in allt---------------------------------------------------
        FastVector fvWekaAttributes = new FastVector(attributes1.size()+1);
        for (Attribute attribute: attributes1) {
            fvWekaAttributes.addElement(attribute);
        }
        return fvWekaAttributes;

    }

    public void CreateArray(String[] data) throws Exception {

        for (int i = 1; i <= 180; i++){
            testData[i] = Double.valueOf(data[i]);
        }
        //testData[181] = "?";

        FastVector instanceAttributes = getFormatDefaultInstanceAttribute();
        Instances dataSet = new Instances("Relation: trainData",instanceAttributes,0);
        dataSet.setClassIndex(instanceAttributes.size()-1);

        single_window = new SparseInstance(dataSet.numAttributes());
        for (int i=0; i < testData.length; i++) {
            single_window.setValue((Attribute) instanceAttributes.elementAt(i),testData[i]);
        }
        single_window.setMissing(181);



        dataSet.add(single_window);

        single_window.setDataset(dataSet); // Completed instance

    //--------Train data-------------------------------------------------------------------------------
        Resources res = this.getResources();

        BufferedReader reader2 = new BufferedReader(
                new InputStreamReader(res.openRawResource(R.raw.traindatatest2)));
        Instances traindata = new Instances(reader2);
        reader2.close();
        traindata.setClassIndex(traindata.numAttributes() - 1);
        Instances train = traindata;
        Remove rm = new Remove();
        classifier = new J48();
        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(rm);
        fc.setClassifier(classifier);
        fc.buildClassifier(train);

            //double pred = fc.classifyInstance(test.instance(i));
            double pred2 = fc.classifyInstance(single_window);
            //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX predicted: " + test.classAttribute().value((int) pred)+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX predicted: " + single_window.classAttribute().value((int) pred2 )+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        if(pred2 == 0.0){
            setText1("Predicted UP");
        }
        if(pred2 == 1.0){
            setText1("Predicted DOWN");
        }
        if(pred2 == 2.0){
            setText1("Predicted LEFT");
        }
        if(pred2 == 3.0){
            setText1("Predicted RIGHT");
        }
        if(pred2 == 5.0){
            setText1("Predicted TILTLEFT");
        }
        if(pred2 == 4.0){
            setText1("Predicted TILTRIGHT");
        }

        //--------Train data-------------------------------------------------------------------------------

        String x2 = "";

   }


}