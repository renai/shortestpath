package preprocess_data;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DataProcess {
	private static int INF=Integer.MAX_VALUE;
	List<String> edge = new ArrayList<String>();
	Map<Integer,String> nodeIndex = new HashMap<Integer,String>();
	List<List> matrix = new ArrayList<List>();
	
	//k为-1时代表读取全部数据，k为0时代表读取0条数据
	public List<String> getEdge(int k,String path) throws IOException{
		File nodeFile = new File(path); //边的文件路径
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(nodeFile));
			BufferedReader readnode = new BufferedReader(reader);	
			
			String sline = readnode.readLine();
			if(k==-1){
				 while (sline!=null) {
					  String[] edgeRelation = sline.replace("\'", "").replace("(", "").replace(")", "").split(":");
					  edge.add(edgeRelation[0]);	
					  sline = readnode.readLine();
				}
			}else{
			   for (int i=0; i<k; i++) {
				  String[] edgeRelation = sline.replace("\'", "").replace("(", "").replace(")", "").split(":");
				  edge.add(edgeRelation[0]);	
				  sline = readnode.readLine();
			}
			}
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("请检查文件是否存在");
		}
		return edge;
	}
	
	public Map<Integer,String> getNode(List<String> edges) throws IOException{
	   HashSet nodeSet = new HashSet();
	   for(int i = 0;i<edges.size();i++){
			String[] nodes = edges.get(i).split(", ");
			nodeSet.add(nodes[0]);
			nodeSet.add(nodes[1]);
	    }	
	   
	    Iterator iterator = nodeSet.iterator();
	    int i=0;
	   while (iterator.hasNext()) {  

		      //遍历Set集合

		   Object object = iterator.next();
		   nodeIndex.put(i, object.toString());
		   i++;

		    }
		return nodeIndex;
	}
	
	public List<List> constructMatrix(int k, Map<Integer, String> nodeIndex, List<String> edgeRelation) throws IOException{
	int Size = nodeIndex.size();
	for(int i=0;i<Size;i++){
		List<Integer> matrixLine = new ArrayList<Integer>();
		for(int j=0;j<Size;j++){
		String edge_relation = nodeIndex.get(i)+", "+nodeIndex.get(j);
		if(i==j){
			matrixLine.add(0);
			//System.out.print("0");
		}else{
		if(edgeRelation.contains(edge_relation)){
			matrixLine.add(1);
		//	System.out.print("1");
		}else {
			matrixLine.add(INF);
			//System.out.print("w");
		}
		}
		}
		matrix.add(matrixLine);
		//System.out.println(matrix.get(i).size());
	}
	return matrix;
	}
	
	public static void main(String args[]) throws IOException {
		DataProcess dataProcess  = new DataProcess();
		List<String>  e = new ArrayList<String>();
		e = dataProcess.getEdge(-1,"./data/test.txt");
//		for (int i = 0; i < e.size(); i++) {
//			System.out.println(e.get(i));
//		}
		System.out.println("number of edges:"+e.size());
		
	    Map<Integer,String> ni = new HashMap<Integer,String>();
		ni =  dataProcess.getNode(e);
//		for(int i:ni.keySet()){
//			System.out.println(ni.get(i));
//		}
		System.out.println("number of nodes:"+ni.size());
		
		List<List> ma = new ArrayList<List>();
		ma = dataProcess.constructMatrix(-1, ni, e);
		
		for(int i=0;i<ma.size();i++){
			for(int j=0;j<ma.size();j++){
				System.out.println(ma.get(i).get(j));
			}
		}

}
}

