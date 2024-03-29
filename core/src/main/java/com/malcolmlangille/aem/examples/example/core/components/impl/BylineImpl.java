package com.malcolmlangille.aem.examples.example.core.components.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.malcolmlangille.aem.examples.example.core.components.Byline;
import com.adobe.cq.wcm.core.components.models.Image;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {Byline.class},
        resourceType = {BylineImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BylineImpl implements Byline {
    protected static final String RESOURCE_TYPE = "example/components/content/byline";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Self
    private SlingHttpServletRequest request;

    @OSGiService
    private ModelFactory modelFactory;

    @ValueMapValue
    private String name;

    @ValueMapValue
    private List<String> occupations;

    private Image image;

    @PostConstruct
    private void init() {
        image = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Image.class);
    }

    @Override
    public String getName() {
        logger.info("INFO:getName()");
        logger.debug("DEBUG:getName()");
        return name;
    }

    @Override
    public List<String> getOccupations() {
        logger.info("INFO:getOccupations()");
        logger.debug("DEBUG:getOccupations()");
        if (occupations != null) {
            Collections.sort(occupations);
            return occupations;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isEmpty() {
        logger.info("INFO:isEmpty()");
        logger.debug("DEBUG:isEmpty()");
        final Image image = getImage();

        if (StringUtils.isBlank(name)) {
            // Name is missing, but required
            return true;
        } else if (occupations == null || occupations.isEmpty()) {
            // At least one occupation is required
            return true;
        } else if (image == null || StringUtils.isBlank(image.getSrc())) {
            // A valid image is required
            return true;
        } else {
            // Everything is populated, so this component is not considered empty
            return false;
        }
    }

    /**
    * @return the Image Sling Model of this resource, or null if the resource cannot create a valid Image Sling Model. 
    */
    private Image getImage() {
        return image;
    }
}