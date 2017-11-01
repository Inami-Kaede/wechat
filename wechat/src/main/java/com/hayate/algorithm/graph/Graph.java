package com.hayate.algorithm.graph;

public class Graph {

	private int vertexSize; //顶点数量
	private int[] vertexs; //顶点数组
	private int[][] matrix; //矩阵
	private static final int MAX = 1000; //最大权值
	
	public Graph(int vertexSize) {
		super();
		this.vertexSize = vertexSize;
		matrix = new int [vertexSize][vertexSize];
		vertexs = new int [vertexSize];
		for(int i = 0; i < vertexSize; i++){
			vertexs[i] = i;
		}
	}
	
	public int[] getVertexs() {
		return vertexs;
	}
	
	public void setVertexs(int[] vertexs) {
		this.vertexs = vertexs;
	}
	
	public static void main(String[] args) {
	
		Graph graph = new Graph(5);
		int [] a1 = {0,MAX,MAX,MAX,6};
		int [] a2 = {9,0,3,MAX,MAX};
		int [] a3 = {2,MAX,0,5,MAX};
		int [] a4 = {MAX,MAX,MAX,0,1};
		int [] a5 = {MAX,MAX,MAX,MAX,0};
		graph.matrix[0] = a1;
		graph.matrix[1] = a2;
		graph.matrix[2] = a3;
		graph.matrix[3] = a4;
		graph.matrix[4] = a5;
		
		System.out.println(graph.getOutDegree(4));
		
		System.out.println("权值：" + graph.getWeitht(2, 3));
		
	}
	
	/**
	 * 获取出度
	 * @param index
	 * @return
	 */
	public int getOutDegree(int index){
		int degree = 0;
		for(int j = 0; j < matrix[index].length; j ++){
			int weight = matrix[index][j];
			if(weight != 0 && weight != MAX){
				degree++;
			}
		}
		return degree;
	}
	
	/**
	 * 获得权值
	 * @param v1
	 * @param v2
	 * @return
	 */
	public int getWeitht(int v1, int v2){
		int weight = matrix[v1][v2];
		return weight == 0 ? 0 : (weight == MAX ? -1 : weight);
	}
}
