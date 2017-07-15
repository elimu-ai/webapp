package ai.elimu.web.tags;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Implementation of the {@code content:gettitle} tag.
 */
public class GetTitleTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        Writer out = pageContext.getOut();
        try {
            String content = (String) pageContext.getAttribute("titleContent");
            out.write(content);
        } catch (IOException ex) {
            throw new JspTagException(ex);
        }
        return SKIP_BODY;
    }
}
