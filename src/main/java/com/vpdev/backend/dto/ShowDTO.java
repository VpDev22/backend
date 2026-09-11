/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vpdev.backend.dto;

import java.util.List;

/**
 *
 * @author marco
 */
public class ShowDTO {
    
    private Long id;
    private String name;
    private String channel;
    private String summary;
    private List<String> genres;
    private List<CommentDTO> comments;

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public String getChannel() { 
        return channel; 
    }
    public void setChannel(String channel) { 
        this.channel = channel; 
    }
    public String getSummary() { 
        return summary; 
    }
    public void setSummary(String summary) { 
        this.summary = summary; 
    }
    public List<String> getGenres() { 
        return genres; 
    }
    public void setGenres(List<String> genres) { 
        this.genres = genres; 
    }
    public List<CommentDTO> getComments() { 
        return comments; 
    }
    public void setComments(List<CommentDTO> comments) { 
        this.comments = comments; 
    }
    
}
