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
@TableName(value = "blocks")
public class Tile extends Model {

    private Integer z;
    private Integer x;
    private Integer y;
    private Object tile;
    private Object hm;



}