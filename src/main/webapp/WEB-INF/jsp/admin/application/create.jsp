<content:title>
    <fmt:message key="add.application" />
</content:title>

<content:section cssId="applicationCreatePage">
    <h4><content:gettitle /></h4>
    <div class="card-panel">
        <form:form modelAttribute="application">
            <tag:formErrors modelAttribute="application" />

            <div class="row">
                <form:hidden path="locale" value="${contributor.locale}" />
                <form:hidden path="applicationStatus" value="${application.applicationStatus}" />
                <form:hidden path="contributor" value="${contributor.id}" />
                <div class="input-field col s6">
                    <form:label path="packageName" cssErrorClass="error"><fmt:message key='package.name' /></form:label>
                    <form:input path="packageName" cssErrorClass="error" placeholder="ai.elimu.soundcards" />
                </div>
            </div>
            
            <div class="row">
                <div class="col s12 m6">
                    <h5><fmt:message key="literacy.skills" /></h5>
                    <blockquote>
                        What <i>literacy</i> skill(s) does the application teach?
                    </blockquote>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(application.literacySkills, literacySkill)}">checked="checked"</c:if> />
                        <label for="${literacySkill}">
                            <fmt:message key="literacy.skill.${literacySkill}" />
                        </label><br />
                    </c:forEach>
                </div>

                <div class="col s12 m6">
                    <h5><fmt:message key="numeracy.skills" /></h5>
                    <blockquote>
                        What <i>numeracy</i> skill(s) does the application teach?
                    </blockquote>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(application.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                        <label for="${numeracySkill}">
                            <fmt:message key="numeracy.skill.${numeracySkill}" />
                        </label><br />
                    </c:forEach>
                </div>
            </div>

            <button id="submitButton" class="btn green waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
