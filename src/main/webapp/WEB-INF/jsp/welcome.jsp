<content:title>
    <fmt:message key="frontpage.subtitle" />
</content:title>

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

              <p class="light center">By utilizing the power of crowdsourcing, we are able to gather a variety of content on one shared platform.</p>
              <p class="light center">The crowdsourced content is then added to an Android application for the children to use.</p>
            </div>
          </div>

          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">person_add</i></h2>
              <h5 class="center"><fmt:message key="how.can.i.help?" /></h5>

              <p class="light center">We need help from different types of people. E.g. content creators, storytellers, designers, photographers, developers, testers, etc.</p>
              <p class="light center">To receive information about different ways to participate, sign up to our <a href="http://eepurl.com/bGihkr">mailing list</a></p>
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
            <a href="http://eepurl.com/bGihkr" id="join-button-bottom" class="btn-large waves-effect waves-light"><fmt:message key="join.now" /></a>
        </div>
    </div>
</content:section>
