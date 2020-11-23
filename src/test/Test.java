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
           File file=new File("C:\\项目\\zongdiao\\故障明细\\集团系统导出\\当日修宽带服务承诺标识清单.csv");
           DangRiXiuFuWuChengNuoReader dr=new DangRiXiuFuWuChengNuoReader();
           dr.inPath="C:\\项目\\zongdiao\\装机明细\\集团系统导出\\当日装宽带服务承诺标识清单.csv";
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
