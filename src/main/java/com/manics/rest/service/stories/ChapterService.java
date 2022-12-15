package com.manics.rest.service.stories;

import com.manics.rest.exception.BadRequestException;
import com.manics.rest.exception.NotFoundException;
import com.manics.rest.model.core.Chapter;
import com.manics.rest.model.core.Story;
import com.manics.rest.repository.ChapterRepository;
import com.manics.rest.service.search.StorySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService<T extends Story> {
  @Autowired
  private StoryService storyService;

  @Autowired
  private StorySearchService searchService;

  @Autowired
  private ChapterRepository chapterRepository;

  @Deprecated
  public List<Chapter> getChapters() {
    List<Chapter> chapters = new ArrayList<>();
    chapterRepository.findAll().iterator().forEachRemaining(chapters::add);
    return chapters;
  }

  public List<Chapter> getChaptersByStoryId(Integer storyId, Class<T> clazz) {
    Story story = storyService.getStoryById(storyId, clazz);
    return chapterRepository.getChaptersByStory_Id(story.getId());
  }

  @Deprecated
  public Chapter getChapter(Integer chapterId) {
    return chapterRepository
        .findById(chapterId)
        .orElseThrow(
            () -> new NotFoundException(String.format("El capítulo con el id: %d no existe", chapterId))
        );
  }

  public Chapter getChapter(Integer storyId, Integer chapterId, Class<T> clazz) {
    List<Chapter> chapters = getChaptersByStoryId(storyId, clazz);
    chapters.stream()
        .filter(chapter -> chapter.getChapterId().equals(chapterId))
        .findAny()
        .orElseThrow(
            () -> new BadRequestException(String.format("El relato con el id: %s no tiene ningún capítulo con el id: %s", storyId, chapterId))
        );
    return getChapter(chapterId);
  }

  public Chapter createChapter(Integer storyId, Chapter chapter, Class<T> clazz) {
    Story story = storyService.getStoryById(storyId, clazz);
    chapter.setStory(story);
    return chapterRepository.save(chapter);
  }

  public Chapter updateChapter(Integer storyId, Integer chapterId, Chapter newChapter, Class<T> clazz) {
    checkIfChapterExists(storyId, chapterId, clazz);
    Chapter chapter = getChapter(chapterId);
    chapter.updateChapter(newChapter);
    return chapterRepository.save(chapter);
  }

  public Chapter deleteChapter(Integer storyId, Integer chapterId, Class<T> clazz) {
    Chapter chapter = getChapter(storyId, chapterId, clazz);
    searchService.deleteIndexedChapter(storyId, chapter);
    chapterRepository.deleteById(chapter.getChapterId());
    return chapter;
  }

  public void checkIfChapterExists(Integer storyId, Integer chapterId, Class<T> clazz) {
    getChapter(storyId, chapterId, clazz);
  }

}
