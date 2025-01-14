package org.kafka.practice.kafkademo.domain.config.cache;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CacheKeyBuilder {

    public static final String COMPANY_PAGE_CACHE_NAME = "company_page_cache";
    public static final String PERSON_PAGE_CACHE_NAME = "person_page_cache";
    public static final String HOBBY_PAGE_CACHE_NAME = "hobby_page_cache";

    public String buildPageKey(Pageable pageable) {
        return String.format("page_%s_size_%s_sort_%s",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
    }

}
