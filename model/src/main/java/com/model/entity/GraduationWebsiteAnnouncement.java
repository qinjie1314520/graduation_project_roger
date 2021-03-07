package com.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * @author PC
 */
@TableName(value = "graduation_website_announcement")
public class GraduationWebsiteAnnouncement {
    public final static int STATUS_VALUE_SHOW = 1;
    public final static int STATUS_VALUE_NOT_SHOW = 0;
    @TableId(type = AUTO)
    private Long id;
    private Integer status;
    private Long createTime;
    private String content;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
