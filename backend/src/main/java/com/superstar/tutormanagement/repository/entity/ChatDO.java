package com.superstar.tutormanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 聊天记录
 */
public class ChatDO {
    private LocalDateTime time;
    private String userName;
    private String message;
}
