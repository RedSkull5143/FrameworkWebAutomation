package com.omshinde.capstone.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchContent {
    private String input;

    public SearchContent init(){
        return SearchContent.builder()
                .input("Dress")
                .build();
    }

    public SearchContent bellDress(){
        return SearchContent.builder()
                .input("Robe Dress")
                .build();
    }

    public SearchContent soldOutProduct(){
        return SearchContent.builder()
                .input("12 Ti Xelium Skis")
                .build();
    }
    public SearchContent shoes(){
        return SearchContent.builder()
                .input("Shoes")
                .build();
    }

    public SearchContent jacket(){
        return SearchContent.builder()
                .input("Jacket")
                .build();
    }

    public SearchContent specialCharacters(){
        return SearchContent.builder()
                .input("%&$")
                .build();
    }

    public String selectJacket(){
        return "Reasonable Jacket";
    }

    public String selectDress(){
        return "Bell Dress";
    }

}
