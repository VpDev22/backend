/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vpdev.backend.dto;

/**
 *
 * @author marco
 */
public class CommentDTO {
    private String comment;
    private Integer rating;
    
    public CommentDTO() {}
    
    public CommentDTO(String comment, Integer rating) {
        this.comment = comment;
        this.rating = rating;
    }
    
    public String getComment() { 
        return comment; 
    }
    
    public void setComment(String comment) { 
        this.comment = comment; 
    }
    
    public Integer getRating() { 
        return rating; 
    }
    
    public void setRating(Integer rating) { 
        this.rating = rating; 
    }
    
}
