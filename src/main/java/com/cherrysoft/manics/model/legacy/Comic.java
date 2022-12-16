package com.cherrysoft.manics.model.legacy;

import com.cherrysoft.manics.model.legacy.core.Story;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comics")
public class Comic extends Story {

}
