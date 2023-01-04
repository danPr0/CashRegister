package tag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class GetParameterValueTag extends SimpleTagSupport {
    private String paramName;

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        String paramValue = pageContext.getRequest().getParameter(paramName);
        if (paramValue != null)
            getJspContext().getOut().print(paramValue);
    }
}
