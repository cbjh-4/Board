package org.zerock.b01.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long rno;
    @NotNull
    private Long bno;
    @NotEmpty
    private String replyText;
    @NotEmpty
    private String replyer; //댓글 적는 사람
    private LocalDateTime regDate, modDate;
}
