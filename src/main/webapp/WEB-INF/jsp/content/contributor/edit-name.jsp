<content:title>
    Edit name
</content:title>

<content:section cssId="editNamePage">
    <div class="col s12 m10 l8 offset-m1 offset-l2">
        <h4><content:gettitle /></h4>
        <div class="card-panel">
            <form method="POST">
                <div class="row">
                    <div class="input-field col s6">
                        <label for="firstName">First name</label>
                        <input id="firstName" name="firstName" <c:if test="${not empty contributor.firstName}">value="${contributor.firstName}"</c:if> type="text" class="validate" required="required" pattern=".{2,}" title="Minimum 2 characters">
                    </div>
                    <div class="input-field col s6">
                        <label for="lastName">Last name</label>
                        <input id="lastName" name="lastName" <c:if test="${not empty contributor.lastName}">value="${contributor.lastName}"</c:if> type="text" class="validate" required="required" pattern=".{2,}" title="Minimum 2 characters">
                    </div>
                </div>

                <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                    Save <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
