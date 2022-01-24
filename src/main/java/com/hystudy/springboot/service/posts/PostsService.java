package com.hystudy.springboot.service.posts;

import com.hystudy.springboot.domain.posts.Posts;
import com.hystudy.springboot.domain.posts.PostsRepository;
import com.hystudy.springboot.web.dto.PostsResponseDto;
import com.hystudy.springboot.web.dto.PostsSaveRequestDto;
import com.hystudy.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    // Autowired 를 사용하지 않고 롬복의 RequiredArgsConstructor 가 final 로 선언된 모든 필드를 생성해줌 (autowired 와 동일한 효과를 봄)
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto dto){
        return postsRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));
        // update 시 데이터베이스으 update 쿼리 실행 안됨
        // jpa 의 영속성 컨텍스트 때문
        // 영속성 컨텍스트 : 엔티티를 영구저장하는 환경
        // 트랜잭션 안에서 데이터 베이스에서 데이터를 가져오면 영속성 컨텍스트가 유지된 상태
        // 이 값을 변경시 트랜잭션 끝나는 시점에 해당 테이블 변경분 반영 -> "더티체킹"
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습닉다. id = "+id));
        return new PostsResponseDto(entity);
    }
}
