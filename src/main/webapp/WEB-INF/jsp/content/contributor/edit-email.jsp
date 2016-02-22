<content:title>
    <fmt:message key="edit.email" />
</content:title>

<content:section cssId="editEmailPage">
    <div class="section row">
        <div class="col s12 m10 offset-m1 l8 offset-l2">
            <h4><content:gettitle /></h4>
            <div class="card-panel">
                <form method="POST">
                    <div class="row">
                        <div class="input-field col s12">
                            <label for="email"><fmt:message key="email" /></label>
                            <input id="email" name="email" <c:if test="${not empty contributor.email}">value="${contributor.email}"</c:if> type="email" class="validate" required="required">
                        </div>
                    </div>
                      
                    <button class="btn waves-effect waves-light" type="submit" name="action">
                        <fmt:message key="save" /> <i class="material-icons right">send</i>
                    </button>
                </form>
            </div>
        </div>
    </div>
</content:section>
