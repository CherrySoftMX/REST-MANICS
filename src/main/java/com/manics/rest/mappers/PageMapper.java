package com.manics.rest.mappers;

import com.manics.rest.model.core.Page;
import com.manics.rest.rest.request.page.PageRequest;
import com.manics.rest.rest.request.page.PageUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PageMapper {

  public abstract Page pageRequestToPage(PageRequest request);

  public abstract Page pageUpdateRequestToPage(PageUpdateRequest request);

}
