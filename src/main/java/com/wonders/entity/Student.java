package com.wonders.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yyalin
 * @desc:此类的用途
 * @create 2021-10-28
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student")
public class Student extends Model {
    @TableId(value = "s_id",type = IdType.AUTO)
//    @TableField(value = "s_id")
    private Long studentId;
    private String studentName;
    private Integer age;
//    @TableId(value = "phone",type = IdType.ASSIGN_ID)
    private String phone;
//    @TableId(value = "addr",type = IdType.ASSIGN_UUID)
    private String addr;

    public Student(String studentName, Integer age, String phone, String addr) {
        this.studentName = studentName;
        this.age = age;
        this.phone = phone;
        this.addr = addr;
    }
}
