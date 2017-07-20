<content:title>
    <fmt:message key="select.language" />
</content:title>

<content:section cssId="editLocalePage">
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
                        <select id="locale" name="locale">
                            <option value="">-- <fmt:message key='select' /> --</option>
                            <c:forEach var="locale" items="${locales}">
                                <option value="${locale}" <c:if test="${locale == contributor.locale}">selected="selected"</c:if>><fmt:message key='language.${locale.language}' /></option>
                            </c:forEach>
                        </select>
                        <label for="language"><fmt:message key="language" /></label>
                    </div>
                </div>

                <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                    <fmt:message key="save" /> <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
