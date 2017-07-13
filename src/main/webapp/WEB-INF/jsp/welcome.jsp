<content:title>
    <fmt:message key='free.quality.education.for.every.child' />
</content:title>

<content:banner>
    <br />
    <br />
    <br />
    <br />
    <div class="row center">
        <h1 class="header center white-text">elimu<span>.ai</span></h1>
    </div>
    <div class="row center">
        <div class="col s3">&nbsp;</div>
        <h5 class="header col s6 light white-text"><fmt:message key="free.quality.education.for.every.child" /></h5>
        <div class="col s3">&nbsp;</div>
    </div>
    <div class="row center">
      <a href="<spring:url value='/sign-on' />" id="join-button-top" class="btn-large waves-effect waves-light purple"><fmt:message key="join.now" /></a>
    </div>
    <br />
</content:banner>

<content:section cssId="welcomePage">
    <div class="section">
        <!--   Icon Section   -->
        <div class="row">
          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">group</i></h2>
              <h5 class="center"><fmt:message key="challenge" /></h5>

              <p class="light center"><fmt:message key="57.million.children.are.currently.out.of.school" /></p>
            </div>
          </div>
          
          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">stay_current_landscape</i></h2>
              <h5 class="center"><fmt:message key="solution" /></h5>

              <p class="light center"><fmt:message key="we.build.tablet.based.software" /></p>
              <p class="light center"><fmt:message key="source.code" />: <a href="https://github.com/elimu-ai"><fmt:message key="github.repository" /></a></p>
            </div>
          </div>

          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">person_add</i></h2>
              <h5 class="center"><fmt:message key="how.can.i.help?" /></h5>

              <p class="light center"><fmt:message key="we.are.looking.for" /></p>
              <p class="light center"><fmt:message key="frontpage.how.can.i.help.description2" />&nbsp;<a href="http://eepurl.com/bGihkr"><c:set var="mailingList">
                          <fmt:message key="mailing.list" />
                      </c:set>${fn:toLowerCase(mailingList)}</a> 
                <fmt:message key="or" />&nbsp;<a href="<spring:url value='/sign-on' />"><c:set var="joinTheCommunity">
                          <fmt:message key="join.the.community" />
                      </c:set>${fn:toLowerCase(joinTheCommunity)}</a>.
              </p>
            </div>
          </div>
        </div>
    </div>

    <div class="divider"></div>
            
    <div class="section">
        <div class="row">
            <div class="col s12 m4 offset-m4">
                <div class="card small">
                  <div class="card-image">
                    <img src="<spring:url value='/img/banner-en.jpg' />" alt="Global Learning XPRIZE" />
                    <span class="card-title">Global Learning XPRIZE</span>
                  </div>
                  <div class="card-content">
                      <p>
                          <img src="<spring:url value='/img/global-learning-xprize-600x142.png' />" alt="Global Learning XPRIZE" style="max-width: 100%;" />
                      </p>
                  </div>
                  <div class="card-action">
                    <a href="http://learning.xprize.org" target="_blank">learning.xprize.org</a>
                  </div>
                </div>
            </div>
        </div>
        <div class="row center">
            <div class="video-container">
                <iframe width="560" height="315" src="https://www.youtube.com/embed/3Dnn7NFQPbQ?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen=""></iframe>
            </div>
        </div>
        <div class="row center">
            <a href="<spring:url value='/sign-on' />" id="join-button-bottom" class="btn-large waves-effect waves-light purple"><fmt:message key="join.now" /></a>
        </div>
    </div>
</content:section>
