package com.superstar.tutormanagement.controller.response;

import com.superstar.tutormanagement.repository.entity.ChatDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 历史聊天记录
 */
public class PullChatInfoResponse {
    List<ChatDO> conversationList;
}
