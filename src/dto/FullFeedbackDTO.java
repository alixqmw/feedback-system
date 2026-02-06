package dto;

import java.time.LocalDateTime;
import java.util.List;

public record FullFeedbackDTO(
        int id,
        String title,
        String message,
        String status,
        LocalDateTime createdAt,
        UserDTO author,
        CategoryDTO category,
        List<CommentDTO> comments
) {}
