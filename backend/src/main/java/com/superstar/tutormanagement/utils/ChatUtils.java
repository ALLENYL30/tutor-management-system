package com.superstar.tutormanagement.utils;

import com.alibaba.fastjson.JSON;
import com.superstar.tutormanagement.repository.entity.ChatDO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 关于聊天的工具
 */
public class ChatUtils {

    /**
     * 合并并排序聊天列表
     * @param list1
     * @param list2
     * @return
     */
    public static List<ChatDO> mergeChatList(List<ChatDO> list1, List<ChatDO> list2) {
        List<ChatDO> mergedList = new ArrayList<>();
        mergedList.addAll(list1);
        mergedList.addAll(list2);

        // 使用 Comparator 对 time 进行排序
        Collections.sort(mergedList, Comparator.comparing(ChatDO::getTime));
        return mergedList;
    }

    /**
     * 创建json字符串
     * @param sender
     * @param message
     * @return
     */
    public static String createDefaultJsonStr(String sender, String message) {
        ChatDO chatDO = createChatDO(sender, message);
        List<ChatDO> list = createChatDOList(chatDO);
        return JSON.toJSONString(list);
    }

    /**
     * 创建默认的信息
     * @return
     * @param userName
     */
    public static String createDefaultMsg(String userName) {
        return "hello, " + userName;
    }

    /**
     * 创建ChatDO
     * @param sender
     * @param message
     * @return
     */
    public static ChatDO createChatDO(String sender, String message) {
        ChatDO chatDO = new ChatDO();
        chatDO.setMessage(message);
        chatDO.setUserName(sender);
        chatDO.setTime(TimeUtils.getNow());
        return chatDO;
    }

    /**
     * 创建ChatDO列表
     * @param chatDO
     * @return
     */
    public static List<ChatDO> createChatDOList(ChatDO chatDO) {
        List<ChatDO> list = new ArrayList<>();
        list.add(chatDO);
        return list;
    }

    /**
     * 转string
     * @param list
     * @return
     */
    public static String List2JsonStr(List<ChatDO> list) {
        return JSON.toJSONString(list);
    }
}
