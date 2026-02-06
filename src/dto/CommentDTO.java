package dto;

import java.time.LocalDateTime;

public record CommentDTO(int id, String text, LocalDateTime createdAt, UserDTO author) {}
