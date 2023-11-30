package com.wonders.service;

import com.wonders.dynamic.DataSource;
import com.wonders.dynamic.DbsConstant;
import com.wonders.entity.Student;

/**
 * @Description: TODO
 * @Author: yyalin
 * @CreateDate: 2023/7/17 15:45
 * @Version: V1.0
 */
public interface StudentService {

    Student findStudentById(String id);
}
