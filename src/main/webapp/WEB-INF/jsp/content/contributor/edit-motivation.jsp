<content:title>
    <fmt:message key="personal.motivation" />
</content:title>

<content:section cssId="editMotivationPage">
    <div class="col s12 m10 l8 offset-m1 offset-l2">
        <h4><content:gettitle /></h4>
        <div class="card-panel">
            <blockquote>
                <fmt:message key="what.is.your.personal.motivation" />
            </blockquote>

            <div class="divider"></div>
            
            <form method="POST">
                <c:if test="${not empty errorCode}">
                    <div id="errorPanel" class="card-panel red lighten-3">
                        <fmt:message key="${errorCode}" />
                    </div>
                </c:if>
                
                <div class="row">
                    <div class="input-field col s6">
                        <label for="motivation"><fmt:message key="personal.motivation" /></label>
                        <input id="motivation" name="motivation" <c:if test="${not empty contributor.motivation}">value="${contributor.motivation}"</c:if> type="text" class="validate" title="Minimum 2 characters">
                    </div>
                </div>

                <button class="btn waves-effect waves-light" type="submit" name="action">
                    <fmt:message key="save" /> <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
