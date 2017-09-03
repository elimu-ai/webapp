<content:title>
    <fmt:message key='free.quality.education.for.every.child.title' />
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
        <a href="#contributeInfoContainer" id="join-button-top" class="btn-large waves-effect waves-light deep-purple lighten-2"><fmt:message key="contribute.now" /></a>
        <script>
            $(function() {
              $('#join-button-top').click(function(event) {
                  event.preventDefault();
                  $('html, body').animate({
                      scrollTop: $('[name="contributeInfoContainer"]').offset().top
                  }, 1000);
              });
            });
        </script>
    </div>
    <br />
</content:banner>

<content:section cssId="welcomePage">
    <div class="section">
        <!--   Icon Section   -->
        <div class="row">
          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium blue-grey-text">public</i></h2>
              <h4 class="center"><fmt:message key="purpose" /></h4>

              <p class="light center"><%--<fmt:message key="millions.of.children.around.the.globe" />&nbsp;--%><fmt:message key="the.purpose.of.elimu.ai.is.to" /></p>
            </div>
          </div>
          
          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center deep-purple-text"><i class="material-icons medium">school</i></h2>
              <h4 class="center"><fmt:message key="why?" /></h4>

              <p class="light center"><fmt:message key="the.word.elimu.is.swahili.for" />&nbsp;<fmt:message key="we.believe.that.a.free.quality.education.is" /></p>
            </div>
          </div>

          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">stay_current_landscape</i></h2>
              <h4 class="center"><fmt:message key="solution" /></h4>

              <p class="light center"><fmt:message key="we.build.tablet.based.software" /></p>
            </div>
          </div>
        </div>
    </div>
            
    <div class="section">
        <%--<div class="row">
            <div class="col s12 m4 offset-m4">
                <div class="card small">
                  <div class="card-image">
                    <img src="<spring:url value='/static/static/img/banner-en.jpg' />" alt="Global Learning XPRIZE" />
                    <span class="card-title">Global Learning XPRIZE</span>
                  </div>
                  <div class="card-content">
                      <p>
                          <img src="<spring:url value='/static/img/global-learning-xprize-600x142.png' />" alt="Global Learning XPRIZE" style="max-width: 100%;" />
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
        <a name="contributeInfoContainer"></a>
        <div class="row center">
            <h4 class="header"><fmt:message key="contribute.now" /></h4>
            <p>There are several ways you can contribute:</p>
        </div>
        
        <div class="divider" style="margin-bottom: 3em;"></div>
        
        <div class="row">
            <div class="col s12 m3">
                <div class="icon-block center">
                    <h2><i class="material-icons medium blue-grey-text">redeem</i></h2>
                    <h4><fmt:message key="donate.money" /></h4>

                    <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
                        <input type="hidden" name="cmd" value="_s-xclick">
                        <input type="hidden" name="hosted_button_id" value="6FRDJF8PK9UHY">
                        <table>
                            <tr>
                                <td class="center">
                                    <input type="hidden" name="on0" value="elimu.ai contribution">Contribute by helping us cover our monthly recurring costs:
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <select name="os0">
                                        <option value="Option 1">Option 1 : €5.00 EUR - monthly</option>
                                        <option value="Option 2">Option 2 : €10.00 EUR - monthly</option>
                                        <option value="Option 3">Option 3 : €20.00 EUR - monthly</option>
                                        <option value="Option 4">Option 4 : €40.00 EUR - monthly</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="currency_code" value="EUR">
                        <input type="submit" class="btn waves-effect waves-light blue-grey" value="Donate via PayPal" />
                        <img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
                    </form>
                    
                    <br />
                    <fmt:message key="or" /><br />
                    <br />
                    
                    <a href="<spring:url value='/donate' />">Donate cryptocurrency</a>
                </div>
            </div>
                  
            <div class="col s12 offset-m1 m5 z-depth-2 white center">
                <div class="icon-block" style="padding: 1em;">
                  <h2><i class="material-icons large deep-purple-text">group</i></h2>
                  <h4><fmt:message key="join.a.team" /></h4>
                  
                  <p>
                      We need help from both technical and non-technical people. 
                      Every contribution helps, both small and large.
                  </p>
                  <p>
                      You are welcome to join a team matching your skill set and 
                      become an active contributor. We will help introduce you 
                      to team members and tasks.
                  </p>
                  <a href="<spring:url value='/sign-on' />" id="join-button-top" class="btn-large waves-effect waves-light deep-purple"><fmt:message key="join.now" /></a>
                </div>
                <br />
            </div>
                  
            <div class="col s12 offset-m1 m2 center">
                <div class="icon-block">
                  <h2><i class="material-icons medium">record_voice_over</i></h2>
                  <h4><fmt:message key="spread.the.word" /></h4>
                  
                  <p>Follow us on social media and share our work with your friends.</p>
                </div>
            </div>
        </div>
    </div>
</content:section>
