package com.hystudy.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // Lombok Annotation : 클래스 내 모든 필드의 getter 자동생성
@NoArgsConstructor // Lombok Annotation : 기본생성자 자동추가 (ex. public Posts(){})
@Entity // JPA Annotation : 테이블과 링크될 클레스임을 나타냄
public class Posts {
    @Id // 해당 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // GeneratedValue:  PK 의 생성규착, 스프링부트2.0 에서는 GenerationTyp0e.IDENTITY 옵션을 줘야 auto increment 가 된다,
    private Long id;

    @Column(length = 500, nullable = false)
    // Column : 테이블 컬럼을 나타내며 굳이 선언하지 않아도 된다.
    // 기본값 외로 추가로 변경이 필요한 옵션이 있으면 사용한다.
    private  String title;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String content;

    private String author;

    @Builder // 해당클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선성 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title,String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
    // Entity 에서는 setter 메소드를 생성하지 않는다.

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
