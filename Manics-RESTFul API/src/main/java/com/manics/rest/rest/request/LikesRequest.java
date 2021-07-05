package com.manics.rest.rest.request;

import javax.validation.constraints.NotNull;

public class  LikesRequest  {

    @NotNull
    private Integer likes;

    @NotNull
    private Integer dislikes;

    public Integer getLikes() {
        return likes;
    }

    public Integer getDislikes(){
        return dislikes;
    }

}
