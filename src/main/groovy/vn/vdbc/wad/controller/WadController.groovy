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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vdbc.wad.model.Post
import vn.vdbc.wad.repository.CategoryRepo
import vn.vdbc.wad.repository.PostRepo


@RestController
@RequestMapping("wad-post")
class WadController {

    @Autowired
    PostRepo postRepo

    @Autowired
    CategoryRepo categoryRepo

    @GetMapping("")
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

    @GetMapping("{id}")
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
                "createdName" : post.createdName,
                "shortContent": post.shortContent,
                "tagNews"     : post.tagNews ?: [],
                "country"     : post.country,
                "categories"  : post.categories?.collect { "/wad-category/" + it.id } ?: [],
                "tags"        : post.tags?.collect { "/wad-tag/" + it.id } ?: [],
                "comments"    : []
        ]
    }


    @PostMapping()
    @Transactional(value = "wadTransactionManager")
    ResponseEntity<?> createPost(@RequestBody Map<String, Object> payload) {
        Post post = new Post()
        updatePostFromPayload(post, payload)
        post.createdAt = new Date()
//        post.createdName = "WORLD ARCHI DESIGN MAGAZINE"
        Post saved = postRepo.save(post)
        return ResponseEntity.ok(convertPostToDTO(saved))
    }

    @PutMapping("{id}")
    @Transactional(value = "wadTransactionManager")
    ResponseEntity<?> updatePost(
            @PathVariable(name = "id") Long id,
            @RequestBody Map<String, Object> payload
    ) {
        Optional<Post> optPost = postRepo.findById(id)
        if (optPost.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        Post post = optPost.get()
        updatePostFromPayload(post, payload)
        Post saved = postRepo.save(post)
        return ResponseEntity.ok(convertPostToDTO(saved))
    }

    private void updatePostFromPayload(Post post, Map<String, Object> payload) {
        post.title = payload.get("title") as String
        post.body = payload.get("body") as String
        post.thumbnail = payload.get("thumbnail") as String
        post.webSource = payload.get("webSource") as String
        post.priority = payload.get("priority") as Integer
        post.src = payload.get("src") as String
        post.status = payload.get("status") as String
        post.projectName = payload.get("projectName") as String
        post.categoryName = payload.get("categoryName") as String
        post.typePost = payload.get("typePost") as String
        post.shortContent = payload.get("shortContent") as String
        post.createdName = payload.get("createdName") as String

        post.country = payload.get("country") as String


        post.tagNews = (payload.get("tagNews") ?: []) as List<String>

        post.updatedAt = new Date()


        List<String> catLinks = (payload.get("categories") ?: []) as List<String>
        if (catLinks) {
            List<Long> catIds = catLinks.collect { it.replace("/wad-category/", "") as Long }
            post.categories = categoryRepo.findAllById(catIds)
        } else {
            post.categories = []
        }

    }
}