package com.manics.rest.service;

import com.manics.rest.model.Manga;
import com.manics.rest.model.core.Category;
import com.manics.rest.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MangaService {

    private final MangaRepository mangaRepository;
    private final CategoryService categoryService;

    @Autowired
    public MangaService(MangaRepository mangaRepository, CategoryService categoryService) {
        this.mangaRepository = mangaRepository;
        this.categoryService = categoryService;
    }

    public List<Manga> getMangas() {
        List<Manga> mangas = new ArrayList<>();
        mangaRepository.findAll().iterator().forEachRemaining(mangas::add);
        return mangas;
    }

    public Manga getMangaById(Integer id) {
        return mangaRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException("dfasd")
                );
    }

    public Manga createManga(Integer categoryId, Manga manga) {
        if (!Objects.isNull(categoryId)) {
            Category category = categoryService.getCategory(categoryId);
            manga.setCategory(category);
        }

        return mangaRepository.save(manga);
    }

    public Manga updateManga(Integer mangaId, Integer categoryId, Manga newManga) {
        Manga manga = getMangaById(mangaId);

        Category category = categoryService.getCategory(categoryId);
        manga.updateStory(category, newManga);

        mangaRepository.save(manga);

        return manga;
    }


    public Manga deleteManga(Integer mangaId) {
        Manga manga = getMangaById(mangaId);

        mangaRepository.delete(manga);

        return manga;
    }

    public boolean isCategoryBeingUse(Category category) {
        return mangaRepository.findAllByCategory(category).size() > 0;
    }

}
