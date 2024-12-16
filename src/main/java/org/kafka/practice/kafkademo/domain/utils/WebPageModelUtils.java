package org.kafka.practice.kafkademo.domain.utils;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class WebPageModelUtils {

    public static void addRequiredAttributes(@NonNull final Model model, @NonNull final String contentPath,
                                             @NonNull final Page<?> contentPage, final int pageUpdateIntervalMs) {
        model.addAttribute("updateInterval", pageUpdateIntervalMs);
        model.addAttribute("contentPath", contentPath);
        model.addAttribute("contentPage", contentPage);
    }

}
