package com.hystudy.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// ibatis 나 mybatis 에서 DAO 라고 불리는 DB Layer 접근자, 인터페이스로 생성한다.
public interface PostsRepository extends JpaRepository<Posts,Long> {
    // JpaRepository :  상속시 기본적인 CRUD 메소드 자동생성
    // Entity 와 Entity Repository 는 같은 패키지 내에 위치해야 한다.

    @Query("SELECT p From Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
