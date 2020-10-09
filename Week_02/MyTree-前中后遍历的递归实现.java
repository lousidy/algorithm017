package com.bigdata.spark.core;

import java.util.Stack;

/**
 * 参考：https://www.cnblogs.com/yaobolove/p/6213936.html
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



    /*
    * 使用栈--前序
    * */
     void theFirstTraversal_Stack(Node root) {  //先序遍历
         Stack<Node> stack = new Stack<Node>();
         Node node = root;
         while (node!=null || stack.size()>0){
             if(node!=null){
                 printNode(node);
                 stack.push(node);
                 node = node.getLeft();
             }else{
                 node = stack.pop();
                 node = node.getRight();
             }
         }
    }
    /*
       * 使用栈--中序
       * */
    void theInTraversal_stack(Node root){
         Stack<Node> stack = new Stack<Node>();
         Node node = root;
         while (node!=null || stack.size()>0){
             if(node !=null){
                 stack.push(node);
                 node = node.getLeft();
             }else{
                 node = stack.pop();
                 printNode(node);
                 node = node.getRight();
             }
         }
    }
     /*
    * 使用栈--后序
    * */
     void thePostTraversal_stack(Node root){
         Stack<Node> stack = new Stack<Node>();
         Stack<Node> output = new Stack<Node>();
         Node node = root;
         while (node!=null || stack.size()>0){
             if(node!=null){
                 output.push(node);
                 stack.push(node);
                 node = node.getRight();
             }else {
                 node = stack.pop();
                 node = node.getLeft();
             }
         }
         System.out.println(output.size());
         while (output.size()>0){
             printNode(output.pop());
         }

     }

    public static void main(String[] args) {
        MyTree2 tree = new MyTree2();
        Node root = tree.init();
        System.out.println("方法2--栈");
        tree.theFirstTraversal_Stack(root);
        System.out.println("方法2--栈");
        tree.thePostTraversal_stack(root);


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
