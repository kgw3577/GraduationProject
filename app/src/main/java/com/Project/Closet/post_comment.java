package com.Project.Closet;

public class post_comment {
    private String Nickname;
    private String Comment;
    private String Likes;

    public post_comment(String Nickname, String Comment, String Likes) {
        this.Nickname = Nickname;
        this.Comment = Comment;
        this.Likes = Likes;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        this.Nickname = nickname;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        this.Comment = comment;
    }

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        this.Likes = likes;
    }
}
