<content:title>
    <fmt:message key="allophones" />
</content:title>

<content:section cssId="allophoneListPage">
    <c:if test="${empty allophones}">
        <p>
            <fmt:message key="to.add.new.content.click.the.button.below" />
        </p>
    </c:if>
    
    TODO...
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/allophone/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.allophone" />"><i class="material-icons">record_voice_over</i></a>
    </div>
</content:section>
