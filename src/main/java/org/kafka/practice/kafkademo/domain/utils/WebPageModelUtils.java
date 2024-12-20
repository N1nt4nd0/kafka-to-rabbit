package org.kafka.practice.kafkademo.domain.utils;

import org.kafka.practice.kafkademo.domain.config.EndpointsConfig;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.springframework.ui.Model;

public class WebPageModelUtils {

    public static void addRequiredAttributes(final Model model,
                                             final EndpointsConfig endpointsConfig,
                                             final WebPagesConfig webPagesConfig,
                                             final int pageNumber,
                                             final int pageSize) {
        model.addAttribute("endpoints", endpointsConfig);
        model.addAttribute("webConfig", webPagesConfig);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
    }

}
