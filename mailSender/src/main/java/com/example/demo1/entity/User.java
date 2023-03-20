package com.example.demo1.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Bean;

import lombok.Data;
@Entity
@Table(name = "student")
@Data
public class User {
 
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  private String name;
  
  private String email;
  
  private String password;
  
  private String tempPass;
}
