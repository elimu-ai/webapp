<content:title>
    <fmt:message key='free.quality.education.for.every.child' />
</content:title>

<content:banner>
    <br />
    <br />
    <br />
    <br />
    <div class="row center">
        <h1 class="header center white-text">elimu<span style="color: #A59ADD;">.ai</span></h1>
    </div>
    <div class="row center">
        <div class="col s2 m3 l4">&nbsp;</div>
        <h5 class="header col s8 m6 l4 light white-text"><fmt:message key="free.quality.education.for.every.child" /></h5>
        <div class="col s2 m3 l4">&nbsp;</div>
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
            
    <div class="section">
        <%--<div class="row">
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
        </div>--%>
        <div class="row center">
            <div class="video-container">
                <iframe width="560" height="315" src="https://www.youtube.com/embed/3Dnn7NFQPbQ?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen=""></iframe>
            </div>
        </div>
        <%--<div class="row center">
            <a href="<spring:url value='/sign-on' />" id="join-button-bottom" class="btn-large waves-effect waves-light purple"><fmt:message key="join.now" /></a>
        </div>--%>
    </div>
    
    <div class="section">
        <div class="row">
            <div class="col s12 m4 offset-m4 center">
                <h5 class="center"><fmt:message key="donate" /></h5>
                
                <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
                    <input type="hidden" name="cmd" value="_s-xclick">
                    <input type="hidden" name="hosted_button_id" value="6FRDJF8PK9UHY">
                    <table>
                    <tr><td><input type="hidden" name="on0" value="elimu.ai contribution">Contribute by helping us cover our monthly recurring costs:</td></tr><tr><td><select name="os0">
                            <option value="Option 1">Option 1 : €5.00 EUR - monthly</option>
                            <option value="Option 2">Option 2 : €10.00 EUR - monthly</option>
                            <option value="Option 3">Option 3 : €20.00 EUR - monthly</option>
                            <option value="Option 4">Option 4 : €40.00 EUR - monthly</option>
                    </select> </td></tr>
                    </table>
                    <input type="hidden" name="currency_code" value="EUR">
                    <input type="image" src="https://www.paypalobjects.com/webstatic/en_US/i/btn/png/btn_donate_cc_147x47.png" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
                    <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
                </form>
            </div>
        </div>
    </div>
</content:section>
