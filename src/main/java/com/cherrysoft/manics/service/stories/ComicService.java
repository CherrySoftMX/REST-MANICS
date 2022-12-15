package com.cherrysoft.manics.service.stories;

import com.cherrysoft.manics.model.Comic;
import com.cherrysoft.manics.model.core.Category;
import com.cherrysoft.manics.repository.ComicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ComicService extends StoryService {
  private final ComicRepository comicRepository;

  public List<Comic> getComics() {
    List<Comic> comics = new ArrayList<>();
    comicRepository.findAll().iterator().forEachRemaining(comics::add);
    return comics;
  }

  public Comic getComicById(Integer comicId) {
    return super.getStoryById(comicId, Comic.class);
  }

  public Comic createComic(Integer categoryId, Comic comic) {
    Category category = categoryService.getCategory(categoryId);
    comic.setCategory(category);
    comicRepository.save(comic);
    searchService.saveStorySearch(comic);
    return comic;
  }

  public Comic updateComic(Integer comicId, Integer categoryId, Comic newComic) {
    Comic comic = getComicById(comicId);
    Category category = categoryService.getCategory(categoryId);

    comic.updateStory(category, newComic);
    comicRepository.save(comic);
    searchService.updateStorySearch(comic.getId(), comic);
    return comic;
  }

  public Comic deleteComic(Integer comicId) {
    Comic comic = getComicById(comicId);
    comicRepository.delete(comic);
    searchService.deleteStorySearch(comicId);
    return comic;
  }

}
