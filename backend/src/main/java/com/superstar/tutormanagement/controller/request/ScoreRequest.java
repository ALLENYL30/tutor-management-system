package com.superstar.tutormanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 给老师打分
 */
public class ScoreRequest {
    private int bookId;
    private int score;
}
