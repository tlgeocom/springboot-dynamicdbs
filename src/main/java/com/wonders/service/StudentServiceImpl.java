package com.wonders.service;

import com.wonders.dao.StudentMapper;
import com.wonders.dynamic.DataSource;
import com.wonders.dynamic.DbsConstant;
import com.wonders.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: yyalin
 * @CreateDate: 2023/7/17 15:45
 * @Version: V1.0
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentMapper studentMapper;
    //注解加在实现层才能生效
    @DataSource(value = DbsConstant.mysql_db_01)
    @Override
    public Student findStudentById(String id) {
        return studentMapper.selectById(id);
    }
}
