package com.cherrysoft.manics.service.legacy.stories;

import com.cherrysoft.manics.service.legacy.search.StorySearchService;
import com.cherrysoft.manics.exception.NotFoundException;
import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.model.legacy.core.Category;
import com.cherrysoft.manics.model.legacy.core.Story;
import com.cherrysoft.manics.repository.legacy.StoryRepository;
import com.cherrysoft.manics.service.legacy.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class StoryService {

  @Autowired
  protected StoryRepository storyRepository;

  @Autowired
  protected UserService userService;

  @Autowired
  protected CategoryService categoryService;

  @Autowired
  protected StorySearchService searchService;

  @Autowired
  protected ChapterService chapterService;

  @Autowired
  protected PageService pageService;

  public StoryService() {

  }

  public <T extends Story> T getStoryById(Integer storyId, Class<T> clazz) {
    Optional<Story> storyMaybe = storyRepository.findById(storyId);
    if (storyMaybe.isEmpty() || !clazz.isInstance(storyMaybe.get())) {
      throw new NotFoundException(String.format("No encontramos el relato con el id: %d", storyId));
    }
    return (T) storyMaybe.get();
  }

  @Deprecated
  public Story getStoryById(Integer storyId) {
    return storyRepository
        .findById(storyId)
        .orElseThrow(() -> new NotFoundException(
            String.format("No encontramos el relato con el id: %d", storyId))
        );
  }

  public Set<Story> getReadLater(String username) {
    User user = userService.getUserByUsername(username);
    return user.getReadLater();
  }

  public Set<Story> getReadLater(Integer userId) {
    User user = userService.getUserById(userId);
    return user.getReadLater();
  }

  public Story toggleLike(Integer storyId, String username) {
    Story story = getStoryById(storyId);
    User user = userService.getUserByUsername(username);
    boolean likedByUser = story.isLikedBy(user.getUserId());
    if (likedByUser) {
      story.removeLikedBy(user.getUserId());
      user.removeLike(storyId);
    } else {
      story.addLikedBy(user);
      user.addLike(story);
    }
    userService.saveUser(user);
    return storyRepository.save(story);
  }

  public Story toggleReadLater(Integer storyId, String username) {
    Story story = getStoryById(storyId);
    User user = userService.getUserByUsername(username);
    if (user.isSavedInReadLater(story.getId())) {
      user.removeFromReadLater(story.getId());
    } else {
      user.addToReadLater(story);
    }
    userService.saveUser(user);
    return story;
  }

  public boolean isCategoryBeingUse(Category category) {
    return storyRepository.findAllByCategory(category).size() > 0;
  }

}
