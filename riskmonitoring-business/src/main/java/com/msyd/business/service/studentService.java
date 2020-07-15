package com.msyd.business.service;

import java.util.List;

import com.msyd.business.domain.student;

/**
 * 参数配置 服务层
 * 
 * @author ruoyi
 */
public interface studentService
{

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<student> selectStudentList(student config);
}
