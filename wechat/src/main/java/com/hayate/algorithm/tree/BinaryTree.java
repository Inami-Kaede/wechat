package com.hayate.algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 *二叉树
 */
public class BinaryTree {
	
	private TreeNode root;
	
	public class TreeNode{
		
		private int index;
		private String data;
		private TreeNode leftChild;
		private TreeNode rightChild;
		
		public TreeNode(int index, String data) {
			super();
			this.index = index;
			this.data = data;
		}
	}
	
	public int getHeight(TreeNode node){
		if(node != null){
			int leftHeight = getHeight(node.leftChild);
			int rightHeight = getHeight(node.rightChild);
			return leftHeight < rightHeight ? rightHeight + 1 : leftHeight + 1;
		}
		return 0;
	}
	
	public int getSize(TreeNode node){
		if(node != null){
			return 1 + getSize(node.leftChild) + getSize(node.rightChild);
		}
		return 0;
	}
	
	/**
	 * 构建二叉树
	 *         A
	 *     B       C
	 * D      E        F
	 */
	public void createBinaryTree(){
		root = new TreeNode(1, "A");
		TreeNode nodeB = new TreeNode(2, "B");
		TreeNode nodeC = new TreeNode(3, "C");
		TreeNode nodeD = new TreeNode(4, "D");
		TreeNode nodeE = new TreeNode(5, "E");
		TreeNode nodeF = new TreeNode(6, "F");
		root.leftChild = nodeB;
		root.rightChild = nodeC;
		nodeB.leftChild = nodeD;
		nodeB.rightChild = nodeE;
		nodeC.rightChild = nodeF;
	}
	
	/**
	 * 根据前序反向生成二叉树
	 * ABD##E##C#F##
	 * @param data
	 * @return
	 */
	public TreeNode createBinaryTree(int size, List<String> data){
		if(!data.isEmpty()){
			int index = size - data.size();
			String stringCode = data.remove(0);
			
			if(stringCode.equals("#")){
				return null;
			}
			TreeNode temp = new TreeNode(index, stringCode);
			
			if(index == 0){
				root = temp;
			}
			temp.leftChild = createBinaryTree(size, data);
			temp.rightChild = createBinaryTree(size, data);
			return temp;
		}
		return null;
	}
	
	/**
	 * 前序遍历-无递归
	 * ABDECF
	 * @param node
	 */
	public void nonRecOrder(TreeNode node){
		if(node != null){
			Stack<TreeNode> stack = new Stack<TreeNode>();
			stack.push(node);
			while(!stack.isEmpty()){
				TreeNode temp = stack.pop();
				System.out.println(temp.data);
				if(temp.rightChild != null){
					stack.push(temp.rightChild);
				}
				if(temp.leftChild != null){
					stack.push(temp.leftChild);
				}
			}
		}
	}
	
	/**
	 * 前序遍历
	 * ABDECF
	 * @param node
	 */
	public void preOrder(TreeNode node){
		if(node != null){
			System.out.println(node.data);
			preOrder(node.leftChild);
			preOrder(node.rightChild);
		}
	}
	
	/**
	 * 中序遍历
	 * DBEACF
	 * @param node
	 */
	public void midOrder(TreeNode node){
		if(node != null){
			midOrder(node.leftChild);
			System.out.println(node.data);
			midOrder(node.rightChild);
		}
	}
	
	/**
	 * 后序遍历
	 * DEBFCA
	 * @param node
	 */
	public void postOrder(TreeNode node){
		if(node != null){
			postOrder(node.leftChild);
			postOrder(node.rightChild);
			System.out.println(node.data);
		}
	}
	
	public static void main(String[] args) {
		BinaryTree binaryTree = new BinaryTree();
/*		binaryTree.createBinaryTree();
		binaryTree.nonRecOrder(binaryTree.root);
		System.out.println("=============================================");
		binaryTree.preOrder(binaryTree.root);
		System.out.println("=============================================");
		binaryTree.midOrder(binaryTree.root);
		System.out.println("=============================================");
		binaryTree.postOrder(binaryTree.root);*/
		
		List<String> arrayList = new ArrayList<String>();
		String s = "ABD##E##C#F##";
		String[] stringArray = s.split("");
		for (String string : stringArray) {
			arrayList.add(string);
		}
		binaryTree.createBinaryTree(arrayList.size(), arrayList);
		binaryTree.preOrder(binaryTree.root);
		
	}
}
