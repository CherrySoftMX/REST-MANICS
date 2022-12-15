package com.cherrysoft.manics.mappers;

import com.cherrysoft.manics.model.core.Page;
import com.cherrysoft.manics.rest.request.page.PageRequest;
import com.cherrysoft.manics.rest.request.page.PageUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PageMapper {

  public abstract Page pageRequestToPage(PageRequest request);

  public abstract Page pageUpdateRequestToPage(PageUpdateRequest request);

}
