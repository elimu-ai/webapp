<content:title>
    <fmt:message key="select.language" />
</content:title>

<content:section cssId="editLanguagePage">
    <div class="col s12 m10 l8 offset-m1 offset-l2">
        <h4><content:gettitle /></h4>
        <div class="card-panel">
            <form method="POST">
                <c:if test="${not empty errorCode}">
                    <div id="errorPanel" class="card-panel red lighten-3">
                        <fmt:message key="${errorCode}" />
                    </div>
                </c:if>
                
                <div class="row">
                    <div class="input-field col s12">
                        <select id="language" name="language">
                            <option value="">-- <fmt:message key='select' /> --</option>
                            <c:forEach var="language" items="${languages}">
                                <option value="${language}" <c:if test="${language == contributor.language}">selected="selected"</c:if>><fmt:message key='language.${language.designator}' /></option>
                            </c:forEach>
                        </select>
                        <label for="language"><fmt:message key="language" /></label>
                    </div>
                </div>

                <button class="btn waves-effect waves-light" type="submit" name="action">
                    <fmt:message key="save" /> <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
