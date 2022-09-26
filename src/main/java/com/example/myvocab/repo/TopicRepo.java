package com.example.myvocab.repo;

import com.example.myvocab.dto.TopicHaveCourseDto;
import com.example.myvocab.dto.TopicToCourseDto;
import com.example.myvocab.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

    List<Topic> findByCourse_IdOrderByIdAsc(Long id);

    @Query(value = "SELECT  t.id as id,t.title as title,c.id as courseId,c.title as courseTitle,cc.title as category,size(t.vocabs) as numberOfVocabs" +
            " FROM Topic t left outer join Course c on t.course.id=c.id" +
            " left join CourseCategory cc on t.course.category.id=cc.id")
    Page<TopicHaveCourseDto> findAllTopic(Pageable pageable);

    @Query(value = "SELECT  t.id as id,t.title as title,c.id as courseId,c.title as courseTitle,cc.title as category,size(t.vocabs)  as numberOfVocabs" +
            " FROM Topic t left outer join Course c on t.course.id=c.id" +
            " left join CourseCategory cc on t.course.category.id=cc.id" +
            " where concat(t.title,' ',c.title,' ',cc.title) like %?1% ")
    Page<TopicHaveCourseDto> listTopicsByKeyWord(String keyword, Pageable pageable);


    @Query("SELECT  t.id as id,t.title as title,c.id as courseId,c.title as courseTitle,cc.title as category, size(t.vocabs) as numberOfVocabs" +
            " FROM Topic t" +
            " left outer join Course c on t.course.id=c.id" +
            " left join CourseCategory cc on t.course.category.id=cc.id" +
            " where t.course is null ")
    List<TopicHaveCourseDto> getTopicsWithNoCourse();


    @Query("select t from Topic t where t.id = ?1")
    <T> Optional<T> findTopicById(Long id, Class<T> type);


}