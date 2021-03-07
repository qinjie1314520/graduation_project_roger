package com.common.pojo;

import lombok.Data;

/**
 * 二叉搜索树
 * @author roger
 * @since  2020/11/5 13:34
 */
@Data
public class BinarySearchAuthorityTree {
    private Node root;

    public boolean isEmpty(){
        return root == null;
    }

    public void insert(String key) {
        Node p= new Node(); //待插入的节点
        p.data=key;

        if(root==null) {
            root=p;
        }
        else {
            Node parent=null;
            Node current=root;
            while(true)
            {
                parent=current;
                //右子树
                if(key.compareTo(current.data) > 0) {
                    current=current.right;
                    if(current==null) {
                        parent.right=p;
                        return;
                    }
                }
                //左子树
                else if(key.compareTo(current.data) < 0) {
                    current=current.left;
                    if(current==null)
                    {
                        parent.left=p;
                        return;
                    }
                }
                //相等直接返回
                else {
                    return;
                }
            }
        }
    }
    public String find(String key) { // 从树中按照关键值查找元素
        if(key==null || key.length()==0){
            return null;
        }
        Node current = root;
        while (!current.data.equals(key)) {
            if (key.compareTo(current.data)>0)
                current = current.right;
            else
                current = current.left;
            if (current == null) return null;
        }
        return current.data;
    }
}
