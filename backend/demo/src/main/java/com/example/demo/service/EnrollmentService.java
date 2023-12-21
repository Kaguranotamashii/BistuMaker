package com.example.demo.service;

import com.example.demo.pojo.Enrollment;

import java.util.List;

public interface EnrollmentService {
    //查询全部报名
    List<Enrollment> selectAllEnrollment();

    //删除报名
    void deleteEnrollment(Integer id);

    //添加报名
    void addEnrollment(Enrollment enrollment);

    //修改报名
    void updateEnrollment(Enrollment enrollment);

    //


}
