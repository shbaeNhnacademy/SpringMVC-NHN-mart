package com.nhnacademy.project;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.*;
import java.util.EnumSet;
import java.util.Objects;

@Order(1)
public class FilterInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

    }

    private void registerFilter(ServletContext servletContext, boolean insertBeforeOtherFilters, String filterName, Filter filter) {
        FilterRegistration.Dynamic registration= servletContext.addFilter(filterName, filter);
        if (Objects.isNull(registration)) {
            throw new IllegalStateException("Duplicate Filter registration for '" + filterName
                    + "'. Check to ensure the Filter is only configured once.");
        }

        registration.setAsyncSupported(true);
        EnumSet<DispatcherType> dispatcherTypes = getSecurityDispatcherTypes();
        registration.addMappingForUrlPatterns(dispatcherTypes, !insertBeforeOtherFilters, "/*");
    }

    private EnumSet<DispatcherType> getSecurityDispatcherTypes() {
        return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC);
    }
}
