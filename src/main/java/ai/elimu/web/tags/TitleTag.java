package ai.elimu.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Implementation of the {@code content:title} tag.
 */
public class TitleTag extends BodyTagSupport {

    @Override
    public int doEndTag() throws JspException {
        pageContext.setAttribute("titleContent", bodyContent.getString());
        return EVAL_PAGE;
    }
}
