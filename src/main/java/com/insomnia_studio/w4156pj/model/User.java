package com.insomnia_studio.w4156pj.model;

import com.insomnia_studio.w4156pj.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private List<String> followers;
    private List<String> followedBy;
    private Date userCreatedTime;
    private Set<PostEntity> posts;
}
