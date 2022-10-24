package com.insomnia_studio.w4156pj.repository;

import com.insomnia_studio.w4156pj.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Optional<CommentEntity> findByCommentId(UUID id);

    Integer deleteCommentEntityByCommentId(UUID id);
}
