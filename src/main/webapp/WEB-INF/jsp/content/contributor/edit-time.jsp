<content:title>
    <fmt:message key="time.available" />
</content:title>

<content:section cssId="editTimePage">
    <div class="col s12 m10 offset-m1 l8 offset-l2">
        <h4><content:gettitle /></h4>
        <div class="card-panel">
            <blockquote>
                <fmt:message key="how.much.time.would.you.like.to.make.available" />
            </blockquote>

            <div class="divider"></div>
            
            <form method="POST">
                <div class="row">
                    <div class="input-field col s12">
                        <label for="timePerWeek"><fmt:message key="minutes.per.week" /></label>
                        <input id="timePerWeek" name="timePerWeek" <c:if test="${not empty contributor.timePerWeek}">value="${contributor.timePerWeek}"</c:if> type="number" class="validate" required="required">
                    </div>
                </div>

                <button id="submitButton" class="btn deep-purple lighten-1 waves-effect waves-light" type="submit">
                    <fmt:message key="save" /> <i class="material-icons right">send</i>
                </button>
            </form>
        </div>
    </div>
</content:section>
