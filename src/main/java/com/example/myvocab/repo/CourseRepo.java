package com.example.myvocab.repo;

import com.example.myvocab.dto.CourseInTableDto;
import com.example.myvocab.dto.CourseInfo;
import com.example.myvocab.model.Course;
import com.example.myvocab.model.CourseCategory;
import com.example.myvocab.model.CourseGroup;
import com.example.myvocab.model.enummodel.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> , JpaSpecificationExecutor<Course> {

    @Query("select c.id as id,c.title as title,cc.title as category,cg.title as group ,c.status as status ,l as levels" +
            " from Course as c" +
            " inner join CourseCategory cc on c.category.id=cc.id" +
            " inner join CourseGroup cg on c.group.id=cg.id" +
            " left join c.levels l")
    Page<CourseInTableDto> listAllCourseInTable(Pageable pageable);


    @Query("select c.id as id,c.title as title,cc.title as category,cg.title as group ,c.status as status ,l as levels" +
            " from Course as c" +
            " inner join CourseCategory cc on c.category.id=cc.id" +
            " inner join CourseGroup cg on c.group.id=cg.id" +
            " left join c.levels l" +
            " where concat(c.title,' ',cc.title, ' ', l.title,' ' ,cg.title) like %?1%")
    Page<CourseInTableDto> listCourseByKeyWord(String keyword, Pageable pageable);

//    @Query(value = "SELECT c FROM Course c left join c.levels l where concat(c.title,' ',c.category.title, ' ', l.title,' ' ,c.group.title) like %?1% group by c")
//    Page<Course> listCourseByKeyWord(String keyword, Pageable pageable);

    Optional<Course> findCourseById(Long id);

    @Query("select c.group from Course as c where c.category.id=?1 group by c.group")
    List<CourseGroup> getGroupByCourseCategory(Long categoryId);

    List<Course> findByCategory_IdAndGroup_IdAndStatus(Long categoryId, Long groupId, CourseStatus status);







}