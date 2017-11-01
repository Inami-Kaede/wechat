package com.hayate.algorithm.tree;

/**
 *查找二叉树
 */
public class SearchBinaryTree {

	private TreeNode root;
	
	public class TreeNode {
		public TreeNode(int data) {
			super();
			this.data = data;
		}
		private int data;
		private TreeNode leftChild;
		private TreeNode rightChild;
		private TreeNode parent;
	}
	
	/**
	 * 添加元素
	 * @param input
	 * @return
	 */
	public TreeNode put(int input) {
		if(root == null) {
			root = new TreeNode(input);
			return root;
		}
		TreeNode temp = root;
		TreeNode parent = null;
		while(temp != null) {
			parent = temp;
			if(input == temp.data) {
				return null;
			}else if(input < temp.data ) {
				temp = temp.leftChild;
			}else {
				temp = temp.rightChild;
			}
		}
		TreeNode target = new TreeNode(input);
		if(input < parent.data) {
			parent.leftChild = target;
		}else {
			parent.rightChild = target;
		}
		target.parent = parent;
		return target;
	}
	
	/**
	 * 删除节点
	 * @param key
	 */
	public void deleteNode(int key){
		TreeNode node = searchNode(key);
		if(node != null){
			delete(node);
		}else{
			throw new RuntimeException("该节点未找到");
		}
	}
	
	private void delete(TreeNode node) {
		if(node != null){
			TreeNode parent = node.parent;
			//被删除节点无左右孩子
			if(node.leftChild == null && node.rightChild == null){
				if(parent.leftChild == node){
					parent.leftChild = null;
				}else{
					parent.rightChild = null;
				}
				return;
			}
			//被删除节点有左无右
			if(node.leftChild != null && node.rightChild == null){
				if(parent.leftChild == node){
					parent.leftChild = node.leftChild;
				}else{
					parent.rightChild = node.leftChild;
				}
				return;
			}
			//被删除节点有右无左
			if(node.leftChild == null && node.rightChild != null){
				if(parent.leftChild == node){
					parent.leftChild = node.rightChild;
				}else{
					parent.rightChild = node.rightChild;
				}
				return;
			}
			//既有左又有右
			TreeNode next = getNextNode(node);
			delete(next);
			node.data = next.data;
			return;
		}else{
			throw new RuntimeException("该节点未找到");
		}
	}

	/**
	 * 找该节点的后继节点
	 * @param node
	 * @return
	 */
	private TreeNode getNextNode(TreeNode node) {
		if(node != null){
			TreeNode target;
			if(node.rightChild != null){
				target = node.rightChild;
				while(target.leftChild != null){
					target = target.leftChild;
				}
				return target;
			}else {
				//向上找
				TreeNode parent = node.parent;
				while(parent != null && parent.rightChild == node){
					node = parent;
					parent = parent.parent;
				}
				return parent;
			}
			
		}
		return null;
	}

	/**
	 * 查找节点
	 * @param key
	 * @return
	 */
	private TreeNode searchNode(int key) {
		if(root != null){
			TreeNode temp = root;
			while(temp != null && temp.data != key){
				if(key > temp.data){
					temp = temp.rightChild;
				}else {
					temp = temp.leftChild;
				}
			}
			return temp;
		}
		return null;
	}

	/**
	 * 中序遍历
	 * @param node
	 */
	public static void midOrder(TreeNode node) {
		if(node != null) {
			midOrder(node.leftChild);
			System.out.println(node.data);
			midOrder(node.rightChild);
		}
	}
	
	public static void main(String[] args) {
		SearchBinaryTree searchBinaryTree = new SearchBinaryTree();
		int[] intArray = {25,33,44,55,11,4,5,7};
		for (int i : intArray) {
			searchBinaryTree.put(i);
		}
		midOrder(searchBinaryTree.root);
		searchBinaryTree.deleteNode(44);
		midOrder(searchBinaryTree.root);
	}
}
