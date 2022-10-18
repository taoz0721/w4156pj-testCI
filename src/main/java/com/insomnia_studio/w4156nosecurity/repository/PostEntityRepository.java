package com.insomnia_studio.w4156nosecurity.repository;

import com.insomnia_studio.w4156nosecurity.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, String> {
    Optional<PostEntity> findByPostId(UUID id);

    Integer deletePostEntityByPostId(UUID id);
}
