package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import model.DangRiXiuFuWuChengNuo;
import read.DangRiXiuFuWuChengNuoReader;


public class Test {

	public static void main(String[] args) throws InvalidFormatException {
		// TODO Auto-generated method stub
           File file=new File("C:\\��Ŀ\\zongdiao\\������ϸ\\����ϵͳ����\\�����޿�������ŵ��ʶ�嵥.csv");
           DangRiXiuFuWuChengNuoReader dr=new DangRiXiuFuWuChengNuoReader();
           dr.inPath="C:\\��Ŀ\\zongdiao\\װ����ϸ\\����ϵͳ����\\����װ��������ŵ��ʶ�嵥.csv";
           List<DangRiXiuFuWuChengNuo> dList = new ArrayList<DangRiXiuFuWuChengNuo>();
           try {
			dr.ReadCSV();
			System.out.println(dList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
