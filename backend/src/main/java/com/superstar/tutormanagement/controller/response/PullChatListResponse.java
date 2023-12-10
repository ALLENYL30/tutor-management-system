package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.ChatListDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 获取聊天列表用户名
 */
public class PullChatListResponse {
    List<ChatListDO> list;
}
