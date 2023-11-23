package com.article.service.entities;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDTO {
    private Long id;
    private String name;
    private String email;
}
