package com.bigdata.spark.core;

/**
  * 参考地址： https://www.cnblogs.com/yaobolove/p/6213936.html
 * Created by 56104 on 2020/10/9.
 */
public class MyTree2 {

    public static Node init(){
        Node J = new Node (8,null,null);
        Node H = new Node(4, null, null);
        Node G = new Node(2, null, null);
        Node F = new Node(7, null, J);
        Node E = new Node(5, H, null);
        Node D = new Node(1, null, G);
        Node C = new Node(9, F, null);
        Node B = new Node(3, D, E);
        Node A = new Node(6, B, C);
        return A;   //返回根节点
    }

    public static  void printNode(Node node){
        System.out.print(node.getValue()+"  ");
    }

    //前序遍历
    void preOrderPe(Node root){
        printNode(root);
        if(root.getLeft()!=null){
            preOrderPe(root.getLeft());
        }
        if(root.getRight()!=null){
            inOrderPe(root.getRight());
        }
    }

//    中序遍历
    public void inOrderPe(Node root){
        if(root.getLeft()!=null){
            inOrderPe(root.getLeft());
        }
        printNode(root);
        if(root.getRight()!=null){
            inOrderPe(root.getRight());
        }
    }
//    后序遍历
    void postOrderPe(Node root){
        if(root.getLeft()!=null){
            inOrderPe(root.getLeft());
        }
        if(root.getRight()!=null){
            inOrderPe(root.getRight());
        }
        printNode(root);
    }

    public static void main(String[] args) {
        MyTree2 tree = new MyTree2();
        Node root = tree.init();
        System.out.println("前序遍历");
        tree.preOrderPe(root);
        System.out.println("中序遍历");
        tree.inOrderPe(root);
        System.out.println("后序遍历");
        tree.postOrderPe(root);


    }
}
 class Node{
    int value;
    Node left;
    Node right;

     public Node(int value, Node left, Node right) {
         this.value = value;
         this.left = left;
         this.right = right;
     }

     public int getValue() {
         return value;
     }

     public void setValue(int value) {
         this.value = value;
     }

     public Node getLeft() {
         return left;
     }

     public void setLeft(Node left) {
         this.left = left;
     }

     public Node getRight() {
         return right;
     }

     public void setRight(Node right) {
         this.right = right;
     }
 }
