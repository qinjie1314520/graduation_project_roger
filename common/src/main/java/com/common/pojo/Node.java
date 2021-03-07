package com.common.pojo;

import lombok.Data;

/**
 * 二叉搜索树节点
 * @author roger
 * @since  2020/11/5 13:34
 */
@Data
public class Node {
    /*
     * 数据域
     */
    String data;
    /*
     * 右子树
     */
    Node right;
    /*
     * 左子树
     */
    Node left;
}
