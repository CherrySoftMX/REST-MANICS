package com.manics.rest.model;

import com.manics.rest.model.core.Story;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mangas")
public class Manga extends Story {

}
