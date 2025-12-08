    package org.example.free_new_magazine.service;

    import lombok.RequiredArgsConstructor;
    import org.example.free_new_magazine.dto.PostDTO;
    import org.example.free_new_magazine.entity.Category;
    import org.example.free_new_magazine.entity.Post;
    import org.example.free_new_magazine.entity.Role;
    import org.example.free_new_magazine.entity.User;
    import org.example.free_new_magazine.exception.ResourceNotFoundException;
    import org.springframework.security.access.AccessDeniedException;
    import org.example.free_new_magazine.mapper.PostMapper;
    import org.example.free_new_magazine.repository.CategoryRepository;
    import org.example.free_new_magazine.repository.PostRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class PostService {

        private final PostRepository postRepository;
        private final CategoryRepository categoryRepository;
        private final PostMapper postMapper;
        private final CurrentUserService currentUserService;
        private final  AuditLogService auditLogService;


        public List<PostDTO> getAllPosts() {
            return postRepository.findAll()
                    .stream()
                    .map(postMapper::toDTO)
                    .collect(Collectors.toList());
        }

        public PostDTO getPostById(Long id) {
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
            return postMapper.toDTO(post);
        }

        public Post getPostEntityById(Long id) {
            return  postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        }



        public PostDTO createPost(PostDTO postDTO) {
            User user = currentUserService.getCurrentUser();
            Post post = postMapper.toEntity(postDTO);
            post.setAuthor(user);

            if(postDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(postDTO.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + postDTO.getCategoryId()));
                post.setCategory(category);
            }

            Post savedPost = postRepository.save(post);
            auditLogService.log("CREATE_POST","/posts");
            return postMapper.toDTO(savedPost);
        }


        public PostDTO updatePost(Long id, PostDTO postDTO){
            User user = currentUserService.getCurrentUser();

            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

            if(!post.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You do not have permission to update this post");
            }

            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());

            if (postDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(postDTO.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + postDTO.getCategoryId()));
                post.setCategory(category);
            }

            Post updatedPost = postRepository.save(post);
            auditLogService.log("UPDATE_POST","/posts" + id);
            return postMapper.toDTO(updatedPost);
        }


        public void deletePost(Long id) {

            User user = currentUserService.getCurrentUser();

            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));

            if(!post.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You do not have permission to delete this post");
            }
            postRepository.delete(post);
            auditLogService.log("DELETE_POST","/posts" + id);
        }
    }
