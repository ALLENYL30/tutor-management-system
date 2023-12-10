package com.superstar.tutormanagement.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 预约信息实体
 */
public class BookingsDO {
    @TableField(exist = false)
    private int bookId;

    @TableField(exist = false)
    private String tutorName;

    @TableField(exist = false)
    private String studentName;

    private boolean isConfirm;
    private boolean isComplete;
    private boolean isDelete;
    private Timestamp startTime;
    private Timestamp createTime;
}
