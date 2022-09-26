package com.example.myvocab.repo;

import com.example.myvocab.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Long> {

    @Query("select c from Comments c where c.id = ?1 and c.userTopic.topic.id = ?2")
    Optional<Comments> findByIdAndUserTopic_Topic_Id(Long id, Long topicId);

    List<Comments> findByUserTopic_Topic_Id(Long id);


}