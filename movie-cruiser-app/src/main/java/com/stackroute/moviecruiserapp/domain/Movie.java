package com.stackroute.moviecruiserapp.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    private int id;
    @NotNull
    private String title,language;
    @Size(min=2, message="Comments should have atleast 2 characters")
    private String comments;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime releaseDate;

//    public Movie() {
//        releaseDate=LocalDateTime.now();
//    }
//
    public Movie(int id, String title, String language, String comments) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.comments = comments;
        this.releaseDate=LocalDateTime.now();
    }
//
//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public LocalDateTime getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(LocalDateTime releaseDate) {
//        this.releaseDate = releaseDate;
//    }
//    @Override
//    public String toString(){
//        return id+title+comments+language+releaseDate;
//    }
}
