package com.cherrysoft.manics.model;

import com.cherrysoft.manics.model.core.Story;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comics")
public class Comic extends Story {

}
