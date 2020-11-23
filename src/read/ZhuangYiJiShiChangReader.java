package read;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

import model.ZhuangYiJiShiChang;

public class ZhuangYiJiShiChangReader {
	public String inPath;
//	public String outPath="c:\\网点大全.csv";
	public ArrayList<String []> List = new ArrayList<String[]>();
//	private void CheckandCreateFile(){
//	
//		File file=new File(outPath);
//		try{
//			if(!file.exists()){
//				file.createNewFile();
//				System.out.println("文件不存在，新建成功！");
//			}
//			else{
//				System.out.println("文件存在！");
//			}
//		}catch( Exception e){
//			e.printStackTrace();
//		}
//	}
	public void ReadCSV() throws IOException {
        
	       CsvReader reader = new CsvReader(inPath,',', Charset.forName("gb2312"));
	       reader.readHeaders();
//	       System.out.println(reader.);
	       
	       while(reader.readRecord()) {
	           List.add(reader.getValues());
	       }
	       reader.close();
	       List<ZhuangYiJiShiChang> dList = new ArrayList<>();
	       for (int row = 0;row < List.size(); row++) {
	          int Length=List.get(row).length;
	          if(Length > 0){
	        	  ZhuangYiJiShiChang dr = new ZhuangYiJiShiChang();
	        	  dr.k_a=List.get(row)[0];
	        	  dr.k_b=List.get(row)[1];
	        	  dr.k_c=List.get(row)[2];
	        	  dr.k_d=List.get(row)[3];
	        	  dr.k_e=List.get(row)[4];
	        	  dr.k_f=List.get(row)[5];
	        	  dr.k_g=List.get(row)[6];
	        	  dr.k_h=List.get(row)[7];
	        	  dr.k_i=List.get(row)[8];
	        	  dr.k_j=List.get(row)[9];
//	        	  dr.k_k=List.get(row)[10];
	        	  dList.add(dr);
//	        	  for(int i=0;i<Length;i++){
//	        		  
//	        		  System.out.print(List.get(row)[i]+",");
//	        	  }//for
//	        	  
	          }//if
	          System.out.println("");
	       }//for
	       System.out.println(dList.get(0));
	       
	   }//class
//public void WriteCSV()	throws IOException{
//		CsvReader_DangRiXiu IO=new CsvReader_DangRiXiu();
//		IO.CheckandCreateFile();
//    CsvWriter wr = new CsvWriter(outPath,',', Charset.forName("gb2312"));
//    String[] header = { "Name","Province","City","Address","Tel","Website","Server_content","Jigou_cengji","Type","Parent_level1","Parent_level2","Branch_level" };
//    wr.writeRecord(header);
//    for(int i=0;i<List.size();i++)
//    {
//        String[] Data= List.get(i);
//        wr.writeRecord(Data);
//    }
//    wr.close();
//}
//public static void main( String args[]) throws IOException{
//	CsvReader_DangRiXiu IO=new CsvReader_DangRiXiu();
//	IO.ReadCSV();
//	IO.WriteCSV();
//}
}





