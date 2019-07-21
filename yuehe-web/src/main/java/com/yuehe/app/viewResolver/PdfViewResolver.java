package com.yuehe.app.viewResolver;

import com.yuehe.app.view.PdfView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class PdfViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {

        return new PdfView();
    }
}
