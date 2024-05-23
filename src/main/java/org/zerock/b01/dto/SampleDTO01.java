package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class SampleDTO01 {
    private Long id;
    private String name;
    private String contents;
    private LocalDateTime regTime;
}