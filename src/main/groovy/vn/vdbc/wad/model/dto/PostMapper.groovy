package vn.vdbc.wad.model.dto

import vn.vdbc.wad.model.Post
import vn.vdbc.wad.model.Tag

class PostMapper {

    static PostDTO toDto(Post post) {
        PostDTO dto = new PostDTO()

        dto.setId(post.getId())
        dto.setTitle(post.getTitle())
        dto.setBody(post.getBody())
        dto.setThumbnail(post.getThumbnail())
        dto.setWebSource(post.getWebSource())
        dto.setPriority(post.getPriority())
        dto.setSrc(post.getSrc())
        dto.setStatus(post.getStatus())
        dto.setProjectName(post.getProjectName())
        dto.setCategoryName(post.getCategoryName())
        dto.setTypePost(post.getTypePost())
        dto.setShortContent(post.getShortContent())

        if (post.getCategories() != null) {
            dto.setCategories(
                    post.getCategories()
                            .stream()
                            .map(c -> "/wad-category" + "/" + c.getId())
                            .toList()
            )
        }

        if (post.getTags() != null) {
            dto.setTags(
                    post.getTags()
                            .stream()
                            .map(Tag::getName)
                            .toList()
            )
        }

        dto.setTagNews(post.getTagNews())

        return dto
    }
}

