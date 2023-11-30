package com.wonders.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wonders.dynamic.DataSource;
import com.wonders.dynamic.DbsConstant;
import com.wonders.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper 与 启动类的@MapperScan({"com.example.demo.mapper"}) 二选一即可
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 功能描述:在mysql_db_01中查询数据
     * @MethodName: findStudentById
     * @MethodParam: [id]
     * @Return: com.wonders.entity.Student
     * @Author: yyalin
     * @CreateDate: 2023/7/17 14:20
     */
    @DataSource(value = DbsConstant.oracle_db_01)
    Student findStudentById(String id);
}
