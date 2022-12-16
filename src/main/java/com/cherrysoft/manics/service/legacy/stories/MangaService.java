package com.cherrysoft.manics.service.legacy.stories;

import com.cherrysoft.manics.model.legacy.Manga;
import com.cherrysoft.manics.model.legacy.core.Category;
import com.cherrysoft.manics.repository.legacy.MangaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MangaService extends StoryService {
  private final MangaRepository mangaRepository;

  public List<Manga> getMangas() {
    List<Manga> mangas = new ArrayList<>();
    mangaRepository.findAll().iterator().forEachRemaining(mangas::add);
    return mangas;
  }

  public Manga getMangaById(Integer mangaId) {
    return super.getStoryById(mangaId, Manga.class);
  }

  public Manga createManga(Integer categoryId, Manga manga) {
    Category category = categoryService.getCategory(categoryId);
    manga.setCategory(category);
    mangaRepository.save(manga);
    searchService.saveStorySearch(manga);
    return manga;
  }

  public Manga updateManga(Integer mangaId, Integer categoryId, Manga newManga) {
    Manga manga = getMangaById(mangaId);
    Category category = categoryService.getCategory(categoryId);
    manga.updateStory(category, newManga);
    mangaRepository.save(manga);
    searchService.updateStorySearch(manga.getId(), manga);
    return manga;
  }

  public Manga deleteManga(Integer mangaId) {
    Manga manga = getMangaById(mangaId);
    mangaRepository.delete(manga);
    searchService.deleteStorySearch(manga.getId());
    return manga;
  }

}
