package com.yuansaas.core.utils;

import cn.hutool.core.util.ObjectUtil;
import com.yuansaas.core.exception.ex.DataErrorCode;
import com.yuansaas.core.model.TreeNode;

import java.util.*;

/**
 * 树形结构工具类，如：菜单、部门等
 *
 * @author lxz
 */
public class TreeUtils {

    private TreeUtils(){}

    /**
     * 根据pid，构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long pid) {
        //pid不能为空
        if (ObjectUtil.isNull(pid)){
            throw DataErrorCode.DATA_VALIDATION_FAILED.buildException("pid不能为空");
        }

        List<T> treeList = new ArrayList<>();
        for(T treeNode : treeNodes) {
            if (pid.equals(treeNode.getPid())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
//        if (ObjectUtil.isEmpty(treeNodes)) {
//            return Collections.emptyList();
//        }
//
//        Map<Long, T> nodeMap = new HashMap<>(treeNodes.size());
//        List<T> rootList = new ArrayList<>();
//
//        // 1. 预构建 map
//        for (T node : treeNodes) {
//            node.setChildren(new ArrayList<>());
//            nodeMap.put(node.getId(), node);
//        }
//
//        // 2. 组装树
//        for (T node : treeNodes) {
//            T parent = nodeMap.get(node.getPid());
//
//            if (parent == null) {
//                rootList.add(node);
//            } else {
//                parent.getChildren().add(node);
//            }
//        }
//
//        // 3. 排序（可选）
//        rootList.sort(Comparator.comparing(TreeNode::getSort));
//        return rootList;
    }

    /**
     * 查找子节点
     */
    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
        for(T treeNode : treeNodes) {
            if(rootNode.getId().equals(treeNode.getPid())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();

        //list转map
        Map<Long, T> nodeMap = LinkedHashMap.newLinkedHashMap(treeNodes.size());
        for(T treeNode : treeNodes){
            nodeMap.put(treeNode.getId(), treeNode);
        }

        for(T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getPid());
            if(parent != null && !(node.getId().equals(parent.getId()))){
                parent.getChildren().add(node);
                continue;
            }

            result.add(node);
        }

        return result;
    }

}