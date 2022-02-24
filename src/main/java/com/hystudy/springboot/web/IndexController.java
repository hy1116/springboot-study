package com.hystudy.springboot.web;

import com.hystudy.springboot.config.auth.LoginUser;
import com.hystudy.springboot.config.auth.dto.SessionUser;
import com.hystudy.springboot.service.posts.PostsService;
import com.hystudy.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){ // 서버 템플릿 엔진에서 사용 할 수 있는 객체를 저장할 수 있습니다.
        model.addAttribute("posts",postsService.findAllDesc());

        if(user!=null){
            model.addAttribute("userName",user.getName());
            model.addAttribute("picture",user.getPicture());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto postsResponseDto = postsService.findById(id);
        model.addAttribute("post",postsResponseDto);
        return "posts-update";
    }
}
