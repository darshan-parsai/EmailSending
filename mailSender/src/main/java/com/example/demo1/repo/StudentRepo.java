package com.example.demo1.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo1.entity.User;
@Repository

public interface StudentRepo extends JpaRepository<User, Integer> {
    @Query(value = "select email from student where id = ?1",nativeQuery = true)
	String getEmailId(Integer id);
    
    @Transactional
    @Modifying
    @Query(value = "update student set temp_pass =:tempPassword where id =:id",nativeQuery = true)
	void updateTempPass(@Param("tempPassword") String tempPassword,@Param("id") Integer id);

    @Query(value = "select temp_pass from student where id = ?1",nativeQuery = true)
	String getTempPass(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update student set password =:newPassword where id =:id",nativeQuery = true)
	void updatePermanentPass(String newPassword,Integer id);

}
