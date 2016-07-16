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
                    <form:input path="packageName" cssErrorClass="error" placeholder="org.literacyapp" />
                </div>
            </div>
            
            <div class="row">
                <div class="col s12 m6">
                    <h5><fmt:message key="literacy.skills" /></h5>
                    <blockquote>
                        What literacy skill(s) does the application teach?
                    </blockquote>
                    <div class="divider"></div>
                    <c:forEach var="literacySkill" items="${literacySkills}">
                        <p>
                            <input type="checkbox" name="literacySkills" id="${literacySkill}" value="${literacySkill}" <c:if test="${fn:contains(application.literacySkills, literacySkill)}">checked="checked"</c:if> />
                            <label for="${literacySkill}">
                                <b><fmt:message key="literacy.skill.${literacySkill}" /></b>
                                <br /><fmt:message key="literacy.skill.${literacySkill}.description" />
                            </label>
                        </p>
                    </c:forEach>
                </div>

                <div class="col s12 m6">
                    <h5><fmt:message key="numeracy.skills" /></h5>
                    <blockquote>
                        What numeracy skill(s) does the application teach?
                    </blockquote>
                    <div class="divider"></div>
                    <c:forEach var="numeracySkill" items="${numeracySkills}">
                        <p>
                            <input type="checkbox" name="numeracySkills" id="${numeracySkill}" value="${numeracySkill}" <c:if test="${fn:contains(application.numeracySkills, numeracySkill)}">checked="checked"</c:if> />
                            <label for="${numeracySkill}">
                                <b><fmt:message key="numeracy.skill.${numeracySkill}" /></b><br />
                                <img src="<spring:url value="/img/admin/EGMA_${numeracySkill}.png" />" alt="<fmt:message key="numeracy.skill.${numeracySkill}" />" /><br />
                                <fmt:message key="numeracy.skill.${numeracySkill}.description" />
                            </label>
                        </p>
                    </c:forEach>
                </div>
            </div>

            <button id="submitButton" class="btn waves-effect waves-light" type="submit">
                <fmt:message key="add" /> <i class="material-icons right">send</i>
            </button>
        </form:form>
    </div>
</content:section>
