package com.manics.rest.model;

import com.manics.rest.model.core.Story;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comics")
public class Comic extends Story {

}
