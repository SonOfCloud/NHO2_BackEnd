package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CampRequestDto {

    @NotNull(message = "训练营标题不能为空")
    @NotBlank(message = "训练营标题不能为空")
    @Length(max = 256, message = "训练营标题长度最长256")
    private String title;

    @NotNull(message = "训练营描述不能为空")
    @NotBlank(message = "训练营描述不能为空")
    @Length(max = 1024, message = "训练营描述长度最长1024")
    private String description;
}
