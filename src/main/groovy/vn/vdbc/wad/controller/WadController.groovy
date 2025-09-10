package vn.vdbc.wad.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.rest.webmvc.BasePathAwareController
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import vn.vdbc.wad.model.Post
import vn.vdbc.wad.repository.PostRepo

@BasePathAwareController
class WadController {

    @Autowired
    PostRepo postRepo

    @GetMapping("wad-post")
    @Transactional(value = "wadTransactionManager", readOnly = true)
    ResponseEntity<?> getAllPosts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending())
        Page<Post> posts = postRepo.findAll(pageable)

        def postDTOs = posts.content.collect { post ->
            post.tagNews?.size()
            post.comments?.size()
            post.categories?.size()
            post.tags?.size()

            convertPostToDTO(post)
        }

        return ResponseEntity.ok([
                "value"       : postDTOs,
                "@odata.count": posts.getTotalElements()
        ])
    }

    @GetMapping("wad-post/{id}")
    @Transactional(value = "wadTransactionManager", readOnly = true)
    ResponseEntity<?> getPostById(@PathVariable(name = "id") Long id) {
        return postRepo.findById(id)
                .map { post ->
                    post.tagNews?.size()
                    post.comments?.size()
                    post.categories?.size()
                    post.tags?.size()

                    ResponseEntity.ok(convertPostToDTO(post))
                }
                .orElse(ResponseEntity.notFound().build())
    }

    private static Map<String, Object> convertPostToDTO(Post post) {
        return [
                "id"          : post.id,
                "title"       : post.title,
                "body"        : post.body,
                "thumbnail"   : post.thumbnail,
                "webSource"   : post.webSource,
                "priority"    : post.priority,
                "src"         : post.src,
                "status"      : post.status,
                "projectName" : post.projectName,
                "categoryName": post.categoryName,
                "typePost"    : post.typePost,
                "shortContent": post.shortContent,
                "tagNews"     : post.tagNews ?: [],
                "categories"  : post.categories?.collect { "/wad-category/" + it.id } ?: [],
                "tags"        : post.tags?.collect { "/wad-tag/" + it.id } ?: [],
                "comments"    : []
        ]
    }
}