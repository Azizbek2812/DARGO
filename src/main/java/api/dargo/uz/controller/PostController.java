package api.dargo.uz.controller;

import api.dargo.uz.dto.PostDTO;
import api.dargo.uz.util.SpringSecurityUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    @PostMapping("/create")
    public String create(@RequestBody PostDTO dto) {
        System.out.println(SpringSecurityUtil.getCurrentProfile());
        System.out.println(SpringSecurityUtil.getCurrentUserId());
        return "DONE";
    }
}
