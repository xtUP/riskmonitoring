package com.msyd.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msyd.business.domain.student;
import com.msyd.business.mapper.studentMapper;
import com.msyd.business.service.studentService;

/**
 * 参数配置 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class studentServiceImpl implements studentService
{
    @Autowired
    private studentMapper studentMapper;


    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<student> selectStudentList(student student)
    {
//    	return null;
        return null;
    }
}
