    package org.example.free_new_magazine.controller;

    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.example.free_new_magazine.dto.PostCreateDTO;
    import org.example.free_new_magazine.dto.PostResponseDTO;
    import org.example.free_new_magazine.service.PostService;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.List;

    @RestController
    @RequestMapping("/api/posts")
    @RequiredArgsConstructor
    public class PostController {

        private final PostService postService;


        @GetMapping
        public ResponseEntity<List<PostResponseDTO>> getAllPosts(Pageable pageable) {
            return ResponseEntity.ok(postService.getAllPosts(pageable));
        }

        @GetMapping("/{id}")
        public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
            return ResponseEntity.ok(postService.getPostById(id));
        }

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<PostResponseDTO> createPost(
            @RequestPart("data") @Valid PostCreateDTO postCreateDTO,
            @RequestPart(value = "file",required = false) MultipartFile file) {
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postCreateDTO, file));
        }


        @GetMapping("/me")
        public ResponseEntity<List<PostResponseDTO>> myPosts(){
            return ResponseEntity.ok(postService.getMyPosts());
        }

        @GetMapping("/public")
        public Page<PostResponseDTO> publicList(Pageable pageable){
            return postService.getPublicPosts(pageable);
        }

        @GetMapping("/public{id}")
        public PostResponseDTO publicById(@PathVariable Long id){
            return postService.getPostById(id);
        }



        @PutMapping("/{id}")
        public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id,
                                                          @Valid @RequestBody PostCreateDTO postCreateDTO) {
            return ResponseEntity.ok(postService.updatePost(id, postCreateDTO));
        }



        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePost(@PathVariable Long id) {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        }
    }
