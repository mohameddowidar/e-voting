package com.electronic.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NavigationDTO {
    private String title;
    private String iconName;
    private String url;
    private String encId;
}
