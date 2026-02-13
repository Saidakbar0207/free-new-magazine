    package org.example.free_new_magazine.service;

    import lombok.RequiredArgsConstructor;
    import org.example.free_new_magazine.dto.PostCreateDTO;
    import org.example.free_new_magazine.dto.PostResponseDTO;
    import org.example.free_new_magazine.dto.TelegramPostDTO;
    import org.example.free_new_magazine.entity.*;
    import org.example.free_new_magazine.exception.ForbiddenException;
    import org.example.free_new_magazine.exception.NotFoundException;
    import org.example.free_new_magazine.repository.PostImageRepository;
    import org.example.free_new_magazine.repository.PostVideoRepository;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.security.access.AccessDeniedException;
    import org.example.free_new_magazine.mapper.PostMapper;
    import org.example.free_new_magazine.repository.CategoryRepository;
    import org.example.free_new_magazine.repository.PostRepository;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.List;


    @Service
    @RequiredArgsConstructor
    public class PostService {


        private final PostRepository postRepository;
        private final CategoryRepository categoryRepository;
        private final  AuditLogService auditLogService;
        private final PostImageRepository postImageRepository;
        private final PostVideoRepository postVideoRepository;
        private final PostMapper postMapper;
        private final CurrentUserService currentUserService;
        private final StorageService storageService;

        @Value("${telegram.bot.url}")
        private String botUrl;



        private void checkPostOwnerOrAdmin(Post post,User me){
            boolean owner = post.getAuthor().getId().equals(me.getId());
            boolean admin = me.getRole() == Role.ROLE_ADMIN;
            if(!owner && !admin) throw new ForbiddenException("You do not have permission to access this post");
        }


        public List<PostResponseDTO> getAllPosts(Pageable pageable) {
            return postRepository
                    .findAll(pageable)
                    .stream()
                    .map(postMapper::toResponseDTO)
                    .toList();
        }

        public PostResponseDTO getPostById(Long id) {
          Post post = getPostEntityById(id);
           return postMapper.toResponseDTO(post);
        }

        public Post getPostEntityById(Long id) {
            return  postRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));
        }

        public PostResponseDTO createPost(PostCreateDTO postCreateDTO, MultipartFile file) {
            User user = currentUserService.getCurrentUser();
            Post post = postMapper.toEntity(postCreateDTO);
            post.setAuthor(user);
            if(postCreateDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(postCreateDTO.getCategoryId())
                        .orElseThrow(() -> new NotFoundException("Category not found with id: " + postCreateDTO.getCategoryId()));
                post.setCategory(category);
            }
            if(post.getViews() == null) post.setViews(0L);
            if(post.getFeatured() == null) post.setFeatured(false);
            if(post.getStatus() == null) post.setStatus(PostStatus.DRAFT);
            if(post.getIsDeleted() == null) post.setIsDeleted(false);

            Post saved = postRepository.save(post);

            auditLogService.log("CREATE_POST","/api/posts");


            if (file != null && !file.isEmpty()) {
                String ct = file.getContentType();

                if (ct != null && ct.startsWith("image/")) {
                    String url = storageService.save(file, "posts/images/" + saved.getId());
                    postImageRepository.save(PostImage.builder().imageUrl(url).post(saved).build());

                    if (saved.getCoverUrl() == null) {
                        saved.setCoverUrl(url);
                        saved.setCoverType(CoverType.IMAGE);
                        saved = postRepository.save(saved);
                    }


                } else if (ct != null && ct.startsWith("video/")) {
                    String url = storageService.save(file, "posts/videos/" + saved.getId());
                    postVideoRepository.save(PostVideo.builder().videoUrl(url).post(saved).build());

                    if (saved.getCoverUrl() == null) {
                        saved.setCoverUrl(url);
                        saved.setCoverType(CoverType.VIDEO);
                        saved = postRepository.save(saved);
                    }

                } else {
                    throw new IllegalArgumentException("File type not supported: " + ct);
                }
            }
            return postMapper.toResponseDTO(saved);
        }

        public PostResponseDTO updatePost(Long id, PostCreateDTO postCreateDTO){
            User user = currentUserService.getCurrentUser();

            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));

            if(!post.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You do not have permission to update this post");
            }

            post.setTitle(postCreateDTO.getTitle());
            post.setContent(postCreateDTO.getContent());

            if (postCreateDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(postCreateDTO.getCategoryId())
                                .orElseThrow(() -> new NotFoundException("Category not found with id: " + postCreateDTO.getCategoryId()));
                post.setCategory(category);
            }

            Post updatedPost = postRepository.save(post);
            auditLogService.log("UPDATE_POST","/posts/" + id);
            return postMapper.toResponseDTO(updatedPost);
        }

        public List<PostResponseDTO> getMyPosts() {
            User user = currentUserService.getCurrentUser();
            return postRepository.findByAuthor_Id(user.getId())
                    .stream().map(postMapper::toResponseDTO)
                    .toList();
        }

        public Page<PostResponseDTO> getPublicPosts(Pageable pageable) {
            return postRepository.findByStatusAndIsDeletedFalse(PostStatus.PUBLISHED, pageable)
                    .map(postMapper::toResponseDTO);
        }

        public PostResponseDTO getPublicPostById(Long id){
            Post post = postRepository.findByIdAndStatusAndIsDeletedFalse(id, PostStatus.PUBLISHED)
                    .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));
            return postMapper.toResponseDTO(post);
        }



        @Transactional
        public void deletePost(Long id) {

            User user = currentUserService.getCurrentUser();

            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));

            if(!post.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("You do not have permission to delete this post");
            }
            post.setIsDeleted(true);
            auditLogService.log("DELETE_POST","/posts/" + id);
            postRepository.save(post);
        }

        public List<TelegramPostDTO> getLatestForTelegram(int limit) {

            return postRepository.findByStatusAndIsDeletedFalse(
                            PostStatus.PUBLISHED,
                            PageRequest.of(0,limit,Sort.by("createAt").descending()))
                    .getContent()
                    .stream()
                    .map(post ->{
                        String content = post.getContent() == null ? "" : post.getContent();
                        String preview = content.length() > 300 ? content.substring(0, 300) + "..." : content;
                        return  new TelegramPostDTO(
                            post.getId(),
                            post.getTitle(),
                            preview,
                           String.format(botUrl,post.getId())
                        );
                    })
                    .toList();
        }

        public PostResponseDTO publishPost(Long id) {
            User user = currentUserService.getCurrentUser();
            Post post = getPostEntityById(id);
            if(!post.getAuthor().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
                throw new ForbiddenException("You do not have permission to publish this post");
            }
            post.setStatus(PostStatus.PUBLISHED);
            Post saved = postRepository.save(post);
            auditLogService.log("PUBLISH_POST","/posts/" + id);
            return postMapper.toResponseDTO(saved);
        }

    }
