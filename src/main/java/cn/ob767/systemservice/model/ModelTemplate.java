package cn.ob767.systemservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelTemplate implements Serializable {

    private static final long serialVersionUID = 2555755120225715096L;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
