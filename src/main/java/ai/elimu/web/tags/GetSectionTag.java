package ai.elimu.web.tags;

import java.io.IOException;
import java.io.Writer;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * Implementation of the {@code content:getsection} tag.
 */
public class GetSectionTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        Writer out = pageContext.getOut();
        try {
            String content = (String) pageContext.getAttribute("sectionContent");
            out.write(content);
        } catch (IOException ex) {
            throw new JspTagException(ex);
        }
        return SKIP_BODY;
    }
}
