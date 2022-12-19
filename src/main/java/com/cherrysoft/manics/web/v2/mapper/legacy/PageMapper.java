package com.cherrysoft.manics.web.v2.mapper.legacy;

import com.cherrysoft.manics.model.legacy.core.Page;
import com.cherrysoft.manics.web.legacy.request.page.PageRequest;
import com.cherrysoft.manics.web.legacy.request.page.PageUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PageMapper {

  public abstract Page pageRequestToPage(PageRequest request);

  public abstract Page pageUpdateRequestToPage(PageUpdateRequest request);

}
