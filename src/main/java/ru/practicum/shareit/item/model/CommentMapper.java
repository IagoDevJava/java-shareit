package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    /**
     * Конвертирование Comment в CommentDto
     */
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .itemId(comment.getItemId())
                .authorId(comment.getAuthorId())
                .created(comment.getCreated())
                .build();
    }

    /**
     * Конвертирование CommentDto в Comment
     */
    public static Comment toComment(CommentDto commentDto, long userId) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .itemId(commentDto.getItemId())
                .authorId(commentDto.getAuthorId())
                .created(commentDto.getCreated())
                .build();
    }

    /**
     * Конвертация списка Comment в CommentDto
     */
    public static List<CommentDto> mapToCommentDto(Iterable<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();
        for (Comment comment : comments) {
            result.add(toCommentDto(comment));
        }
        return result;
    }
}
