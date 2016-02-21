package calculate_shortest_path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.Size2DSyntax;

import preprocess_data.DataProcess;

public class FloydInGraph {

	/**
	 * 图	邻接矩阵 		最短路径		弗洛伊德算法
	 */
	private static int INF=Integer.MAX_VALUE;
         //dist[i][j]=INF<==>no edges between i and j
	private int[][] dist;
         //the distance between i and j.At first,dist[i][j] is the weight of edge [i,j]  
	private int[][] path;  
	
	private List<Integer> result=new ArrayList<Integer>();
	
	public FloydInGraph(int size){
		this.dist = new int[size][size];
		this.path = new int[size][size];
	}
	
	public static void main(String[] args) throws IOException {
		//得到边的关系
		DataProcess dataProcess  = new DataProcess();
		List<String>  e = new ArrayList<String>();
		e = dataProcess.getEdge(-1,"./data/weight-strength.txt");
		//得到点
		Map<Integer, String> ni = new HashMap<Integer,String>();
		ni = dataProcess.getNode(e);
//		for(int id:ni.keySet()){
//			System.out.println(id+":"+ni.get(id));
//		}
		//得到矩阵
		List<List> matrix = new ArrayList<List>();
	    matrix= dataProcess.constructMatrix(-1, ni, e);
	    
	    FloydInGraph graph=new FloydInGraph(ni.size());
		int begin=0;
		int end=0;
		int shortP = 0;
		for(int i =0; i<ni.size();i++){
			for(int j=i+1; j<ni.size(); j++){
				begin = i;
				end = j;
				graph.findCheapestPath(begin,end,matrix);
//				List<Integer> list=graph.result;
//				System.out.println(begin+" to "+end+",the cheapest path is:");
//				System.out.println(list.toString());
				shortP = shortP + graph.dist[begin][end];
			}
		}
		System.out.println(shortP);
		System.out.println(shortP*1.0/(ni.size()*(ni.size()-1)));
		
	}

	public  void findCheapestPath(int begin,int end,List<List> matrix){
		floyd(matrix);
		result.add(begin);
		findPath(begin,end);
		result.add(end);
	}
	
	public void findPath(int i,int j){
		int k=path[i][j];
		if(k==-1)return;
		findPath(i,k);
		result.add(k);
		findPath(k,j);
	}
	public  void floyd(List<List> matrix){
		int size=matrix.size();
		//initialize dist and path
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				path[i][j]=-1;
				dist[i][j]=(int) matrix.get(i).get(j);
			}
		}
		for(int k=0;k<size;k++){
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					if(dist[i][k]!=INF&&
						dist[k][j]!=INF&&
						dist[i][k]+dist[k][j]<dist[i][j]){//dist[i][k]+dist[k][j]>dist[i][j]-->longestPath
						dist[i][j]=dist[i][k]+dist[k][j];
						path[i][j]=k;
					}
				}
			}
		}
		
	}
	
	
}

