package com.nowcoder.wenda.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
//InitializingBean接口为bean提供了初始化方法的方式，
// 它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
public class SensitiveService implements InitializingBean {

    private static final Logger logger= LoggerFactory.getLogger(SensitiveService.class);

    //字典树的根节点
    private TrieNode rootNode = new TrieNode();

    /**
     * 内部类，表示字典树（前缀树）节点
     */
    private class TrieNode{
        //是不是敏感词的结尾
        private boolean end = false;

        //当前节点下所有的子节点
        private Map<Character, TrieNode> subNodes = new HashMap<Character, TrieNode>();

        public void addSubNode(Character key, TrieNode node){
            subNodes.put(key,node);

        }

        TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeywordEnd(){return end;}

        void setKeywordEnd(boolean end){this.end = end;}

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStreamReader inputStreamReader = null;
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null){
                addWord(lineTxt.trim());    //去掉前后空格
            }

        }catch (Exception e){
            logger.error("读取敏感词失败" +  e.getMessage());
        }
        finally {
            inputStreamReader.close();
        }
    }

    /**
     * 向字典树中增加关键词
     */
    private void addWord(String lineTxt){
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineTxt.length(); i++) {
            Character character = lineTxt.charAt(i);
            // 过滤空格等非东亚字符
            if (isSymbol(character)) {
                continue;
            }
            TrieNode trieNode = tempNode.getSubNode(character);
            if (null == trieNode){
                trieNode = new TrieNode();
                tempNode.addSubNode(character, trieNode);
            }
            tempNode = trieNode;
            if(i == lineTxt.length() - 1){
                tempNode.setKeywordEnd(true);
            }
        }

    }

    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    /**
     * 敏感词过滤，三个指针
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }
        //敏感词用***替代
        String replacement = "***";
        //结果
        StringBuilder result = new StringBuilder();
        //三个指针
        TrieNode tempNode = rootNode; //字典树的节点指针
        int begin = 0; //回滚数
        int position = 0; //当前比较的位置
        while (position < text.length()){
            char c = text.charAt(position);

            // 空格等非东亚文字直接跳过，可以过滤掉如：赌△博、色  情
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                //确定当前子串不是敏感词
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd()){
                //发现敏感词，打码
                result.append(replacement);
                position += 1;
                begin = position;
                tempNode = rootNode;
            }else {
                //在敏感词字典树上但没有最后确定，比如敏感词是abc，现在position指向的是b，不确定后面还有没有c
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

//    /**
//     * 测试类
//     */
//    public static void main(String[] args){
//        SensitiveService s = new SensitiveService();
//        s.addWord("色情");
//        s.addWord("赌博");
//        System.out.println(s.filter("欢迎来赌博！"));
//    }

}
