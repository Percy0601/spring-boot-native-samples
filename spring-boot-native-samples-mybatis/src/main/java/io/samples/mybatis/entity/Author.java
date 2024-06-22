package io.samples.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@Data
@Accessors(chain = true)
@TableName(value = "author")
public class Author {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
}
