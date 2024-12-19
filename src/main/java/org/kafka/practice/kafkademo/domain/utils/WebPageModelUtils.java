package org.kafka.practice.kafkademo.domain.utils;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class WebPageModelUtils {

    public static void addRequiredAttributes(final Model model,
                                             final String contentPath,
                                             final String apiContentPath,
                                             final Page<?> contentPage,
                                             final int pageUpdateIntervalMs) {
        model.addAttribute("updateInterval", pageUpdateIntervalMs);
        model.addAttribute("contentApiPath", apiContentPath);
        model.addAttribute("contentPath", contentPath);
        model.addAttribute("contentPage", contentPage);
    }

}
