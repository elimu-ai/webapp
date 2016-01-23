<content:title>
    <fmt:message key="frontpage.subtitle" />
</content:title>

<content:banner>
    <br />
    <br />
    <br />
    <br />
    <div class="row center">
        <h1 class="header center white-text">Literacy<span>App</span></h1>
    </div>
    <div class="row center">
        <div class="col s2">&nbsp;</div>
        <h5 class="header col s8 light white-text"><fmt:message key="frontpage.subtitle" /></h5>
        <div class="col s2">&nbsp;</div>
    </div>
    <div class="row center">
      <a href="<spring:url value='/sign-on' />" id="join-button-top" class="btn-large waves-effect waves-light"><fmt:message key="join.now" /></a>
    </div>
    <br />
</content:banner>

<content:section cssId="welcomePage">
    <div class="section">
        <!--   Icon Section   -->
        <div class="row">
          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">lock_open</i></h2>
              <h5 class="center"><fmt:message key="open.source" /></h5>

              <p class="light center"><fmt:message key="frontpage.open.source.description" /></p>
              <%--
              <p class="light center">All of the code used in the project is publicly available in our <a href="https://github.com/XPRIZE/GLEXP-Team-Educativo-LiteracyApp">GitHub</a> repository.</p>
              --%>
              <div class="row center">
                <a href="https://github.com/literacyapp-org" id="download-button" class="btn-large waves-effect waves-light"><fmt:message key="download.source.code" /></a>
              </div>
            </div>
          </div>

          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">group</i></h2>
              <h5 class="center"><fmt:message key="crowdsourced" /></h5>

              <p class="light center"><fmt:message key="frontpage.crowdsourced.description1" /></p>
              <p class="light center"><fmt:message key="frontpage.crowdsourced.description2" /></p>
            </div>
          </div>

          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">person_add</i></h2>
              <h5 class="center"><fmt:message key="how.can.i.help?" /></h5>

              <p class="light center"><fmt:message key="frontpage.how.can.i.help.description1" /></p>
              <p class="light center"><fmt:message key="frontpage.how.can.i.help.description2" /> <a href="http://eepurl.com/bGihkr"><fmt:message key="mailing.list" /></a></p>
            </div>
          </div>
        </div>
    </div>

    <div class="section">
        <div class="row center">
            <a href="http://learning.xprize.org" target="_blank">
                <img src="http://educativo.eu/GLEXP_logo.jpg" alt="Global Learning XPRIZE" style="max-width: 300px;" />
            </a>
        </div>
        <div class="row center">
            <div class="video-container">
                <iframe width="560" height="315" src="https://www.youtube.com/embed/3Dnn7NFQPbQ?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen=""></iframe>
            </div>
        </div>
        <div class="row center">
            <a href="<spring:url value='/sign-on' />" id="join-button-bottom" class="btn-large waves-effect waves-light"><fmt:message key="join.now" /></a>
        </div>
    </div>
</content:section>
