package com.insomnia_studio.w4156pj.repository;

import com.insomnia_studio.w4156pj.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, UUID> {
    PostEntity findByPostId(UUID id);

    Integer deletePostEntityByPostId(UUID id);

    boolean existsByPostId(UUID id);


}
