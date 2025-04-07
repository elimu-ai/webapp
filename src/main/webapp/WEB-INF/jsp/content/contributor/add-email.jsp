<content:title>
    Add e-mail
</content:title>

<content:section cssId="addEmailPage">
    <div class="col s12 m10 offset-m1 l8 offset-l2">
        <h4><content:gettitle /></h4>
        <div class="card-panel">
            <form method="POST">
                <div class="row">
                    <div class="input-field col s12">
                        <label for="email">E-mail</label>
                        <input id="email" name="email" <c:if test="${not empty contributor.email}">value="${contributor.email}"</c:if> type="email" class="validate" required="required">
                    </div>
                </div>

                <button id="submitButton" class="btn-large waves-effect waves-light" type="submit">
                    Save <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
