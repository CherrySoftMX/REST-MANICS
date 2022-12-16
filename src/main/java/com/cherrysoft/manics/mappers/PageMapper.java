package com.cherrysoft.manics.mappers;

import com.cherrysoft.manics.model.legacy.core.Page;
import com.cherrysoft.manics.controller.request.page.PageRequest;
import com.cherrysoft.manics.controller.request.page.PageUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PageMapper {

  public abstract Page pageRequestToPage(PageRequest request);

  public abstract Page pageUpdateRequestToPage(PageUpdateRequest request);

}
