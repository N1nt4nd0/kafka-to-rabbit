package org.kafka.practice.kafkademo.domain.utils;

import org.springframework.ui.Model;

public class WebPageModelUtils {

    public static void addRequiredAttributes(final Model model,
                                             final String contentApiPath,
                                             final int pageNumber,
                                             final int pageSize,
                                             final int pageUpdateIntervalMs) {
        model.addAttribute("contentApiPath", contentApiPath);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("updateInterval", pageUpdateIntervalMs);
    }

}
