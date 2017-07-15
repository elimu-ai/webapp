package ai.elimu.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Implementation of the {@code content:aside} tag.
 */
public class AsideTag extends BodyTagSupport {
    
    private String cssClass;

    @Override
    public int doEndTag() throws JspException {
        pageContext.setAttribute("asideCssClass", cssClass);
        pageContext.setAttribute("asideContent", bodyContent.getString());
        pageContext.setAttribute("hasAside", true);
        return EVAL_PAGE;
    }
    
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
