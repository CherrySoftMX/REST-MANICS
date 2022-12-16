package com.cherrysoft.manics.mappers.legacy;

import com.cherrysoft.manics.model.legacy.core.Page;
import com.cherrysoft.manics.controller.legacy.request.page.PageRequest;
import com.cherrysoft.manics.controller.legacy.request.page.PageUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PageMapper {

  public abstract Page pageRequestToPage(PageRequest request);

  public abstract Page pageUpdateRequestToPage(PageUpdateRequest request);

}
